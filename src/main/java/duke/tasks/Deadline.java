package duke.tasks;

import duke.main.DukeException;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.temporal.TemporalAdjusters.next;

public class Deadline extends Task {
    private LocalDateTime due;
    private static final DateTimeFormatter FORMAT_LIST[] = {
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            DateTimeFormatter.ofPattern("dd-MMM-yyyy"),
            DateTimeFormatter.ofPattern("MM dd, yyyy"),
            DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm:ss"),
            DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss"),
            DateTimeFormatter.ofPattern("dd/MM/yy"),
            DateTimeFormatter.ofPattern("dd-MMM-yy")
    };

    public Deadline(String s, String deadline) throws DukeException {
        super(s);
        tryReadDate(deadline);
    }

    /**
     * Attempts to convert the user supplied date string to a machine date
     * @param dateStr The string to be converted
     */
    private void tryReadDate(String dateStr) throws DukeException {
        LocalDateTime result = null;
        for (DateTimeFormatter dtf : FORMAT_LIST) {
            try {
                result = LocalDateTime.parse(dateStr, dtf);
            } catch (Exception e) {
                continue;
            }
            if (result != null) {
                this.due = result;
                return;
            }
        }
        //Still failed to read date, try grammar interpretation
        LocalDateTime now = LocalDateTime.now();
        if (dateStr.toLowerCase().contains("next")) {
            if (dateStr.toLowerCase().matches("next\\s*mon.*")) {
                this.due = LocalDateTime.now().with(next(DayOfWeek.MONDAY));
            } else if (dateStr.toLowerCase().matches("next\\s*tue.*")) {
                this.due = LocalDateTime.now().with(next(DayOfWeek.TUESDAY));
            } else if (dateStr.toLowerCase().matches("next\\s*wed.*")) {
                this.due = LocalDateTime.now().with(next(DayOfWeek.WEDNESDAY));
            } else if (dateStr.toLowerCase().matches("next\\s*thu.*")) {
                this.due = LocalDateTime.now().with(next(DayOfWeek.THURSDAY));
            } else if (dateStr.toLowerCase().matches("next\\s*fri.*")) {
                this.due = LocalDateTime.now().with(next(DayOfWeek.FRIDAY));
            } else if (dateStr.toLowerCase().matches("next\\s*sat.*")) {
                this.due = LocalDateTime.now().with(next(DayOfWeek.SATURDAY));
            } else if (dateStr.toLowerCase().matches("next\\s*sun.*")) {
                this.due = LocalDateTime.now().with(next(DayOfWeek.SUNDAY));
            }
        }
        if (dateStr.toLowerCase().contains("end")) {
            if (dateStr.toLowerCase().matches("end\\s*mon.*")) {
                this.due = LocalDateTime.now().with(next(DayOfWeek.MONDAY));
            } else if (dateStr.toLowerCase().matches("end\\s*tue.*")) {
                this.due = LocalDateTime.now().with(next(DayOfWeek.TUESDAY));
            } else if (dateStr.toLowerCase().matches("end\\s*wed.*")) {
                this.due = LocalDateTime.now().with(next(DayOfWeek.WEDNESDAY));
            } else if (dateStr.toLowerCase().matches("end\\s*thu.*")) {
                this.due = LocalDateTime.now().with(next(DayOfWeek.THURSDAY));
            } else if (dateStr.toLowerCase().matches("end\\s*fri.*")) {
                this.due = LocalDateTime.now().with(next(DayOfWeek.FRIDAY));
            } else if (dateStr.toLowerCase().matches("end\\s*sat.*")) {
                this.due = LocalDateTime.now().with(next(DayOfWeek.SATURDAY));
            } else if (dateStr.toLowerCase().matches("end\\s*sun.*")) {
                this.due = LocalDateTime.now().with(next(DayOfWeek.SUNDAY));
            }
            if(this.due !=null){
                this.due = this.due.withHour(23).withMinute(59).withSecond(59).withNano(0);
            }
        }
        if (this.due == null) {
            throw new DukeException();
        }
    }

    public LocalDateTime getDue() {
        return due;
    }

    public void setDue(LocalDateTime due) {
        this.due = due;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " Due: " + due;
    }
}
