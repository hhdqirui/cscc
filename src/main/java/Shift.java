import org.json.simple.JSONObject;

import java.time.LocalDateTime;

public class Shift {
    private final LocalDateTime start;
    private final LocalDateTime end;

    public Shift(JSONObject object) {
        this.start = LocalDateTime.parse((String) object.get("start"));
        this.end = LocalDateTime.parse((String) object.get("end"));
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }
}
