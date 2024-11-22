package de.efi23a.db_app_backend.model;

import lombok.Data;

import java.util.List;

@Data
public class Departure {
    private String tripId;
    private Stop stop;
    private String when;
    private String plannedWhen;
    private String prognosedWhen;
    private Integer delay;
    private String platform;
    private String plannedPlatform;
    private String prognosedPlatform;
    private String prognosisType;
    private String direction;
    private String provenance;
    private Line line;
    private List<Remark> remarks;
    private Stop origin;
    private Stop destination;
    private Boolean cancelled;
}
