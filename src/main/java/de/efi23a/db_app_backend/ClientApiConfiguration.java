package de.efi23a.db_app_backend;



import org.example.apidemo.ApiClient;
import org.example.apidemo.dbapi.DefaultApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientApiConfiguration {

    @Bean
    public DefaultApi clientApiClient() {
        return new DefaultApi();
    }
}
