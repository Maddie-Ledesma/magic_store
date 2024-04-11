package magic.store.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {
    private static final String url = "jdbc:mysql://localhost/MagicItemShop";
    private final Connection connection;

    public DbConnector(String user, String password) throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
        System.out.println("Connected to SQLite database.");
    }

    public Connection getConnection() {
        return this.connection;
    }

}
