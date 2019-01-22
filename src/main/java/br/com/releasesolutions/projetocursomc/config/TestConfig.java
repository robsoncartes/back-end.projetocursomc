package br.com.releasesolutions.projetocursomc.config;

import br.com.releasesolutions.projetocursomc.services.DBService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig {

    private DBService dbService;

    public TestConfig(DBService dbService){
        this.dbService = dbService;
    }

    @Bean
    public boolean instantiateDatabase() throws Exception {

        dbService.instantiateTestDatabase();

        return true;
    }
}
