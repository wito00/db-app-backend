package de.efi23a.db_app_backend.model;

import lombok.Data;

import java.util.List;
@Data
public class StationOverview {
    String name;
    Boolean elevator;
    List<Departure> nextDepartures;
    List<Arrival> nextArrivals;
    public StationOverview(String name, Boolean elevator, List<Departure> departureList, List<Arrival> arrivalList){
        this.name = name;
        this.elevator = elevator;
        this.nextDepartures = departureList;
        this.nextArrivals = arrivalList;

    }
}
