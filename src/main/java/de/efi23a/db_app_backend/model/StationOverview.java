package de.efi23a.db_app_backend.model;

import lombok.Data;

import java.util.List;

@Data
public class StationOverview {
    String stationName;
    String date;
    List<Departure> nextDepartures;
    List<Arrival> nextArrivals;

    public StationOverview(String stationName, String date, List<Departure> departureList, List<Arrival> arrivalList) {
        this.stationName = stationName;
        this.date = date;
        this.nextDepartures = departureList;
        this.nextArrivals = arrivalList;


    }
}
