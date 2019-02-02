package br.com.releasesolutions.projetocursomc.config;

import br.com.releasesolutions.projetocursomc.domain.PagamentoComBoleto;
import br.com.releasesolutions.projetocursomc.domain.PagamentoComCartao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {

        return new Jackson2ObjectMapperBuilder() {

            public void configure(ObjectMapper objectMapper) {
                objectMapper.registerSubtypes(PagamentoComBoleto.class);
                objectMapper.registerSubtypes(PagamentoComCartao.class);
                super.configure(objectMapper);
            }
        };
    }
}
