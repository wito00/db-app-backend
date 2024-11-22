package de.efi23a.db_app_backend.model;

import lombok.Data;

@Data
public class Products {
    private boolean nationalExpress;
    private boolean national;
    private boolean regionalExp;
    private boolean regional;
    private boolean suburban;
    private boolean bus;
    private boolean ferry;
    private boolean subway;
    private boolean tram;
    private boolean taxi;
}
