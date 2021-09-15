import java.io.IOException;
import java.io.FileReader;
import java.io.Reader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {
    public static void main(String[] args) {
        JSONParser jsonParser = new JSONParser();

        try (Reader reader = new FileReader("src/main/resources/sample-input.json")) {
            JSONObject input = (JSONObject) jsonParser.parse(reader);

            Shift shift = new Shift((JSONObject) input.get("shift"));
            RoboRate roboRate = new RoboRate((JSONObject) input.get("roboRate"));

            JSONObject output = new JSONObject();
            PaymentCalculator calculator = new PaymentCalculator();
            output.put("value", calculator.calculate(shift, roboRate));
            System.out.println(output.toJSONString());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
