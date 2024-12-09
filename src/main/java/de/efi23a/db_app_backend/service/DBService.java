package de.efi23a.db_app_backend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.efi23a.db_app_backend.model.Arrival;
import de.efi23a.db_app_backend.model.Departure;
import org.example.dbREst.api.DefaultApi;
import org.example.dbRest.model.ArrivalWrapper;
import org.example.dbRest.model.DepartureWrapper;
import org.example.dbRest.model.Station;
import org.example.faSta.api.FaStaApi;
import org.example.faSta.model.Facility;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service // Kennzeichnet diese Klasse als Service-Klasse in Spring.
public class DBService {
    DefaultApi defaultApi; // API-Client für den Zugriff auf die DB Rest-API.
    ObjectMapper jacksonObjectMapper; // Objekt-Mapper für JSON-Serialisierung/Deserialisierung.
    FaStaApi faStaApi; // API-Client für den Zugriff auf die FaSta-API.

    public DBService(
            DefaultApi defaultApi, // Instanz des DefaultApi.
            ObjectMapper jacksonObjectMapper, // Instanz des ObjectMapper.
            FaStaApi faStaApi // Instanz des FaStaApi.
    ) {
        this.defaultApi = defaultApi;
        this.jacksonObjectMapper = jacksonObjectMapper;
        this.faStaApi = faStaApi;
    }

    // Ruft die EVA-Nummer und den Namen eines Bahnhofs basierend auf einem Suchmuster ab.
    public List<String> getEvaAndNameByPattern(String stationName) {
        // Ruft eine Liste von Stationen von der DB Rest-API ab.
        List<Station> stationList = defaultApi.locationsGet(stationName, null, null, null, null, null, null, null, null);
        String eva = stationList.getFirst().getId(); // EVA-Nummer der ersten Station.
        String name = stationList.getFirst().getName(); // Name der ersten Station.
        List<String> evaAndName = new ArrayList<>();
        evaAndName.add(eva);
        evaAndName.add(name);
        return evaAndName; // Gibt eine Liste mit EVA-Nummer und Namen zurück.
    }

    // Ruft die Abfahrten für einen Bahnhof und einen bestimmten Zeitpunkt ab.
    public List<Departure> getDeparturesByEva(String eva, OffsetDateTime offsetDateTime) {
        // Ruft die Abfahrten von der DB Rest-API ab.
        DepartureWrapper departureWrapper = defaultApi.stopsIdDeparturesGet(eva, offsetDateTime, null, 180, 100, null, null, null, null);
        List<org.example.dbRest.model.Departure> departureList = departureWrapper.getDepartures(); // Liste der Abfahrten.
        List<Departure> departureListFrontend = new ArrayList<>(); // Liste für die aufbereiteten Abfahrten.
        List<String> platformsWithElevators = getListOfPlatformsWithElevatorByEva(eva); // Liste der Gleise mit Aufzug.

        for (org.example.dbRest.model.Departure departure : departureList) {
            // Filtert Busse, Straßenbahnen und U-Bahnen heraus.
            if (departure.getLine().getProductName().equals("Bus") || departure.getLine().getProductName().equals("STR") || departure.getLine().getProductName().equals("U")) {
                continue;
            }
            if (departureListFrontend.size() >= 10) { // Begrenzt die Anzahl der Abfahrten auf 10.
                return departureListFrontend;
            }
            // Extrahiert die relevanten Informationen aus der Abfahrt.
            String name = departure.getLine().getName();
            String departureTime = departure.getPlannedWhen().toString();
            String platform = departure.getPlannedPlatform();
            String destination = departure.getDestination().getName();
            String typ = departure.getLine().getProductName();
            boolean stepless = false;

            if (platformsWithElevators.contains(platform)) { // Prüft, ob das Gleis einen Aufzug hat.
                stepless = true;
            }
            // Fügt die aufbereitete Abfahrt zur Liste hinzu.
            departureListFrontend.add(new Departure(name, departureTime, platform, destination, typ, stepless));
        }
        return departureListFrontend; // Gibt die Liste der aufbereiteten Abfahrten zurück.
    }

