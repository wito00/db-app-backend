package de.efi23a.db_app_backend.api.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import de.efi23a.db_app_backend.model.Arrival;
import de.efi23a.db_app_backend.model.Departure;
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
    private DefaultApi defaultApi;
    private TimetablesApi timetablesApi;
    private ObjectMapper jacksonObjectMapper;
    private DBService dbService;
    private FaStaApi faStaApi;


    public Controller(
            DefaultApi defaultApi,
            ObjectMapper jacksonObjectMapper,
            TimetablesApi timetablesApi,
            DBService dbService,
            FaStaApi faStaApi,

            @Value("${api.id}") String apiId,
            @Value("${api.secret}") String apiSecret
    ) {

        this.defaultApi = defaultApi;
        this.jacksonObjectMapper = jacksonObjectMapper;
        this.timetablesApi = timetablesApi;
        this.dbService = dbService;
        this.faStaApi = faStaApi;

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
        List<Arrival> arrivals = dbService.getArrivalsByEva(eva);
        Boolean hasElevator = dbService.hasElevatorByEva(eva);
        return new StationOverview(stationName, hasElevator, departures, arrivals);
    }

    @GetMapping("/arrivals")
    @CrossOrigin("http://localhost:4200")
    public List<Arrival> arrivals(String eva) {
//        ArrivalWrapper arrivalWrapper = defaultApi.stopsIdArrivalsGet( eva, null, null, null, null, null, null,null,null);
//        return arrivalWrapper.getArrivals();
        return dbService.getArrivalsByEva(eva);
    }


}
