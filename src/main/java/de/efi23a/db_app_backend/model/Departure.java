package de.efi23a.db_app_backend.model;

import lombok.Data;

@Data
public class Departure {
    String name;
    String departureTime;
    String destination;
    String typ;
    public Departure(String name, String departureTime, String destination, String typ) {
        this.name = name;
        this.departureTime = departureTime;
        this.destination = destination;
        this.typ = typ;
    }
}