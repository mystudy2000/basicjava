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

    public <T> T transactionalExecute(SqlTransaction<T> runner) {
        try (Connection conn = connectionFactory.getConnection()) {
            try {
                conn.setAutoCommit(false);
                T res = runner.execution(conn);
                conn.commit();
                return res;
            } catch (SQLException e) {
                conn.rollback();
                throw ExceptionUtil.convertException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public interface SqlExecution<T> {
        T execution(PreparedStatement st) throws SQLException;
    }

    public interface SqlTransaction<T> {
        T execution(Connection con) throws SQLException;
    }
}