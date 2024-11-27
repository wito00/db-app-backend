package de.efi23a.db_app_backend.model;

import lombok.Data;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class Arrival {
    String name;
    String arrivalTime;
    String origin;
    String typ;

    public Arrival(String name, String departureDateTime, String origin, String typ) {

        this.name = name;
        this.arrivalTime = dateTimeToTime(departureDateTime);
        this.origin = origin;
        this.typ = typ;
    }

    private String dateTimeToTime(String dateTime) {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateTime);
        ZoneId berlinZone = ZoneId.of("Europe/Berlin");
        ZonedDateTime berlinTime = zonedDateTime.withZoneSameInstant(berlinZone);
        return berlinTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}
