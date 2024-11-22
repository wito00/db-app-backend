//package de.efi23a.db_app_backend.service;
//
//import org.example.apidemo.ApiClient;
//import org.example.apidemo.dbapi.TimetablesApi;
//import org.example.apidemo.dbpapimodell.StationData;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class DBService {
//    TimetablesApi timetablesApi;
//
//    public DBService(TimetablesApi timetablesApi){
//        this.timetablesApi = timetablesApi;
//    }
//
//    public String getName(List<StationData> stationData) {
//        String name = stationData.getFirst().getName();
//        return name;
//    }
//
//    public String getEvoNoByStationName(String stationName) {
//        return timetablesApi.stationPatternGet(stationName).getStation().getFirst().getEva().toString();
//    }
//}
