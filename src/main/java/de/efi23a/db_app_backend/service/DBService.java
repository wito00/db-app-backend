package de.efi23a.db_app_backend.service;

import org.example.apidemo.dbpapimodell.StationData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DBService {

    public String getName(List<StationData> stationData) {
        String name = stationData.getFirst().getName();
        return name;
    }
}
