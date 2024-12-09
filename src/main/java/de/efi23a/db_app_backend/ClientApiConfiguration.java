package de.efi23a.db_app_backend;


import org.example.dbREst.api.DefaultApi;
import org.example.faSta.api.FaStaApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // Kennzeichnet diese Klasse als Konfigurationsklasse in Spring.
public class ClientApiConfiguration {

    @Bean // Definiert eine Bean für den DB Rest-API-Client.
    public DefaultApi dbRestClient() {
        return new DefaultApi(); // Erstellt und gibt eine neue Instanz des DefaultApi zurück.
    }

    @Bean // Definiert eine Bean für den FaSta-API-Client.
    public FaStaApi faStaClient() {
        return new FaStaApi(); // Erstellt und gibt eine neue Instanz des FaStaApi zurück.
    }

}