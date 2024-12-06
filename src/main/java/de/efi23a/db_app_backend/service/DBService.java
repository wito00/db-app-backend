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


@Service
public class DBService {
    DefaultApi defaultApi;
    ObjectMapper jacksonObjectMapper;
    FaStaApi faStaApi;
    Arrival arrival;

    public DBService(
            DefaultApi defaultApi,
            ObjectMapper jacksonObjectMapper,
            FaStaApi faStaApi
    ) {
        this.defaultApi = defaultApi;
        this.jacksonObjectMapper = jacksonObjectMapper;
        this.faStaApi = faStaApi;
    }

    public List<String> getEvaAndNameByPattern(String stationName) {
        List<Station> stationList = defaultApi.locationsGet(stationName, null, null, null, null, null, null, null, null);
        String eva = stationList.getFirst().getId();
        String name = stationList.getFirst().getName();
        List<String> evaAndName = new ArrayList<>();
        evaAndName.add(eva);
        evaAndName.add(name);
        return evaAndName;
    }

    public List<Departure> getDeparturesByEva(String eva, OffsetDateTime offsetDateTime) {
        DepartureWrapper departureWrapper = defaultApi.stopsIdDeparturesGet(eva, offsetDateTime, null, 180, 100, null, null, null, null);
        List<org.example.dbRest.model.Departure> departureList = departureWrapper.getDepartures();
        List<Departure> departureListFrontend = new ArrayList<>();
        List<String> platformsWithElevators = getListOfPlatformsWithElevatorByEva(eva);
        for (org.example.dbRest.model.Departure departure : departureList) {
            if (departure.getLine().getProductName().equals("Bus") || departure.getLine().getProductName().equals("STR") || departure.getLine().getProductName().equals("U")) {
                continue;
            }
            if (departureListFrontend.size() >= 10) {
                return departureListFrontend;
            }
            String name = departure.getLine().getName();
            String departureTime = departure.getPlannedWhen().toString();
            String platform = departure.getPlannedPlatform();
            String destination = departure.getDestination().getName();
            String typ = departure.getLine().getProductName();
            boolean stepless = false;

            if (platformsWithElevators.contains(platform)) {
                stepless = true;
            }
            departureListFrontend.add(new Departure(name, departureTime, platform, destination, typ, stepless));
        }
        return departureListFrontend;
    }

    public List<Arrival> getArrivalsByEva(String eva, OffsetDateTime offsetDateTime) {
        ArrivalWrapper arrivalWrapper = defaultApi.stopsIdArrivalsGet(eva, offsetDateTime, null, 180, 100, null, null, null, null);
        List<org.example.dbRest.model.Arrival> arrivalList = arrivalWrapper.getArrivals();
        List<Arrival> arrivalListFrontend = new ArrayList<>();
        List<String> platformsWithElevators = getListOfPlatformsWithElevatorByEva(eva);
        for (org.example.dbRest.model.Arrival arrival : arrivalList) {
            if (arrival.getLine().getProductName().equals("Bus") || arrival.getLine().getProductName().equals("STR") || arrival.getLine().getProductName().equals("U")) {
                continue;
            }
            if (arrivalListFrontend.size() >= 10) {
                return arrivalListFrontend;
            }
            String name = arrival.getLine().getName();
            String departureTime = arrival.getPlannedWhen().toString();
            String platform = arrival.getPlannedPlatform();
            String origin = "";
            Boolean stepless = false;
            if (platformsWithElevators.contains(platform)) {
                stepless = true;
            }
            assert arrival.getOrigin() != null;
            if (arrival.getOrigin().getName() != null) {
                origin = arrival.getOrigin().getName();

            }

            String typ = arrival.getLine().getProductName();
            arrivalListFrontend.add(new Arrival(name, departureTime, platform, origin, typ, stepless));
        }
        return arrivalListFrontend;
    }

    public List<String> getListOfPlatformsWithElevatorByEva(String eva) {
        Object object = defaultApi.stationsIdGet(eva);
        Map<String, Object> map = jacksonObjectMapper.convertValue(object, new TypeReference<Map<String, Object>>() {
        });
        String id = map.get("nr").toString();
        List<Facility> facilities;
        try {
            //facilities = faStaApi.findStationByStationNumber(Long.parseLong(id)).getFacilities();
            facilities = faStaApi.findFacilities(null, null, null, Long.parseLong(id), null);
        } catch (Exception e) {
            throw e;
        }
        assert facilities != null;

        List<String> platformsWithElevator = new ArrayList<>();
        for (Facility facility : facilities) {
            if (facility.getType() == Facility.TypeEnum.ELEVATOR) {
                String[] parts = facility.getDescription().split(" ");
                for (int i = 0; i < parts.length; i++) {
                    if (parts[i].equalsIgnoreCase("Gleis") && i + 1 < parts.length) {
                        String[] platformParts = parts[i + 1].split("/");
                        for (String platform : platformParts) {
                            platformsWithElevator.add(platform);
                        }
                    }
                }

            }
        }
        return platformsWithElevator;
    }

    public String getCurrentDate() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return date.format(formatter);
    }


    public List<Arrival> sortByArrivalTime(List<Arrival> arrivals) {
        return arrivals.stream()
                .sorted((t1, t2) -> LocalTime.parse(t1.getArrivalTime())
                        .compareTo(LocalTime.parse(t2.getArrivalTime())))
                .collect(Collectors.toList());
    }

    public List<Departure> sortByDepartureTime(List<Departure> departures) {
        return departures.stream()
                .sorted((t1, t2) -> LocalTime.parse(t1.getDepartureTime())
                        .compareTo(LocalTime.parse(t2.getDepartureTime())))
                .collect(Collectors.toList());
    }
}
