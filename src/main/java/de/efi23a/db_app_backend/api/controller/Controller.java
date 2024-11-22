package de.efi23a.db_app_backend.api.controller;

//import de.efi23a.db_app_backend.service.DBService;
//import org.example.apidemo.dbapi.TimetablesApi;
//import org.example.apidemo.dbpapimodell.StationData;
//import org.example.apidemo.dbpapimodell.Timetable;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.efi23a.db_app_backend.model.Departure;
import de.efi23a.db_app_backend.model.DepartureList;
import org.example.apidemo.dbapi.DefaultApi;
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
    private final ObjectMapper jacksonObjectMapper;


    public Controller(
            DefaultApi defaultApi,
            ObjectMapper jacksonObjectMapper) {

        this.defaultApi = defaultApi;
        this.jacksonObjectMapper = jacksonObjectMapper;
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
    public DepartureList getDeparturesById(@RequestParam String id) {
        Object object = defaultApi.stopsIdDeparturesGet(id, null, null, null, null, null, null, null, null);
        Map<String, Object> map = jacksonObjectMapper.convertValue(object, new TypeReference<Map<String, Object>>() {
        });
        List<Map<String, Object>> departuresMapList = (List<Map<String, Object>>) map.get("departures");
        List<Departure> departures = departuresMapList.stream().map(departureMap -> jacksonObjectMapper.convertValue(departureMap, Departure.class)).collect(Collectors.toList());
        DepartureList departureList = new DepartureList();
        departureList.setDepartureList(departures);
        return departureList;
    }

    @GetMapping("/db/autocomplete")
    @CrossOrigin("http//localhost:4200")
    public void autocomplete(String pattern){
        defaultApi.stationsGet(pattern, 3, false, true);


    }

}
