package de.efi23a.db_app_backend.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private int id;
    private String name;
    private int age;

    public User(int id, String name, int age){
        this.id = id;
        this.name = name;
        this.age = age;
    }

}
