package br.com.releasesolutions.projetocursomc.config;

import br.com.releasesolutions.projetocursomc.services.DBService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevConfig {

    private DBService dbService;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String stragegy;

    public DevConfig(DBService dbService){
        this.dbService = dbService;
    }

    @Bean
    public boolean instantiateDatabase() throws Exception {

        if (!"create".equals(stragegy))
            return false;

        dbService.instantiateTestDatabase();

        return true;
    }
}
