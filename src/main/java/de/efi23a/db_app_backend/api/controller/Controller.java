package de.efi23a.db_app_backend.api.controller;

import de.efi23a.db_app_backend.service.DBService;
import org.example.apidemo.dbapi.TimetablesApi;
import org.example.apidemo.dbpapimodell.StationData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class Controller {

    private DBService dbService;
    private TimetablesApi timetablesApi;

    private String apiSecret;


    public Controller(
            DBService dbService,
            TimetablesApi timetablesApi,
            @Value("${api.id}") String apiId,
            @Value("${api.secret}") String apiSecret
            ){

        this.timetablesApi = timetablesApi;
        this.dbService = dbService;

        timetablesApi.getApiClient().addDefaultHeader("DB-Client-Id", apiId);
        timetablesApi.getApiClient().addDefaultHeader("DB-Api-Key", apiSecret);
    }

    @GetMapping("/db/getNameByEvaNo")
    @CrossOrigin("http://localhost:4200")
    public String  getStationNameByEvaNo(@RequestParam String evaNo){
        List<StationData> stationData=  timetablesApi.stationPatternGet(evaNo).getStation();
        return dbService.getName(stationData);
    }

//    @GetMapping("/db")
//    @CrossOrigin("http://localhost:4200")
//    public String  getDeparturesByStationName(@RequestParam String name, String date, String hour) {
//        String station = timetablesApi.stationPatternGet(name).getStation();
//        timetablesApi.planEvaNoDateHourGet()
//
//    }

}
