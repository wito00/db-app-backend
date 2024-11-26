package de.efi23a.db_app_backend.service;

import org.example.dbREst.api.DefaultApi;
import org.example.dbRest.model.Station;
import org.example.timetables.api.TimetablesApi;
import org.example.timetables.model.StationData;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DBService {
    TimetablesApi timetablesApi;
    DefaultApi defaultApi;

    public DBService(TimetablesApi timetablesApi, DefaultApi defaultApi) {
        this.timetablesApi = timetablesApi;
        this.defaultApi = defaultApi;
    }

    public String getName(List<StationData> stationData) {
        String name = stationData.getFirst().getName();
        return name;
    }

//    public String getEvoNoByStationName(String stationName) {
//        return timetablesApi.stationPatternGet(stationName).getStation().getFirst().getEva().toString();
//    }

    public String getEvoNoByStationName(String stationName) {
        List<Station> stationList = defaultApi.locationsGet(stationName, null, null, null, null, null, null, null,null);
        return stationList.getFirst().getId();
    }
}
