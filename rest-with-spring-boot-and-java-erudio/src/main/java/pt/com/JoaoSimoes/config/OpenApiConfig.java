package pt.com.JoaoSimoes.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("REST API's from 0 with Java, Spring Boot, Kubernets and Docker")
                .version("V1")
                .description("REST API's from 0 with Java, Spring Boot, Kubernets and Docker")
                .termsOfService("http://swagger.io/terms/")
                .license(new License()
                        .name("Apache 2.0")
                        .url("http://www.apache.org/licenses/LICENSE-2.0")));
    }
}
