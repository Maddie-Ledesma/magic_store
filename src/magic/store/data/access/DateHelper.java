package magic.store.data.access;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateHelper {

    public static Date fromLocalDate(LocalDate source) {
        return Date.from(source.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

}
