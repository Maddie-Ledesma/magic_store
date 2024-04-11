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

    public static List<ItemModel> listByCriteria(
            Integer dangerLevel,
            Integer magicId,
            Double price,
            boolean isGreater,
            Connection connection
    ) throws SQLException {
        String SQL = "SELECT i.*, m.Name as MagicName FROM Item AS i INNER JOIN Magic AS m ON i.MagicId = m.Id WHERE";
        var hasAnd = false; // Use to append and when multiple conditions are included
        if (dangerLevel != null) {
            SQL += ( " DangerLevel = " + dangerLevel );
            hasAnd = true;
        }
        if (magicId != null) {
            SQL += hasAnd ? " AND " : "";
            SQL += ( " MagicId = " + magicId );
            hasAnd = true;
        }

        if (price != null) {
            SQL += hasAnd ? " AND " : "";
            SQL += (" SellPrice " + (isGreater ? ">= " : "<= " ) + price );
        }
        System.out.println(SQL);
        try (var pstm = connection.createStatement()) {
            try (var resultset = pstm.executeQuery(SQL)) {
                var result = new ArrayList<ItemModel>();
                while (resultset.next()) {
                    result.add(createFromRow(resultset));
                }
                return result;
            }
        }
    }

    private static ItemModel createFromRow(ResultSet resultSet) throws SQLException {
        return new ItemModel(
                resultSet.getString("ItemName"),
                resultSet.getString("Description"),
                resultSet.getInt("MagicId"),
                resultSet.getInt("Quantity"),
                resultSet.getInt("DangerLevel"),
                resultSet.getDouble("SellPrice"),
                resultSet.getString("magicName")
        );
    }
}
