package com.demo.taf.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.demo.taf.base.DatabaseBaseTest;
import com.demo.taf.data.models.BorderauRecord;

public class BorderauIngestionTest extends DatabaseBaseTest {

    static Stream<BorderauRecord> borderauDataProvider() {
        return Stream.of(
            new BorderauRecord("CLM-2026-X99", "Allianz Global", "TREATY-ALL-26", 250000.50, "OPEN"),
            new BorderauRecord("CLM-2026-Y88", "Munich Re", "TREATY-MUN-26", 150000.00, "CLOSED"),
            new BorderauRecord("CLM-2026-Z77", "Swiss Re", "TREATY-SWI-26", 50000.75, "PENDING")
        );
    }

    @ParameterizedTest(name = "Data Pipeline: Ingest {0} for {1}")
    @MethodSource("borderauDataProvider")
    public void testBorderauIngestion(BorderauRecord record) throws SQLException {

        String insertSql = "INSERT INTO claims_bordereau (claim_id, cedent_name, treaty_reference, reserve_amount, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {

            preparedStatement.setString(1, record.claimId());
            preparedStatement.setString(2, record.cedentName());
            preparedStatement.setString(3, record.treatyRef());
            preparedStatement.setDouble(4, record.reserveAmount());
            preparedStatement.setString(5, record.status());
            preparedStatement.executeUpdate();
        }

        String selectSql = "SELECT * FROM claims_bordereau WHERE claim_id = '" + record.claimId() + "'";

        try (Connection connection = DatabaseManager.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(selectSql)) {

            
            assertThat(resultSet.next())
                .as("The ingested claim record should exist in the database")
                .isTrue();

            assertThat(resultSet.getString("cedent_name")).isEqualTo(record.cedentName());
            assertThat(resultSet.getDouble("reserve_amount")).isEqualTo(record.reserveAmount());
            assertThat(resultSet.getString("status")).isEqualTo(record.status());
        }
    }

    @Test
    @DisplayName("Data Pipeline: Update existing Reserve Amount (UPDATE)")
    public void testUpdateReserveAmount() throws SQLException {
        
        String insertSql = "INSERT INTO claims_bordereau (claim_id, cedent_name, reserve_amount, status) " +
                           "VALUES ('CLM-UPDATE-01', 'Test Re', 500.00, 'OPEN')";

        String updateSql = "UPDATE claims_bordereau SET reserve_amount = 999.99 WHERE claim_id = 'CLM-UPDATE-01'";
        
        String selectSql = "SELECT reserve_amount FROM claims_bordereau WHERE claim_id = 'CLM-UPDATE-01'";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement()) {
            
            stmt.execute(insertSql);
            
            int rowsAffected = stmt.executeUpdate(updateSql);
            assertThat(rowsAffected).as("One row should be updated").isEqualTo(1);
            
            try (ResultSet rs = stmt.executeQuery(selectSql)) {
                rs.next();
                assertThat(rs.getDouble("reserve_amount"))
                    .as("Reserve amount should reflect the newly updated value")
                    .isEqualTo(999.99);
            }
        }
    }

    @Test
    @DisplayName("Data Pipeline Negative: Reject invalid data type mapping")
    void testDatabaseRejectsInvalidData() {
        String invalidInsertSql = "INSERT INTO claims_bordereau (claim_id, reserve_amount) " +
                                  "VALUES ('CLM-ERR-01', 'INVALID_STRING')";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement()) {
            
            assertThrows(SQLException.class, () -> {
                stmt.execute(invalidInsertSql);
            }, "Database should strictly enforce schema constraints and throw an SQLException");
            
        } catch (SQLException e) {
        }
    }
}
