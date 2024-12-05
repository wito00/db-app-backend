package de.efi23a.db_app_backend;


import org.example.dbREst.api.DefaultApi;
import org.example.faSta.api.FaStaApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientApiConfiguration {

    @Bean
    public DefaultApi dbRestClient() {
        return new DefaultApi();
    }

    @Bean
    public FaStaApi faStaClient() {
        return new FaStaApi();
    }

}
