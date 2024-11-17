package de.efi23a.db_app_backend;

import org.example.apidemo.dbapi.TimetablesApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientApiConfiguration {

    @Bean
    public TimetablesApi clientApiClient() {
        return new TimetablesApi();
    }
}
