package de.efi23a.db_app_backend.model;

import lombok.Data;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class Departure {
    String name;
    String departureTime;
    String platform;
    String destination;
    String typ;
    boolean elevator;

    public Departure(String name, String departureTime, String platform, String destination, String typ, boolean elevator) {
        this.name = name;
        this.departureTime = dateTimeToTime(departureTime);
        this.platform = platform;
        this.destination = destination;
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