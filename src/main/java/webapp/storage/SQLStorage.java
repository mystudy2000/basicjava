package webapp.storage;

import webapp.exceptions.StorageException;
import webapp.model.Resume;
import webapp.model.TypeOfContact;
import webapp.sql.SQLHelper;

import java.sql.*;
import java.util.*;

// TODO implement Section (except OrganizationSection)
// TODO Join and split ListSection by `\n`

public class SQLStorage implements Storage {

    private static final String SQL_CLEAR_ALL = "DELETE FROM resume";
    private static final String SQL_GET = "SELECT * FROM resume r LEFT JOIN contact c ON r.uuid =c.resume_uuid WHERE r.uuid =?";
    private static final String SQL_UPDATE_RESUME = "UPDATE resume SET full_name = ? WHERE uuid = ?";
    private static final String SQL_UPDATE_CONTACTS = "UPDATE contact SET value = ? WHERE type =? AND resume_uuid = ?";
    private static final String SQL_SAVE_RESUME = "INSERT INTO resume (full_name,uuid) VALUES (?,?)";
    private static final String SQL_SAVE_CONTACTS = "INSERT INTO contact (value, type,resume_uuid) VALUES (?,?,?)";
    private static final String SQL_DELETE_RESUME = "DELETE FROM resume WHERE uuid=?";
    private static final String SQL_GET_ALL_SORTED = "SELECT * FROM resume r LEFT JOIN contact c ON r.uuid = c.resume_uuid ORDER BY full_name, uuid";
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
                    preparedStatement.executeUpdate();
                    return null;
                }
        );
    }

    @Override
    public Resume get(UUID uuid) {
        return sqlHelper.execution(SQL_GET, preparedStatement -> {
            preparedStatement.setString(1, String.valueOf(uuid));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new StorageException("UUID = " + String.valueOf(uuid) + " is not EXIST!");
            }
            Resume r = new Resume(uuid, resultSet.getString("full_name"));
            do {
                addContact(resultSet, r);
            } while (resultSet.next());
            return r;
        });

    }

    @Override
    public void update(UUID uuid, Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            updateResumeToDB(r, conn, SQL_UPDATE_RESUME, "UUID is NOT EXIST. UUID = ");
            updateContactsToDB(r, conn, SQL_UPDATE_CONTACTS);
            return null;
        });

    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            updateResumeToDB(r, conn, SQL_SAVE_RESUME, "UUID is DUPLICATED. UUID = ");
            updateContactsToDB(r, conn, SQL_SAVE_CONTACTS);
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
            Map<UUID, Resume> map = new LinkedHashMap<>();
            while (resultSet.next()) {
                UUID uuid = UUID.fromString(resultSet.getString("uuid"));
                Resume resume = map.get(uuid);
                if (resume == null) {
                    resume = new Resume(uuid, resultSet.getString("full_name"));
                    map.put(uuid, resume);
                                    }
                                addContact(resultSet, resume);
            }
            return new ArrayList<>(map.values());
        });
    }

    @Override
    public int size() {
        return sqlHelper.execution(SQL_TABLE_SIZE, preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? resultSet.getInt(1) : 0;
        });
    }

    private void updateResumeToDB(Resume r, Connection conn, String sql, String exception) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, r.getFullName());
            ps.setString(2, String.valueOf(r.getUuid()));
            if (ps.executeUpdate() != 1) {
                throw new StorageException(exception + String.valueOf(r.getUuid()));
            }
        }
    }

    private void updateContactsToDB(Resume r, Connection conn, String sql) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Map.Entry<TypeOfContact, String> e : r.getContacts().entrySet()) {
                ps.setString(3, String.valueOf(r.getUuid()));
                ps.setString(2, e.getKey().name());
                ps.setString(1, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void addContact(ResultSet rs, Resume r) throws SQLException {
                String value = rs.getString("value");
                r.setContact(TypeOfContact.valueOf(rs.getString("type")), value);
            }
}
