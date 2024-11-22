package de.efi23a.db_app_backend.model;

import lombok.Data;

@Data
public class Line {
    private String type;
    private String id;
    private String fahrtNr;
    private String name;
    private Boolean isPublic;
    private String adminCode;
    private String productName;
    private String mode;
    private String product;
    private Operator operator;
}
