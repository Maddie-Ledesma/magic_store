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
