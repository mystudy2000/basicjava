package webapp.storage;

import webapp.exceptions.StorageException;
import webapp.model.Resume;
import webapp.sql.SQLHelper;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SQLStorage implements Storage {

    private static final String SQL_CLEAR_ALL = "DELETE FROM resume";
    private static final String SQL_GET_RESUME = "SELECT * FROM resume r WHERE r.uuid =?";
    private static final String SQL_UPDATE_RESUME = "UPDATE resume SET full_name = ? WHERE uuid = ?";
    private static final String SQL_SAVE_RESUME = "INSERT INTO resume (uuid, full_name) VALUES (?,?)";
    private static final String SQL_DELETE_RESUME = "DELETE FROM resume WHERE uuid=?";
    private static final String SQL_GET_ALL_SORTED = "SELECT * FROM resume r ORDER BY full_name,uuid";
    private static final String SQL_TABLE_SIZE = "SELECT count(*) FROM resume";

    private final SQLHelper sqlHelper;

    public SQLStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
            sqlHelper = new SQLHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
        } catch (ClassNotFoundException e) {
            throw new StorageException("Can't load PostgreSQL driver " + e);
        }
    }

    @Override
    public void clear() {
        sqlHelper.execution(SQL_CLEAR_ALL, preparedStatement -> {
            if (preparedStatement.executeUpdate() == 0) {
                throw new StorageException("Table resume is empty already.");
            }
            return null;
        });
    }

    @Override
    public Resume get(UUID uuid) {
        return sqlHelper.execution(SQL_GET_RESUME, preparedStatement -> {
            preparedStatement.setString(1, String.valueOf(uuid));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new StorageException(String.valueOf(uuid));
            }
            String name = resultSet.getString("full_name");
            SQLHelper.closeResultSet(resultSet);
            return new Resume(UUID.fromString(String.valueOf(uuid)), name);
        });

    }

    @Override
    public void update(UUID uuid, Resume r) {
        sqlHelper.execution(SQL_UPDATE_RESUME, preparedStatement -> {
            preparedStatement.setString(1, r.getFullName());
            preparedStatement.setString(2, String.valueOf(r.getUuid()));
            if (preparedStatement.executeUpdate() == 0) {
                throw new StorageException(String.valueOf(r.getUuid()));
            }
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.execution(SQL_SAVE_RESUME, preparedStatement -> {
            preparedStatement.setString(1, String.valueOf(r.getUuid()));
            preparedStatement.setString(2, r.getFullName());
            preparedStatement.execute();
            return null;
        });
    }

    @Override
    public void delete(UUID uuid) {
        sqlHelper.execution(SQL_DELETE_RESUME, preparedStatement -> {
            preparedStatement.setString(1, String.valueOf(uuid));
            if (preparedStatement.executeUpdate() == 0) {
                throw new StorageException(String.valueOf(uuid));
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execution(SQL_GET_ALL_SORTED, preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            while (resultSet.next()) {
                resumes.add(new Resume(UUID.fromString(resultSet.getString("uuid")), resultSet.getString("full_name")));
            }
            SQLHelper.closeResultSet(resultSet);
            return resumes;
        });
    }

    @Override
    public int size() {
        return sqlHelper.execution(SQL_TABLE_SIZE, preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            int sizeOf = resultSet.next() ? resultSet.getInt(1) : 0;
            SQLHelper.closeResultSet(resultSet);
            return sizeOf;
        });
    }

}
