import java.time.LocalTime;

import org.json.simple.JSONObject;

public class Rate {
    private final LocalTime start;
    private final LocalTime end;
    private final long value;

    public Rate (JSONObject rate) {
        this.start = LocalTime.parse((String) rate.get("start"));
        this.end = LocalTime.parse((String) rate.get("end"));
        this.value = (long) rate.get("value");
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public long getValue() {
        return value;
    }

    public long getDurationInHours() {
        int startHour = start.getHour();
        int endHour = end.getHour();
        if (startHour < endHour) {
            return endHour - startHour;
        }
        return 24 - startHour + endHour;
    }
}
