import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.temporal.TemporalAdjusters.next;

public class Event extends Task {
    private LocalDateTime start;
    private LocalDateTime end;
    private static DateTimeFormatter FORMAT_LIST[] = {
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            DateTimeFormatter.ofPattern("dd-MMM-yyyy"),
            DateTimeFormatter.ofPattern("MM dd, yyyy"),
            DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm:ss"),
            DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss"),
            DateTimeFormatter.ofPattern("dd/MM/yy"),
            DateTimeFormatter.ofPattern("dd-MMM-yy")
    };

    public Event(String s, String startStr) {
        super(s);
        tryReadDate(startStr);
        end = start;
    }

    /**
     * tryReadDate - Attempts to convert the user supplied date string to a machine date
     * TODO implement end time
     * @param dateStr The string to be converted
     */
    private void tryReadDate(String dateStr) {
        LocalDateTime result = null;
        for (DateTimeFormatter dtf : FORMAT_LIST) {
            try {
                result = LocalDateTime.parse(dateStr, dtf);
            } catch (Exception e) {
                continue;
            }
            if (result != null) {
                this.start = result;
                return;
            }
        }
        //Still failed to read date, try grammar interpretation
        LocalDateTime now = LocalDateTime.now();
        if (dateStr.toLowerCase().contains("next")) {
            if (dateStr.toLowerCase().matches("next\\s*mon.*")) {
                this.start = LocalDateTime.now().with(next(DayOfWeek.MONDAY));
            } else if (dateStr.toLowerCase().matches("next\\s*tue.*")) {
                this.start = LocalDateTime.now().with(next(DayOfWeek.TUESDAY));
            } else if (dateStr.toLowerCase().matches("next\\s*wed.*")) {
                this.start = LocalDateTime.now().with(next(DayOfWeek.WEDNESDAY));
            } else if (dateStr.toLowerCase().matches("next\\s*thu.*")) {
                this.start = LocalDateTime.now().with(next(DayOfWeek.THURSDAY));
            } else if (dateStr.toLowerCase().matches("next\\s*fri.*")) {
                this.start = LocalDateTime.now().with(next(DayOfWeek.FRIDAY));
            } else if (dateStr.toLowerCase().matches("next\\s*sat.*")) {
                this.start = LocalDateTime.now().with(next(DayOfWeek.SATURDAY));
            } else if (dateStr.toLowerCase().matches("next\\s*sun.*")) {
                this.start = LocalDateTime.now().with(next(DayOfWeek.SUNDAY));
            }
        }
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " Starts: " + start;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
}
