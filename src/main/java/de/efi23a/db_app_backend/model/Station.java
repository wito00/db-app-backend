package de.efi23a.db_app_backend.model;

import lombok.Data;

@Data
public class Station {
    private String type;
    private String id;
    private String name;
    private Location location;
    private Products products;
}
