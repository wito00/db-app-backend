package de.efi23a.db_app_backend.service;


import de.efi23a.db_app_backend.api.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    public List<User> userList;

    private UserService(){
        userList = new ArrayList<>();

        User user1 = new User(1, "tom", 24);

        userList.add(user1);
    }

    public User getUser(int id){
        for(User user: userList){
            if(user.getId() == id){
                return user;
            }
        }
        return null;
    }

    public void addUser(String name, int age){
        User newUser = new User(userList.getLast().getId()+1, name, age);
        userList.add(newUser);
    }
}
