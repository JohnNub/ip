package duke.tasks;

import duke.main.DukeException;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static java.time.temporal.TemporalAdjusters.next;

public class Event extends Task {
    private LocalDateTime start;
    private LocalDateTime end;
    private static final DateTimeFormatter FORMAT_LIST[] = {
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            DateTimeFormatter.ofPattern("dd-MMM-yyyy"),
            DateTimeFormatter.ofPattern("MM dd, yyyy"),
            DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm:ss"),
            DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss"),
            DateTimeFormatter.ofPattern("dd/MM/yy"),
            DateTimeFormatter.ofPattern("dd-MMM-yy")
    };

    public Event(String s, String startStr, String endStr) throws DukeException {
        super(s);
        tryReadDate(startStr, endStr);
        end = start;
    }

    /**
     * Attempts to convert the user supplied date string to a machine date
     * TODO implement end time
     * @param dateStr The string to be converted
     */
    private void tryReadDate(String dateStr, String date2) throws DukeException {
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
        if (dateStr.toLowerCase().contains("end")) {
            if (dateStr.toLowerCase().matches("end\\s*mon.*")) {
                this.start = LocalDateTime.now().with(next(DayOfWeek.MONDAY));
            } else if (dateStr.toLowerCase().matches("end\\s*tue.*")) {
                this.start = LocalDateTime.now().with(next(DayOfWeek.TUESDAY));
            } else if (dateStr.toLowerCase().matches("end\\s*wed.*")) {
                this.start = LocalDateTime.now().with(next(DayOfWeek.WEDNESDAY));
            } else if (dateStr.toLowerCase().matches("end\\s*thu.*")) {
                this.start = LocalDateTime.now().with(next(DayOfWeek.THURSDAY));
            } else if (dateStr.toLowerCase().matches("end\\s*fri.*")) {
                this.start = LocalDateTime.now().with(next(DayOfWeek.FRIDAY));
            } else if (dateStr.toLowerCase().matches("end\\s*sat.*")) {
                this.start = LocalDateTime.now().with(next(DayOfWeek.SATURDAY));
            } else if (dateStr.toLowerCase().matches("end\\s*sun.*")) {
                this.start = LocalDateTime.now().with(next(DayOfWeek.SUNDAY));
            }
            if(this.start !=null){
                this.start = this.start.withHour(23).withMinute(59).withSecond(59).withNano(0);
            }
        }
        if (this.start == null) {
            throw new DukeException();
        }
        // Parse Date2. relative timing now reflects "next weekday after start"
        for (DateTimeFormatter dtf : FORMAT_LIST) {
            try {
                result = LocalDateTime.parse(dateStr, dtf);
            } catch (Exception e) {
                continue;
            }
            if (result != null) {
                this.end = result;
                return;
            }
        }
        //Still failed to read date, try grammar interpretation
        LocalDateTime now = LocalDateTime.now();
        if (date2.toLowerCase().contains("next")) {
            if (date2.toLowerCase().matches("next\\s*mon.*")) {
                this.end = start.with(next(DayOfWeek.MONDAY));
            } else if (date2.toLowerCase().matches("next\\s*tue.*")) {
                this.end = start.with(next(DayOfWeek.TUESDAY));
            } else if (date2.toLowerCase().matches("next\\s*wed.*")) {
                this.end = start.with(next(DayOfWeek.WEDNESDAY));
            } else if (date2.toLowerCase().matches("next\\s*thu.*")) {
                this.end = start.with(next(DayOfWeek.THURSDAY));
            } else if (date2.toLowerCase().matches("next\\s*fri.*")) {
                this.end = start.with(next(DayOfWeek.FRIDAY));
            } else if (date2.toLowerCase().matches("next\\s*sat.*")) {
                this.end = start.with(next(DayOfWeek.SATURDAY));
            } else if (date2.toLowerCase().matches("next\\s*sun.*")) {
                this.end = start.with(next(DayOfWeek.SUNDAY));
            }
        }
        if (date2.toLowerCase().contains("end")) {
            if (date2.toLowerCase().matches("end\\s*mon.*")) {
                this.end = start.with(next(DayOfWeek.MONDAY));
            } else if (date2.toLowerCase().matches("end\\s*tue.*")) {
                this.end = start.with(next(DayOfWeek.TUESDAY));
            } else if (date2.toLowerCase().matches("end\\s*wed.*")) {
                this.end = start.with(next(DayOfWeek.WEDNESDAY));
            } else if (date2.toLowerCase().matches("end\\s*thu.*")) {
                this.end = start.with(next(DayOfWeek.THURSDAY));
            } else if (date2.toLowerCase().matches("end\\s*fri.*")) {
                this.end = start.with(next(DayOfWeek.FRIDAY));
            } else if (date2.toLowerCase().matches("end\\s*sat.*")) {
                this.end = start.with(next(DayOfWeek.SATURDAY));
            } else if (date2.toLowerCase().matches("end\\s*sun.*")) {
                this.end = start.with(next(DayOfWeek.SUNDAY));
            }
            if(this.end !=null){
                this.end = this.end.withHour(23).withMinute(59).withSecond(59).withNano(0);
            }
        }
        if (this.end == null) {
            throw new DukeException();
        }
        if(this.end.toEpochSecond(ZoneOffset.UTC)<this.start.toEpochSecond(ZoneOffset.UTC)){
            // An event cannot end before it starts
            throw new DukeException();
        }
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " Starts: " + start + " Ends: " + end;
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
