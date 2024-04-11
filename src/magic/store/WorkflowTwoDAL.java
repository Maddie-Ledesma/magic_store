package magic.store;

import magic.store.connector.DbConnector;
import magic.store.data.MagicDataCache;
import magic.store.data.access.ItemDataAccess;
import magic.store.data.models.ItemModel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WorkflowTwoDAL {

    private final Connection connection;
    private MagicDataCache cache;
    public WorkflowTwoDAL(String user, String password) throws SQLException {
        var dbConnector = new DbConnector(user, password);
        this.connection = dbConnector.getConnection();
        this.cache = new MagicDataCache(this.connection);
    }

    public List<ItemModel> searchByFilters(
            String strDangerLevel,
            String magicType,
            String strPrice,
            String isGreaterThan
    ) throws SQLException {
        Integer dangerLevel = null;
        if (!strDangerLevel.trim().isEmpty()) {
            dangerLevel = Integer.parseInt(strDangerLevel);
            if (dangerLevel < 1 || dangerLevel > 5) {
                throw new RuntimeException("Invalid danger level: " + dangerLevel);
            }
        }
        Integer magicId = null;
        if (!magicType.trim().isEmpty()) {
            magicId = this.cache.getIdByName(magicType.trim().toUpperCase());
            if (magicId == null) {
                throw new RuntimeException("Invalid magic type: " + magicType);
            }
        }

        Double price = null;
        var isGreater = false;
        if (!strPrice.trim().isEmpty()) {
            price = Double.parseDouble(strPrice);
            isGreater = isGreaterThan.equalsIgnoreCase("YES");
        }

        if (dangerLevel == null && magicId == null && price == null) {
            throw new RuntimeException("Please provide at least one criteria");
        }

        return ItemDataAccess.listByCriteria(dangerLevel, magicId, price, isGreater, connection);
    }
}