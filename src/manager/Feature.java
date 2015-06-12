package manager;

/**
 * Created by Nick on 6/5/2015.
 */
import org.joda.time.*;
import  org.json.*;

public abstract class Feature {
    public abstract JSONObject toJSON();
    public static String readableDate(DateTime dt){
        String result = null;
        if (dt != null) {
            result =  String.format("%d-%d-%d %d:%d:%d", dt.year().get(), dt.monthOfYear().get(), dt.dayOfMonth().get(), dt.hourOfDay().get(), dt.minuteOfHour().get(), dt.secondOfMinute().get());
        }
        return result;
    }
}
