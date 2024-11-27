package de.efi23a.db_app_backend.model;

import lombok.Data;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class Departure {
    String name;
    String departureTime;
    String destination;
    String typ;

    public Departure(String name, String departureTime, String destination, String typ) {
        this.name = name;
        this.departureTime = dateTimeToTime(departureTime);
        this.destination = destination;
        this.typ = typ;
    }

    private String dateTimeToTime(String dateTime) {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateTime);
        ZoneId berlinZone = ZoneId.of("Europe/Berlin");
        ZonedDateTime berlinTime = zonedDateTime.withZoneSameInstant(berlinZone);
        return berlinTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }


}