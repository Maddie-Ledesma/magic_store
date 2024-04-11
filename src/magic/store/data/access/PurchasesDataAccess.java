package magic.store.data.access;

import magic.store.data.models.PurchasesModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PurchasesDataAccess {
    private static final String tableName = "Purchases";

    public PurchasesModel create(PurchasesModel request, Connection connection) throws SQLException {
        final String SQL = "INSERT INTO Purchases(ItemName, Quantity, PayPrice, PurchaseDate) VALUES(?, ?, ?, ?)";
        try (var pstm = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setString(1, request.getItemName());
            pstm.setInt(2, request.getQuantity());
            pstm.setDouble(3, request.getPayPrice());
            pstm.setDate(4, new java.sql.Date(request.getPurchaseDate().getTime()));
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

    public PurchasesModel update(PurchasesModel request, Connection connection) throws SQLException {
        final String SQL = "UPDATE Purchases SET ItemName = ?, Quantity = ?, PayPrice = ?, PurchaseDate = ? WHERE id = ?";
        try (var pstm = connection.prepareStatement(SQL)) {
            pstm.setString(1, request.getItemName());
            pstm.setInt(2, request.getQuantity());
            pstm.setDouble(3, request.getPayPrice());
            pstm.setDate(4, new java.sql.Date(request.getPurchaseDate().getTime()));
            pstm.setInt(5, request.getId());
            var affectedRows = pstm.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException(String.format("Updating %s failed, no rows affected.", tableName));
            }
        }
        return request;
    }

    public void delete(PurchasesModel request, Connection connection) throws SQLException {
        final String SQL = "DELETE FROM Purchases WHERE id = ?";
        try (var pstm = connection.prepareStatement(SQL)) {
            pstm.setInt(1, request.getId());
            var affectedRows = pstm.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException(String.format("Deleting %s failed, no rows affected.", tableName));
            }
        }
    }

    public PurchasesModel getByCustomerId(PurchasesModel request, Connection connection) throws SQLException {
//        final String SQL = "SELECT * Purchases WHERE UPPER(name) = ?";
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

    public List<PurchasesModel> listAll(Connection connection) throws SQLException {
        final String SQL = "SELECT * Purchases ORDER BY id ASC";
        try (var pstm = connection.prepareStatement(SQL)) {
            try (var resultset = pstm.executeQuery()) {
                var result = new ArrayList<PurchasesModel>();
                while (resultset.next()) {
                    result.add(createFromRow(resultset));
                }
                return result;
            }
        }
    }

    private PurchasesModel createFromRow(ResultSet resultSet) throws SQLException {
        return new PurchasesModel(
                resultSet.getInt("id"),
                resultSet.getString("ItemName"),
                resultSet.getInt("Quantity"),
                resultSet.getDouble("PayPrice"),
                DateHelper.fromLocalDate(resultSet.getObject("PurchaseDate", LocalDate.class))
        );
    }
}
