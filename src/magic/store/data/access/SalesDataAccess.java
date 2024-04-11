package magic.store.data.access;

import magic.store.data.models.SalesModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SalesDataAccess {
    private static final String tableName = "Sales";

    public SalesModel create(SalesModel request, Connection connection) throws SQLException {
        final String SQL = "INSERT INTO Sales(Id, CustomerId, SalesDate) VALUES(?,?,?)";
        try (var pstm = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setInt(1, request.getId());
            pstm.setInt(2, request.getCostumerId());
            pstm.setDate(3, new java.sql.Date(request.getSalesDate().getTime()));
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

    public SalesModel update(SalesModel request, Connection connection) throws SQLException {
        final String SQL = "UPDATE Sales SET CustomerId = ?, SalesDate = ? WHERE id = ?";
        try (var pstm = connection.prepareStatement(SQL)) {
            pstm.setInt(1, request.getCostumerId());
            pstm.setDate(2, new java.sql.Date(request.getSalesDate().getTime()));
            pstm.setInt(3, request.getId());
            var affectedRows = pstm.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException(String.format("Updating %s failed, no rows affected.", tableName));
            }
        }
        return request;
    }

    public void delete(SalesModel request, Connection connection) throws SQLException {
        final String SQL = "DELETE FROM Sales WHERE id = ?";
        try (var pstm = connection.prepareStatement(SQL)) {
            pstm.setInt(1, request.getId());
            var affectedRows = pstm.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException(String.format("Deleting %s failed, no rows affected.", tableName));
            }
        }
    }

    public SalesModel getByCustomerId(SalesModel request, Connection connection) throws SQLException {
//        final String SQL = "SELECT * Sales WHERE UPPER(name) = ?";
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

    public List<SalesModel> listAll(Connection connection) throws SQLException {
        final String SQL = "SELECT * Sales ORDER BY id ASC";
        try (var pstm = connection.prepareStatement(SQL)) {
            try (var resultset = pstm.executeQuery()) {
                var result = new ArrayList<SalesModel>();
                while (resultset.next()) {
                    result.add(createFromRow(resultset));
                }
                return result;
            }
        }
    }

    private SalesModel createFromRow(ResultSet resultSet) throws SQLException {
        return new SalesModel(
                resultSet.getInt("id"),
                resultSet.getInt("CustomerId"),
                DateHelper.fromLocalDate(resultSet.getObject("SalesDate", LocalDate.class))
        );
    }
}
