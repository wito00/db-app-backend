package de.efi23a.db_app_backend.model;

import lombok.Data; // Lombok-Annotation zur automatischen Generierung von Gettern, Settern, etc.

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Data // Generiert Getter, Setter, toString(), equals() und hashCode() für die Klasse.
public class Departure {
    String name; // Name des Zuges/der Bahn.
    String departureTime; // Abfahrtszeit.
    String platform; // Gleis der Abfahrt.
    String destination; // Zielbahnhof.
    String typ; // Zugtyp (z.B. ICE, RE, S-Bahn).
    boolean elevator; // Gibt an, ob ein Aufzug am Gleis verfügbar ist.

    public Departure(String name, String departureTime, String platform, String destination, String typ, boolean elevator) {
        this.name = name;
        this.departureTime = dateTimeToTime(departureTime); // Konvertiert den Abfahrtszeitpunkt in die gewünschte Zeitdarstellung.
        this.platform = platform;
        this.destination = destination;
        this.typ = typ;
        this.elevator = elevator;
    }

    private String dateTimeToTime(String dateTime) {
        // Parst den übergebenen String als ZonedDateTime.
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateTime);
        // Definiert die Zeitzone "Europe/Berlin".
        ZoneId berlinZone = ZoneId.of("Europe/Berlin");
        // Konvertiert den ZonedDateTime in die Berliner Zeitzone.
        ZonedDateTime berlinTime = zonedDateTime.withZoneSameInstant(berlinZone);
        // Formatiert den ZonedDateTime als String im Format "HH:mm".
        return berlinTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}