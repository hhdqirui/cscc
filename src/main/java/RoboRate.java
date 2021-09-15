import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

public class RoboRate {
    protected static final String WEEKDAY_DAY = "weekdayDay";
    protected static final String WEEKDAY_NIGHT = "weekdayNight";
    protected static final String WEEKEND_DAY = "weekendDay";
    protected static final String WEEKEND_NIGHT = "weekendNight";

    private Map<String, Rate> rates = new HashMap<>();

    public RoboRate (JSONObject roboRate) {
        rates.put(WEEKDAY_DAY, new Rate((JSONObject) roboRate.get("standardDay")));
        rates.put(WEEKDAY_NIGHT, new Rate((JSONObject) roboRate.get("standardNight")));
        rates.put(WEEKEND_DAY, new Rate((JSONObject) roboRate.get("extraDay")));
        rates.put(WEEKEND_NIGHT, new Rate((JSONObject) roboRate.get("extraNight")));
    }

    public Rate getStandardDay() {
        return rates.get(WEEKDAY_DAY);
    }

    public Rate getStandardNight() {
        return rates.get(WEEKDAY_NIGHT);
    }

    public Rate getExtraDay() {
        return rates.get(WEEKEND_DAY);
    }

    public Rate getExtraNight() {
        return rates.get(WEEKEND_NIGHT);
    }

}
