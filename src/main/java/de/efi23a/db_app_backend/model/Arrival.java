package de.efi23a.db_app_backend.model;

import lombok.Data;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class Arrival {
    String name;
    String arrivalTime;
    String platform;
    String origin;
    String typ;
    boolean elevator;

    public Arrival(String name, String arrivalDateTime,String platform, String origin, String typ, boolean elevator) {

        this.name = name;
        this.arrivalTime = dateTimeToTime(arrivalDateTime);
        this.platform = platform;
        this.origin = origin;
        this.typ = typ;
        this.elevator = elevator;
    }

    private String dateTimeToTime(String dateTime) {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateTime);
        ZoneId berlinZone = ZoneId.of("Europe/Berlin");
        ZonedDateTime berlinTime = zonedDateTime.withZoneSameInstant(berlinZone);
        return berlinTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}
