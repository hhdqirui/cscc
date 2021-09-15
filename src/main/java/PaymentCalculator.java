import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class PaymentCalculator {

    private static final long HOUR_IN_MINUTES = 60;
    private static final long EIGHT_HOURS_IN_MINUTES = 8 * 60;

    public PaymentCalculator () {}

    public long calculate (Shift shift, RoboRate roboRate) {
        LocalDateTime startTime = shift.getStart();
        LocalDateTime endTime = shift.getEnd();

        long value = 0;
        int eightHourCount = 0;
        boolean restAtEndTime = false;

        // First go to the nearest hour. Then calculate hour by hour.
        while (startTime.isBefore(endTime)) {
            if (eightHourCount + HOUR_IN_MINUTES > EIGHT_HOURS_IN_MINUTES) {
                long remainingTime = EIGHT_HOURS_IN_MINUTES - eightHourCount;
                LocalDateTime nextTime = startTime.plusMinutes(remainingTime);
                value += calculatePayment(startTime, nextTime, roboRate);
                startTime = nextTime.plusHours(1);
                eightHourCount = 0;
                restAtEndTime = true;
                continue;
            }
            if (startTime.plusHours(1).isAfter(endTime) || startTime.plusHours(1).isEqual(endTime)) {
                restAtEndTime = false;
                startTime = startTime.plusHours(1);
                break;
            }
            if (startTime.getMinute() > 0) {
                LocalDateTime nearestNextHourTime = startTime.plusHours(1).minusMinutes(startTime.getMinute());
                value += calculatePayment(startTime, nearestNextHourTime, roboRate);
                eightHourCount += HOUR_IN_MINUTES - startTime.getMinute();
                startTime = nearestNextHourTime;
            } else {
                LocalDateTime nearestNextHourTime = startTime.plusHours(1);
                value += calculatePayment(startTime, nearestNextHourTime, roboRate);
                eightHourCount += HOUR_IN_MINUTES;
                startTime = nearestNextHourTime;
            }
            restAtEndTime = false;
        }
        if (!restAtEndTime) {
            value += calculatePayment(startTime.minusHours(1), endTime, roboRate);
        }
        return value;
    }

    private boolean isWeekDay (LocalDateTime time) {
        return time.getDayOfWeek().getValue() <= DayOfWeek.FRIDAY.getValue();
    }

    private boolean isDayTime (LocalDateTime time, Rate rate) {
        return (time.isAfter(LocalDateTime.of(time.toLocalDate(), rate.getStart()))
                || time.isEqual(LocalDateTime.of(time.toLocalDate(), rate.getStart())))
                && time.isBefore(LocalDateTime.of(time.toLocalDate(), rate.getEnd()));
    }

    private long calculatePayment(LocalDateTime start, LocalDateTime end, RoboRate roboRate) {
        long duration = start.until(end, ChronoUnit.MINUTES);

        if (isWeekDay(start)) {
            if (isDayTime(start, roboRate.getStandardDay())) {
                return duration * roboRate.getStandardDay().getValue();
            } else {
                return duration * roboRate.getStandardNight().getValue();
            }
        } else {
            if (isDayTime(start, roboRate.getExtraDay())) {
                return duration * roboRate.getExtraDay().getValue();
            } else {
                return duration * roboRate.getExtraNight().getValue();
            }
        }
    }
}
