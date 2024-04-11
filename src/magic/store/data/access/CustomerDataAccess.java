package magic.store.data.access;

import magic.store.data.models.CustomerModel;
import magic.store.data.models.MagicModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerDataAccess {
    private static final String tableName = "Customer";

    public CustomerModel create(CustomerModel request, Connection connection) throws SQLException {
        final String SQL = "INSERT INTO Customer(Name, Age) VALUES(?,?)";
        try (var pstm = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setString(1, request.getName());
            pstm.setInt(2, request.getAge());
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

    public CustomerModel update(CustomerModel request, Connection connection) throws SQLException {
        final String SQL = "UPDATE Customer SET NAME = ?, AGE = ? WHERE id = ?";
        try (var pstm = connection.prepareStatement(SQL)) {
            pstm.setString(1, request.getName());
            pstm.setInt(2, request.getAge());
            pstm.setInt(1, request.getId());
            var affectedRows = pstm.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException(String.format("Updating %s failed, no rows affected.", tableName));
            }
        }
        return request;
    }

    public void delete(CustomerModel request, Connection connection) throws SQLException {
        final String SQL = "DELETE FROM Customer WHERE id = ?";
        try (var pstm = connection.prepareStatement(SQL)) {
            pstm.setInt(1, request.getId());
            var affectedRows = pstm.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException(String.format("Deleting %s failed, no rows affected.", tableName));
            }
        }
    }

    public CustomerModel getByName(CustomerModel request, Connection connection) throws SQLException {
        final String SQL = "SELECT * Customer WHERE UPPER(name) = ?";
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

    public List<CustomerModel> listAll(Connection connection) throws SQLException {
        final String SQL = "SELECT * Customer ORDER BY name ASC";
        try (var pstm = connection.prepareStatement(SQL)) {
            try (var resultset = pstm.executeQuery()) {
                var result = new ArrayList<CustomerModel>();
                while (resultset.next()) {
                    result.add(createFromRow(resultset));
                }
                return result;
            }
        }
    }

    private CustomerModel createFromRow(ResultSet resultSet) throws SQLException {
        return new CustomerModel(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getInt("age")
        );
    }
}
