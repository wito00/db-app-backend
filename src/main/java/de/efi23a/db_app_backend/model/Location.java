package de.efi23a.db_app_backend.model;

import lombok.Data;

@Data
public class Location {
    private String type;
    private String id;
    private double latitude;
    private double longitude;
}
