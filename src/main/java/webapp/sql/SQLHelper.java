package webapp.sql;

import webapp.exceptions.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLHelper {
    private final ConnectionFactory connectionFactory;

    public SQLHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T execution(String sql, SqlExecution<T> runner) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement preparedStmt = conn.prepareStatement(sql)) {
            return runner.execution(preparedStmt);
        } catch (SQLException e) {
            System.out.println("SQLException message: " + e.getMessage());
            System.out.println("SQLException SQL state: " + e.getSQLState());
            System.out.println("SQLException SQL error code: " + e.getErrorCode());
            throw new StorageException("SQL command problem!");
        }
    }

    public interface SqlExecution<T> {
        T execution(PreparedStatement st) throws SQLException;
    }
}