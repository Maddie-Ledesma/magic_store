package magic.store;

import magic.store.connector.DbConnector;
import magic.store.data.MagicDataCache;
import magic.store.data.access.ItemDataAccess;
import magic.store.data.models.ItemModel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class WorkflowFourDAL {

    private final Connection connection;
    private MagicDataCache cache;

    public WorkflowFourDAL(String user, String password) throws SQLException {
        var dbConnector = new DbConnector(user, password);
        this.connection = dbConnector.getConnection();
        this.cache = new MagicDataCache(this.connection);
    }

    public void generateMonthlyReport(String strMonth) throws SQLException, IOException {
        Integer month = null;
        if (strMonth.trim().isEmpty()) {
            throw new RuntimeException("You must provide a month");
        }
        month = Integer.parseInt(strMonth);
        if (month < 1 || month > 12) {
            throw new RuntimeException("Invalid month: " + month + ", you must select a number between 1 and 12");
        }
        var currentTime = Calendar.getInstance(); // Created to get current year
        final String SQL = "SELECT i.ItemName, c.Name AS CustomerName, s.SalesDate, d.SellPrice, m.Name AS MagicType, i.DangerLevel "
                + "FROM Sales AS s "
                + "INNER JOIN SalesDetails AS d ON s.Id = d.SalesId "
                + "INNER JOIN Item AS i ON d.ItemName = i.ItemName "
                + "INNER JOIN Customer AS c ON s.CustomerId = c.Id "
                + "INNER JOIN Magic AS m ON i.MagicId = m.Id "
                + "WHERE YEAR(s.SalesDate) = ? and MONTH(s.SalesDate) = ?";

        File file = new File("monthly-report-" + month + "-" + currentTime.get(Calendar.YEAR) + ".txt");
        try (var writer = new FileWriter(file)) {
            writer.write(String.format(
                    "| %-50s | %-50s | %-10s | %-10s | %-11s | %-12s |%n",
                    "Name", "Customer Name", "Sales Date", "Price", "Magic Type", "Danger Level"
            ));
            try (var pstm = connection.prepareStatement(SQL)) {
                pstm.setInt(1, currentTime.get(Calendar.YEAR));
                pstm.setInt(2, month);
                try (var resultSet = pstm.executeQuery()) {
                    while (resultSet.next()) {
                        writeRow(resultSet, writer);
                    }
                }
            }
        }
    }

    private void writeRow(ResultSet resultSet, FileWriter writer) throws IOException, SQLException {
        writer.write(String.format(
                "| %-50s | %-50s | %-10s | %-10s | %-11s | %-12s |%n",
                resultSet.getString("ItemName"), resultSet.getString("CustomerName"),
                resultSet.getDate("SalesDate"), resultSet.getDouble("SellPrice"),
                resultSet.getString("MagicType"), resultSet.getInt("DangerLevel")
        ));
    }
}