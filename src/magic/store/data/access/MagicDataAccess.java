package magic.store.data.access;

import magic.store.data.models.MagicModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MagicDataAccess {
    private static final String tableName = "Magic";

    public static MagicModel create(MagicModel request, Connection connection) throws SQLException {
        final String SQL = "INSERT INTO Magic(Name) VALUES(?)";
        try (var pstm = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setString(1, request.getName());
            var affectedRows = pstm.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException(String.format("Creating %s failed, no rows affected.", tableName));
            }
            try (var generatedKeys = pstm.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    request.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException(String.format("Creating %s failed, no ID obtained.", tableName));
                }
            }
        }
        return request;
    }

    public static MagicModel update(MagicModel request, Connection connection) throws SQLException {
        final String SQL = "UPDATE Magic SET NAME = ? WHERE id = ?";
        try (var pstm = connection.prepareStatement(SQL)) {
            pstm.setString(1, request.getName());
            pstm.setInt(1, request.getId());
            var affectedRows = pstm.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException(String.format("Updating %s failed, no rows affected.", tableName));
            }
        }
        return request;
    }

    public static void delete(MagicModel request, Connection connection) throws SQLException {
        final String SQL = "DELETE FROM Magic WHERE id = ?";
        try (var pstm = connection.prepareStatement(SQL)) {
            pstm.setInt(1, request.getId());
            var affectedRows = pstm.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException(String.format("Deleting %s failed, no rows affected.", tableName));
            }
        }
    }

    public static MagicModel getByName(MagicModel request, Connection connection) throws SQLException {
        final String SQL = "SELECT * Magic WHERE UPPER(name) = ?";
        try (var pstm = connection.prepareStatement(SQL)) {
            pstm.setString(1, request.getName().toUpperCase());
            try (var resultset = pstm.executeQuery()) {
                if (resultset.next()) {
                    return createFromRow(resultset);
                }
            }
        }
        return null;
    }

    public static List<MagicModel> listAll(Connection connection) throws SQLException {
        final String SQL = "SELECT * FROM Magic ORDER BY name ASC";
        try (var pstm = connection.prepareStatement(SQL)) {
            try (var resultset = pstm.executeQuery()) {
                var result = new ArrayList<MagicModel>();
                while (resultset.next()) {
                    result.add(createFromRow(resultset));
                }
                return result;
            }
        }
    }

    private static MagicModel createFromRow(ResultSet resultSet) throws SQLException {
        return new MagicModel(
                resultSet.getInt("id"),
                resultSet.getString("name")
        );
    }
}
