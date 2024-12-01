package de.efi23a.db_app_backend.api.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import de.efi23a.db_app_backend.model.Arrival;
import de.efi23a.db_app_backend.model.Departure;
import de.efi23a.db_app_backend.model.Elevator;
import de.efi23a.db_app_backend.model.StationOverview;
import de.efi23a.db_app_backend.service.DBService;
import org.example.dbREst.api.DefaultApi;
import org.example.faSta.api.FaStaApi;
import org.example.timetables.api.TimetablesApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {

    private DBService dbService;

    public Controller(
            DefaultApi defaultApi,
            ObjectMapper jacksonObjectMapper,
            TimetablesApi timetablesApi,
            DBService dbService,
            FaStaApi faStaApi,
            @Value("${api.id}") String apiId,
            @Value("${api.secret}") String apiSecret
    ) {

        this.dbService = dbService;
        //authentication for timetables and staDa API
        timetablesApi.getApiClient().addDefaultHeader("DB-Client-Id", apiId);
        timetablesApi.getApiClient().addDefaultHeader("DB-Api-Key", apiSecret);
        faStaApi.getApiClient().addDefaultHeader("DB-Client-Id", apiId);
        faStaApi.getApiClient().addDefaultHeader("DB-Api-Key", apiSecret);
    }

    @GetMapping("/overview")
    @CrossOrigin("http://localhost:4200")
    public StationOverview getStationOverviewByStationName(String pattern) {
        List<String> evaAndName = dbService.getEvaAndNameByPattern(pattern);
        String eva = evaAndName.get(0);
        String stationName = evaAndName.get(1);
        List<Departure> departures = dbService.getDeparturesByEva(eva);
        departures = dbService.sortByDepartureTime(departures);
        List<Arrival> arrivals = dbService.getArrivalsByEva(eva);
        arrivals = dbService.sortByArrivalTime(arrivals);
        List<Elevator> elevators= dbService.hasElevatorByEva(eva);
        String date = dbService.getCurrentDate();
        return new StationOverview(stationName, elevators, date, departures, arrivals);
    }


}
