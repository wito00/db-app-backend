package de.efi23a.db_app_backend.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import de.efi23a.db_app_backend.model.Arrival;
import de.efi23a.db_app_backend.model.Departure;
import de.efi23a.db_app_backend.model.StationOverview;
import de.efi23a.db_app_backend.service.DBService;
import org.example.faSta.api.FaStaApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.List;

@RestController // Kennzeichnet diese Klasse als REST-Controller, der HTTP-Anfragen entgegennimmt.
public class Controller {

    private DBService dbService; // Service-Klasse für Datenbankinteraktionen.

    public Controller(
            ObjectMapper jacksonObjectMapper,
            DBService dbService,
            FaStaApi faStaApi,
            @Value("${api.id}") String apiId,
            @Value("${api.secret}") String apiSecret
    ) {

        this.dbService = dbService;
        // Authentifizierung für die Fahrplan- und StaDa-API.
        faStaApi.getApiClient().addDefaultHeader("DB-Client-Id", apiId);
        faStaApi.getApiClient().addDefaultHeader("DB-Api-Key", apiSecret);
    }

    @GetMapping("/overview") // Definiert einen GET-Endpunkt unter "/overview".
    @CrossOrigin("http://localhost:4200") // Erlaubt Cross-Origin-Anfragen von "http://localhost:4200".
    public StationOverview getStationOverviewByStationName(String pattern, OffsetDateTime offsetDateTime) {
        // Ruft die EVA-Nummer und den Namen des Bahnhofs basierend auf dem Suchmuster ab.
        List<String> evaAndName = dbService.getEvaAndNameByPattern(pattern);
        String eva = evaAndName.get(0); // EVA-Nummer.
        String stationName = evaAndName.get(1); // Bahnhofname.

        // Ruft die Abfahrten für den Bahnhof und den angegebenen Zeitpunkt ab.
        List<Departure> departures = dbService.getDeparturesByEva(eva, offsetDateTime);
        departures = dbService.sortByDepartureTime(departures);

        // Ruft die Ankünfte für den Bahnhof und den angegebenen Zeitpunkt ab.
        List<Arrival> arrivals = dbService.getArrivalsByEva(eva, offsetDateTime);
        arrivals = dbService.sortByArrivalTime(arrivals);

        // Formatiert das Datum für die Ausgabe.
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = offsetDateTime.format(dtf);

        // Gibt eine StationOverview-Instanz mit den abgerufenen Daten zurück.
        return new StationOverview(stationName, date, departures, arrivals);
    }
}
