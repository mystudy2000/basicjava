package webapp.storage;

import webapp.exceptions.StorageException;
import webapp.model.Resume;
import webapp.model.Section;
import webapp.model.TypeOfContact;
import webapp.model.TypeOfSection;
import webapp.sql.SQLHelper;
import webapp.util.JsonParser;

import java.sql.*;
import java.util.*;

// TODO implement Section (except OrganizationSection)
// TODO Join and split ListSection by `\n`

public class SQLStorage implements Storage {

    private static final String SQL_CLEAR_ALL = "DELETE FROM resume";
    private static final String SQL_GET_JOIN = "SELECT * FROM resume r LEFT JOIN contact c ON r.uuid =c.resume_uuid WHERE r.uuid =?";
    private static final String SQL_GET_FROM_RESUME = "SELECT * FROM resume WHERE uuid =?";
    private static final String SQL_GET_FROM_CONTACT = "SELECT * FROM contact WHERE resume_uuid =?";
    private static final String SQL_GET_FROM_SECTION = "SELECT * FROM section WHERE resume_uuid =?";
    private static final String SQL_UPDATE_RESUME = "UPDATE resume SET full_name = ? WHERE uuid = ?";
    private static final String SQL_UPDATE_CONTACTS = "UPDATE contact SET value = ? WHERE type =? AND resume_uuid = ?";
    private static final String SQL_INSERT_RESUME = "INSERT INTO resume (uuid, full_name) VALUES (?,?)";
    private static final String SQL_INSERT_CONTACT = "INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)";
    private static final String SQL_INSERT_SECTION = "INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)";
    private static final String SQL_SAVE_RESUME = "INSERT INTO resume (full_name,uuid) VALUES (?,?)";
    private static final String SQL_SAVE_CONTACTS = "INSERT INTO contact (value, type,resume_uuid) VALUES (?,?,?)";
    private static final String SQL_DELETE_RESUME = "DELETE FROM resume WHERE uuid=?";
    private static final String SQL_DELETE_CONTACT = "DELETE FROM contact WHERE resume_uuid=?";
    private static final String SQL_DELETE_SECTION = "DELETE FROM section WHERE resume_uuid=?";
    private static final String SQL_GET_ALL_SORTED = "SELECT * FROM resume r LEFT JOIN contact c ON r.uuid = c.resume_uuid ORDER BY full_name, uuid";
    private static final String SQL_SELECT_CONTACT = "SELECT * FROM contact";
    private static final String SQL_SELECT_SECTION = "SELECT * FROM section";
    private static final String SQL_GET_RESUME_SORTED = "SELECT * FROM resume ORDER BY full_name, uuid";
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
        return sqlHelper.transactionalExecute(conn -> {
            Resume r;
            try (PreparedStatement ps = conn.prepareStatement(SQL_GET_FROM_RESUME)) {
                ps.setString(1, String.valueOf(uuid));
                ResultSet resultSet = ps.executeQuery();
                if (!resultSet.next()) {
                    throw new StorageException("UUID = " + String.valueOf(uuid) + " is not EXIST!");
                }
                r = new Resume(uuid, resultSet.getString("full_name"));
            }

            try (PreparedStatement ps = conn.prepareStatement(SQL_GET_FROM_CONTACT)) {
                ps.setString(1, String.valueOf(uuid));
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addContact(rs, r);
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(SQL_GET_FROM_SECTION)) {
                ps.setString(1, String.valueOf(uuid));
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addSection(rs, r);
                }
            }
            return r;
        });
    }


    @Override
    public void update(UUID uuid, Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_RESUME)) {
                ps.setString(1, r.getFullName());
                ps.setString(2, String.valueOf(r.getUuid()));
                if (ps.executeUpdate() != 1) {
                    throw new StorageException("UUID is " + String.valueOf(r.getUuid()));
                }
            }
            deleteContacts(conn, r);
            deleteSections(conn, r);
            insertContacts(conn, r);
            insertSections(conn, r);
            return null;
        });

    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement(SQL_INSERT_RESUME)) {
                        ps.setString(1, String.valueOf(r.getUuid()));
                        ps.setString(2, r.getFullName());
                        ps.execute();
                    }
                    insertContacts(conn, r);
                    insertSections(conn, r);
                    return null;
                }
        );
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
        return sqlHelper.transactionalExecute(conn -> {
            Map<UUID, Resume> resumes = new LinkedHashMap<>();

            try (PreparedStatement ps = conn.prepareStatement(SQL_GET_RESUME_SORTED)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    UUID uuid = UUID.fromString(rs.getString("uuid"));
                    resumes.put(uuid, new Resume(uuid, rs.getString("full_name")));
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(SQL_SELECT_CONTACT)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    UUID uuid = UUID.fromString(rs.getString("resume_uuid"));
                    Resume r = resumes.get(uuid);
                    addContact(rs, r);
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(SQL_SELECT_SECTION)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    UUID uuid = UUID.fromString(rs.getString("resume_uuid"));
                    Resume r = resumes.get(uuid);
                    addSection(rs, r);
                }
            }

            return new ArrayList<>(resumes.values());
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

    private void insertContacts(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(SQL_INSERT_CONTACT)) {
            for (Map.Entry<TypeOfContact, String> e : r.getContacts().entrySet()) {
                ps.setString(1, String.valueOf(r.getUuid()));
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertSections(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(SQL_INSERT_SECTION)) {
            for (Map.Entry<TypeOfSection, Section> e : r.getSections().entrySet()) {
                ps.setString(1, String.valueOf(r.getUuid()));
                ps.setString(2, e.getKey().name());
                Section section = e.getValue();
                ps.setString(3, JsonParser.write(section, Section.class));
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteContacts(Connection conn, Resume r) throws SQLException {
        deleteAttributes(conn, r, SQL_DELETE_CONTACT);
    }

    private void deleteSections(Connection conn, Resume r) throws SQLException {
        deleteAttributes(conn, r, SQL_DELETE_SECTION);
    }

    private void deleteAttributes(Connection conn, Resume r, String sql) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, String.valueOf(r.getUuid()));
            ps.execute();
        }
    }

    private void addContact(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            r.setContact(TypeOfContact.valueOf(rs.getString("type")), value);
        }
    }

    private void addSection(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            r.setSection(TypeOfSection.valueOf(rs.getString("type")), JsonParser.read(value, Section.class));
        }
    }
}