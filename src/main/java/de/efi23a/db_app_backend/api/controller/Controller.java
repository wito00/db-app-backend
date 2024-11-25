package de.efi23a.db_app_backend.api.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.efi23a.db_app_backend.model.Departure;
import de.efi23a.db_app_backend.model.DepartureList;
import de.efi23a.db_app_backend.service.DBService;
import org.example.dbREst.api.DefaultApi;
import org.example.faSta.api.FaStaApi;
import org.example.faSta.model.Facility;
import org.example.timetables.api.TimetablesApi;
import org.example.timetables.model.StationData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @GetMapping("/db/stationData")
    @CrossOrigin("http://localhost:4200")
    public Object getStationDataById(@RequestParam String id) {
        Object object = defaultApi.stopsIdGet(id, null, null, null);
        Map<String, Object> map = jacksonObjectMapper.convertValue(object, new TypeReference<Map<String, Object>>() {
        });
        String type = map.get("type").toString();
        System.out.println(type);
        return object;
    }

    @GetMapping("/db/departures")
    @CrossOrigin("http://localhost:4200")
    public DepartureList getDeparturesByStationName(@RequestParam String stationName) {
        String id = dbService.getEvoNoByStationName(stationName);
        Object object = defaultApi.stopsIdDeparturesGet(id, null, null, null, 10, null, null, null, null);
        Map<String, Object> map = jacksonObjectMapper.convertValue(object, new TypeReference<Map<String, Object>>() {
        });
        List<Map<String, Object>> departuresMapList = (List<Map<String, Object>>) map.get("departures");
        List<Departure> departures = departuresMapList.stream().map(departureMap -> jacksonObjectMapper.convertValue(departureMap, Departure.class)).collect(Collectors.toList());
        DepartureList departureList = new DepartureList();
        departureList.setDepartureList(departures);
        return departureList;
    }

//    @GetMapping("/db/autocomplete")
//    @CrossOrigin("http//localhost:4200")
//    public void autocomplete(String pattern) {
//        defaultApi.stationsGet(pattern, 3, false, true);
//    }

    @GetMapping("/db/getName")
    @CrossOrigin("http://localhost:4200")
    public String getStationNameByEva(@RequestParam String evaNo) {
        List<StationData> stationData = timetablesApi.stationPatternGet(evaNo).getStation();
        return dbService.getName(stationData);
    }

    @GetMapping("/db/test")
    @CrossOrigin("http://localhost:4200")
    public void getDeparturesByEva(@RequestParam String id) {
        //String id = dbService.getEvoNoByStationName(stationName);
        Object object = defaultApi.stopsIdDeparturesGet(
                id, null, null, null, null, null, null, null, null
        );
    }

    @GetMapping("db/elevator")
    @CrossOrigin("http://localhost:4200")
    public Boolean hasElevatorByEva(String eva) {
        Object object = defaultApi.stationsIdGet(eva);
        Map<String, Object> map = jacksonObjectMapper.convertValue(object, new TypeReference<Map<String, Object>>() {
        });
        String id = map.get("nr").toString();
        List<Facility> facilities;
        try {
            facilities = faStaApi.findStationByStationNumber(Long.parseLong(id)).getFacilities();
        } catch (Exception e) {
            return false;
        }
           assert facilities != null;

        for (Facility facility : facilities) {
            if (facility.getType() == Facility.TypeEnum.ELEVATOR && facility.getState() == Facility.StateEnum.ACTIVE) {
                return true;
            }
        }
        return false;
    }
}
