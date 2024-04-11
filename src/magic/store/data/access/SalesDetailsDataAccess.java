package magic.store.data.access;

import magic.store.data.models.SalesDetailsModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalesDetailsDataAccess {
    private static final String tableName = "SalesDetails";

    public SalesDetailsModel create(SalesDetailsModel request, Connection connection) throws SQLException {
        final String SQL = "INSERT INTO SalesDetails(SalesId, ItemName, SellPrice, Quantity) VALUES(?,?,?,?)";
        try (var pstm = connection.prepareStatement(SQL)) {
            pstm.setInt(1, request.getSalesId());
            pstm.setString(2, request.getItemName());
            pstm.setDouble(3, request.getSellPrice());
            pstm.setInt(4, request.getQuantity());
            var affectedRows = pstm.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException(String.format("Creating %s failed, no rows affected.", tableName));
            }
        }
        return request;
    }

    public SalesDetailsModel update(SalesDetailsModel request, Connection connection) throws SQLException {
        final String SQL = "UPDATE SalesDetails SET ItemName = ?, SellPrice = ?, Quantity = ? WHERE id = ?";
        try (var pstm = connection.prepareStatement(SQL)) {
            pstm.setString(1, request.getItemName());
            pstm.setDouble(2, request.getSellPrice());
            pstm.setInt(3, request.getQuantity());
            pstm.setInt(4, request.getSalesId());
            var affectedRows = pstm.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException(String.format("Updating %s failed, no rows affected.", tableName));
            }
        }
        return request;
    }

    public void delete(SalesDetailsModel request, Connection connection) throws SQLException {
        final String SQL = "DELETE FROM SalesDetails WHERE SalesId = ?";
        try (var pstm = connection.prepareStatement(SQL)) {
            pstm.setInt(1, request.getSalesId());
            var affectedRows = pstm.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException(String.format("Deleting %s failed, no rows affected.", tableName));
            }
        }
    }

    public SalesDetailsModel getByCustomerId(SalesDetailsModel request, Connection connection) throws SQLException {
//        final String SQL = "SELECT * SalesDetails WHERE UPPER(name) = ?";
//        try (var pstm = connection.prepareStatement(SQL)) {
//            pstm.setString(1, request.getName().toUpperCase());
//            try (var resultset = pstm.executeQuery()) {
//                if (resultset.next()) {
//                    return createFromRow(resultset);
//                }
//            }
//        }
        return null;
    }

    public List<SalesDetailsModel> listAll(Connection connection) throws SQLException {
        final String SQL = "SELECT * SalesDetails ORDER BY id ASC";
        try (var pstm = connection.prepareStatement(SQL)) {
            try (var resultset = pstm.executeQuery()) {
                var result = new ArrayList<SalesDetailsModel>();
                while (resultset.next()) {
                    result.add(createFromRow(resultset));
                }
                return result;
            }
        }
    }

    private SalesDetailsModel createFromRow(ResultSet resultSet) throws SQLException {
        return new SalesDetailsModel(
                resultSet.getInt("SalesId"),
                resultSet.getString("ItemName"),
                resultSet.getDouble("SellPrice"),
                resultSet.getInt("Quantity")
        );
    }
}
