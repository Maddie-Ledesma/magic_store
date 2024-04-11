package magic.store.data;

import magic.store.data.access.MagicDataAccess;
import magic.store.data.models.MagicModel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MagicDataCache {
    private final Map<String, Integer> magicMap = new LinkedHashMap<>();

    public MagicDataCache(Connection connection) throws SQLException {
        var allRows = MagicDataAccess.listAll(connection);
        allRows.forEach(magicModel -> magicMap.put(magicModel.getName().toUpperCase(), magicModel.getId()));
        System.out.println(magicMap);
    }

    public Integer getIdByName(String name) {
        return this.magicMap.get(name.toUpperCase());
    }

}
