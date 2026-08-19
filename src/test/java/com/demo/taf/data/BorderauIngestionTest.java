package com.demo.taf.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.demo.taf.data.models.BorderauRecord;

public class BorderauIngestionTest {

    @BeforeAll
    static void setUpDatabase() throws SQLException {
        try (Connection connection = DatabaseManager.getConnection()) {
            Statement statement = connection.createStatement();

            String createTableSql = """
                CREATE TABLE IF NOT EXISTS claims_bordereau (
                    claim_id VARCHAR(50) PRIMARY KEY,
                    cedent_name VARCHAR(100),
                    treaty_reference VARCHAR(50),
                    reserve_amount DECIMAL(15, 2),
                    status VARCHAR(20)
                )
            """;
            statement.execute(createTableSql);
        }
    }

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

    @AfterAll
    static void tearDownDatabase() {
        DatabaseManager.closePool();
    }
    
}
