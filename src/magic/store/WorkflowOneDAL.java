package magic.store;

import magic.store.connector.DbConnector;
import magic.store.data.MagicDataCache;

import java.io.Console;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class WorkflowOneDAL {

    private final Connection connection;
    private MagicDataCache cache;
    public WorkflowOneDAL(String user, String password) throws SQLException {
        var dbConnector = new DbConnector(user, password);
        this.connection = dbConnector.getConnection();
        this.cache = new MagicDataCache(this.connection);
    }

    /**
     * From a give set of attributes delimited by comma, all the necessaries will need be passed.
     * Example:
     *  Wand, It's a Wand, Sorcery, 1, 1, 4.50
     *  ItemName, Description, Magic Type, Quantity, DangerLevel, Price
     *
     * @param line
     */
    public void registerItem(String line) throws SQLException {
        String[] tokens = line.split(",");
        if (tokens.length != 6) {
            throw new RuntimeException("Missing data, Item line should contains ItemName, Description, Magic Type, Quantity, DangerLevel, Price");
        }

        // Converting magic name to magic id
        var magicId = this.cache.getIdByName(tokens[2].trim());
        if (magicId == null) {
            throw new RuntimeException(String.format("Magic %s is not valid classification", tokens[2]));
        }

        try( var callable = connection.prepareCall("CALL addItem(?, ?, ?, ?, ?, ?)")) {
            callable.setString(1, tokens[0].trim());
            callable.setString(2, tokens[1].trim());
            callable.setInt(3, magicId);
            callable.setInt(4, Integer.parseInt(tokens[3].trim()));
            callable.setInt(5, Integer.parseInt(tokens[4].trim()));
            callable.setDouble(6, Double.parseDouble(tokens[5].trim()));
            callable.execute();
        }

    }
}