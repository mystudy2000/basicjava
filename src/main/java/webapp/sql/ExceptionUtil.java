package webapp.sql;

import org.postgresql.util.PSQLException;
import webapp.exceptions.StorageException;

import java.sql.SQLException;

public class ExceptionUtil {
    private ExceptionUtil() {
    }

    public static StorageException convertException(SQLException e) {
        if (e instanceof PSQLException) {
            System.out.println("SQLException message: " + e.getMessage());
            System.out.println("SQLException SQL state: " + e.getSQLState());
            System.out.println("SQLException SQL error code: " + e.getErrorCode());
//            http://www.postgresql.org/docs/9.3/static/errcodes-appendix.html
            if (e.getSQLState().equals("23505")) {
                return new StorageException("UUID is not found");
            }
        }
        return new StorageException(e);
    }
}
