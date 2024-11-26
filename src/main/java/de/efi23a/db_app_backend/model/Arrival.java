package de.efi23a.db_app_backend.model;

import lombok.Data;

@Data
public class Arrival {
    String name;
    String departureTime;
    String origin;
    String typ;
    public Arrival(String name, String departureTime, String origin, String typ) {
        this.name = name;
        this.departureTime = departureTime;
        this.origin = origin;
        this.typ = typ;
    }
}
