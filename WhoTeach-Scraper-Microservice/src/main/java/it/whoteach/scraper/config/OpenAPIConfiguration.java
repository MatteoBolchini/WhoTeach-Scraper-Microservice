package it.whoteach.scraper.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfiguration {
	
	@Value("${whoteach.cloudstorage.url}")
	private String url;
	
    @Bean
    public OpenAPI openAPI() {
    	return new OpenAPI()
                .addServersItem(new Server().url(url))
                .info(new Info()
                        .title("WhoTeach Scraper Microservice")
                        .description("Those are REST API to interrogate a Neo4j Aura DB")
                        .version("0.0.1"))
                .components(new Components()
                       .addSecuritySchemes("basicScheme",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")));
    }
    
}
