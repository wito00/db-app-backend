package de.efi23a.db_app_backend.model;

import lombok.Data;

@Data
public class Elevator {
    private String platform;
    private String status;

    public Elevator(String platform, String status) {
        this.platform = platform;
        this.status = status;
    }
}