    // Ruft die Ankünfte für einen Bahnhof und einen bestimmten Zeitpunkt ab.
    public List<Arrival> getArrivalsByEva(String eva, OffsetDateTime offsetDateTime) {
        // Ruft die Ankünfte von der DB Rest-API ab.
        ArrivalWrapper arrivalWrapper = defaultApi.stopsIdArrivalsGet(eva, offsetDateTime, null, 180, 100, null, null, null, null);
        List<org.example.dbRest.model.Arrival> arrivalList = arrivalWrapper.getArrivals(); // Liste der Ankünfte.
        List<Arrival> arrivalListFrontend = new ArrayList<>(); // Liste für die aufbereiteten Ankünfte.
        List<String> platformsWithElevators = getListOfPlatformsWithElevatorByEva(eva); // Liste der Gleise mit Aufzug.

        for (org.example.dbRest.model.Arrival arrival : arrivalList) {
            // Filtert Busse, Straßenbahnen und U-Bahnen heraus.
            if (arrival.getLine().getProductName().equals("Bus") || arrival.getLine().getProductName().equals("STR") || arrival.getLine().getProductName().equals("U")) {
                continue;
            }
            if (arrivalListFrontend.size() >= 10) { // Begrenzt die Anzahl der Ankünfte auf 10.
                return arrivalListFrontend;
            }
            // Extrahiert die relevanten Informationen aus der Ankunft.
            String name = arrival.getLine().getName();
            String departureTime = arrival.getPlannedWhen().toString();
            String platform = arrival.getPlannedPlatform();
            String origin = "";
            Boolean stepless = false;
            if (platformsWithElevators.contains(platform)) { // Prüft, ob das Gleis einen Aufzug hat.
                stepless = true;
            }
            assert arrival.getOrigin() != null;
            if (arrival.getOrigin().getName() != null) {
                origin = arrival.getOrigin().getName();

            }

            String typ = arrival.getLine().getProductName();
            // Fügt die aufbereitete Ankunft zur Liste hinzu.
            arrivalListFrontend.add(new Arrival(name, departureTime, platform, origin, typ, stepless));
        }
        return arrivalListFrontend; // Gibt die Liste der aufbereiteten Ankünfte zurück.
    }

    // Ruft eine Liste der Gleise mit Aufzug für einen Bahnhof ab.
    public List<String> getListOfPlatformsWithElevatorByEva(String eva) {
        Object object = defaultApi.stationsIdGet(eva); // Ruft Bahnhofsinformationen von der DB Rest-API ab.
        // Konvertiert das Objekt in eine Map.
        Map<String, Object> map = jacksonObjectMapper.convertValue(object, new TypeReference<Map<String, Object>>() {
        });
        String id = map.get("nr").toString(); // Extrahiert die Bahnhofsnr.
        List<Facility> facilities;
        try {
            // Ruft die Ausstattungsmerkmale des Bahnhofs von der FaSta-API ab.
            facilities = faStaApi.findFacilities(null, null, null, Long.parseLong(id), null);
        } catch (Exception e) {
            throw e;
        }
        assert facilities != null;

        List<String> platformsWithElevator = new ArrayList<>();
        for (Facility facility : facilities) {
            // Sucht nach Aufzügen in den Ausstattungsmerkmalen.
            if (facility.getType() == Facility.TypeEnum.ELEVATOR) {
                String[] parts = facility.getDescription().split(" ");
                for (int i = 0; i < parts.length; i++) {
                    if (parts[i].equalsIgnoreCase("Gleis") && i + 1 < parts.length) {
                        String[] platformParts = parts[i + 1].split("/");
                        for (String platform : platformParts) {
                            platformsWithElevator.add(platform); // Fügt das Gleis zur Liste hinzu.
                        }
                    }
                }

            }
        }
        return platformsWithElevator; // Gibt die Liste der Gleise mit Aufzug zurück.
    }

    // Gibt das aktuelle Datum im Format "dd.MM.yyyy" zurück.
    public String getCurrentDate() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return date.format(formatter);
    }

    // Sortiert eine Liste von Ankünften nach Ankunftszeit.
    public List<Arrival> sortByArrivalTime(List<Arrival> arrivals) {
        return arrivals.stream()
                .sorted((t1, t2) -> LocalTime.parse(t1.getArrivalTime())
                        .compareTo(LocalTime.parse(t2.getArrivalTime())))
                .collect(Collectors.toList());
    }

    // Sortiert eine Liste von Abfahrten nach Abfahrtszeit.
    public List<Departure> sortByDepartureTime(List<Departure> departures) {
        return departures.stream()
                .sorted((t1, t2) -> LocalTime.parse(t1.getDepartureTime())
                        .compareTo(LocalTime.parse(t2.getDepartureTime())))
                .collect(Collectors.toList());
    }
}