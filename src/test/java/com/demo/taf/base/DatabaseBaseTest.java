package com.demo.taf.base;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.taf.data.DatabaseManager;
import com.demo.taf.listeners.TestExecutionWatcher;

@ExtendWith(TestExecutionWatcher.class)
public abstract class DatabaseBaseTest {
    protected static final Logger logger = LoggerFactory.getLogger(DatabaseBaseTest.class);

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

    @BeforeEach
    public void logTestName(TestInfo testInfo) {
        logger.info("▶️ STARTING TEST: {}", testInfo.getDisplayName());
    }

    @AfterAll
    static void tearDownDatabase() {
        logger.info("Closing database connection pool...");
        DatabaseManager.closePool();
    }
}
