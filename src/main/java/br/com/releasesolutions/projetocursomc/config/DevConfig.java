package br.com.releasesolutions.projetocursomc.config;

import br.com.releasesolutions.projetocursomc.services.DBService;
import br.com.releasesolutions.projetocursomc.services.EmailService;
import br.com.releasesolutions.projetocursomc.services.SmtpEmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevConfig {

    private final DBService dbService;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String stragegy;

    public DevConfig(DBService dbService) {
        this.dbService = dbService;
    }

    @Bean
    public boolean instantiateDatabase() throws Exception {

        if (!"create".equals(stragegy))
            return false;

        dbService.instantiateTestDatabase();

        return true;
    }

    @Bean
    EmailService getEmailService() {

        return new SmtpEmailService();
    }
}
