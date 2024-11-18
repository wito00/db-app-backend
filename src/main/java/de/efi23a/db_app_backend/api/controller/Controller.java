package de.efi23a.db_app_backend.api.controller;


import de.efi23a.db_app_backend.api.model.User;

import de.efi23a.db_app_backend.service.DBService;
import de.efi23a.db_app_backend.service.UserService;
import org.example.apidemo.dbapi.TimetablesApi;
import org.example.apidemo.dbpapimodell.StationData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {

    private UserService userService;
    private DBService dbService;
    private TimetablesApi timetablesApi;

    private String apiSecret;


    public Controller(
            UserService userService,
            DBService dbService,
            TimetablesApi timetablesApi,
            @Value("${api.id}") String apiId,
            @Value("${api.secret}") String apiSecret
            ){
        this.userService = userService;
        this.timetablesApi = timetablesApi;
        this.dbService = dbService;

        timetablesApi.getApiClient().addDefaultHeader("DB-Client-Id", apiId);
        timetablesApi.getApiClient().addDefaultHeader("DB-Api-Key", apiSecret);
    }

    @GetMapping("/db")
    @CrossOrigin("http://localhost:4200")
    public String  getStationNameByEvaNo(@RequestParam String evaNo){
        List<StationData> stationData=  timetablesApi.stationPatternGet(evaNo).getStation();
        return dbService.getName(stationData);
    }
//
//    @GetMapping("/db")
//    @CrossOrigin("http://localhost:4200")
//    public Timetable getSomething(@RequestParam String evoNo){
//        return timetablesApi.rchgEvaNoGet(evoNo);
//    }

    @PostMapping("/users/create")
    @CrossOrigin("http://localhost:4200")
    public void createUser(@RequestParam String name, @RequestParam int age){
        userService.addUser(name, age);
    }

    @PostMapping( "/users/id")
    public User getUserById(@RequestParam int id){
        return userService.getUser(id);
    }

    @GetMapping( "/users")
    @CrossOrigin("http://localhost:4200")
    public List<User> getAllUsers(){
        return userService.userList;
    }

    @PatchMapping("users/updateAge")
    public void updateAgeId(@RequestParam int id, @RequestParam int age){
        userService.getUser(id).setAge(age);

    }

    @GetMapping("/bahnhof")
    @CrossOrigin("http://localhost:4200")
    public void getStation(@RequestParam int evaNo){

    }

//    @PostMapping
//    public void createUser(String name){
//        int id = userService.userList.getLast().getId();
//        userService.userList.add(new User(id +1, name));
//    }
}
