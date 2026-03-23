package Domain.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Partida {
    private String namePlay;
    private LocalDateTime dateTime;
    private long durationTime;

    public Partida(String namePlay, LocalDateTime dateTime, long durationTime) {
        this.namePlay = namePlay;
        this.dateTime = dateTime;
        this.durationTime = durationTime;
    }

    public String getNamePlay() {
        return namePlay;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public long getDurationTime() {
        return durationTime;
    }
}
