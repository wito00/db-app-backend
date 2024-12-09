package de.efi23a.db_app_backend.model;

import lombok.Data; // Lombok-Annotation zur automatischen Generierung von Gettern, Settern, etc.

import java.util.List;

@Data // Generiert Getter, Setter, toString(), equals() und hashCode() für die Klasse.
public class StationOverview {
    String stationName; // Name des Bahnhofs.
    String date; // Datum der Abfrage.
    List<Departure> nextDepartures; // Liste der nächsten Abfahrten.
    List<Arrival> nextArrivals; // Liste der nächsten Ankünfte.

    public StationOverview(String stationName, String date, List<Departure> departureList, List<Arrival> arrivalList) {
        this.stationName = stationName;
        this.date = date;
        this.nextDepartures = departureList;
        this.nextArrivals = arrivalList;
    }
}