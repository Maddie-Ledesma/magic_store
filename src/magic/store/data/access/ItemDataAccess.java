package magic.store.data.access;

import magic.store.data.models.CustomerModel;
import magic.store.data.models.ItemModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ItemDataAccess {
    private static final String tableName = "Item";

    public ItemModel create(ItemModel request, Connection connection) throws SQLException {
        final String SQL = "INSERT INTO Item(ItemName, Description, MagicId, Quantity, DangerLevel, SellPrice) VALUES(?, ?, ?, ?, ?, ?)";
        try (var pstm = connection.prepareStatement(SQL)) {
            pstm.setString(1, request.getItemName());
            pstm.setString(2, request.getDescription());
            pstm.setInt(3, request.getMagicId());
            pstm.setInt(4, request.getQuantity());
            pstm.setInt(5, request.getDangerLevel());
            pstm.setDouble(6, request.getSellPrice());
            var affectedRows = pstm.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException(String.format("Creating %s failed, no rows affected.", tableName));
            }

        }
        return request;
    }

    public ItemModel update(ItemModel request, Connection connection) throws SQLException {
        final String SQL = "UPDATE Item SET Description = ?, MagicId = ?, Quantity = ?, DangerLevel = ?, SellPrice = ?, WHERE ItemName = ?";
        try (var pstm = connection.prepareStatement(SQL)) {
            pstm.setString(1, request.getDescription());
            pstm.setInt(2, request.getMagicId());
            pstm.setInt(3, request.getQuantity());
            pstm.setInt(4, request.getDangerLevel());
            pstm.setDouble(5, request.getSellPrice());
            pstm.setString(6, request.getItemName());
            var affectedRows = pstm.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException(String.format("Updating %s failed, no rows affected.", tableName));
            }
        }
        return request;
    }

    public void delete(ItemModel request, Connection connection) throws SQLException {
        final String SQL = "DELETE FROM Item WHERE ItemName = ?";
        try (var pstm = connection.prepareStatement(SQL)) {
            pstm.setString(1, request.getItemName());
            var affectedRows = pstm.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException(String.format("Deleting %s failed, no rows affected.", tableName));
            }
        }
    }

    public ItemModel getByName(ItemModel request, Connection connection) throws SQLException {
        final String SQL = "SELECT * Item WHERE UPPER(ItemName) = ?";
        try (var pstm = connection.prepareStatement(SQL)) {
            pstm.setString(1, request.getItemName().toUpperCase());
            try (var resultset = pstm.executeQuery()) {
                if (resultset.next()) {
                    return createFromRow(resultset);
                }
            }
        }
        return null;
    }

    public List<ItemModel> listAll(Connection connection) throws SQLException {
        final String SQL = "SELECT * Item ORDER BY name ASC";
        try (var pstm = connection.prepareStatement(SQL)) {
            try (var resultset = pstm.executeQuery()) {
                var result = new ArrayList<ItemModel>();
                while (resultset.next()) {
                    result.add(createFromRow(resultset));
                }
                return result;
            }
        }
    }

    private ItemModel createFromRow(ResultSet resultSet) throws SQLException {
        return new ItemModel(
                resultSet.getString("ItemName"),
                resultSet.getString("Description"),
                resultSet.getInt("MagicId"),
                resultSet.getInt("Quantity"),
                resultSet.getInt("DangerLevel"),
                resultSet.getDouble("SellPrice")
        );
    }
}
