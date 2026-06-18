package cl.duoc.msMecanicos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        
        return new OpenAPI()
                .info(new Info()
                        .title("API MS Mecanicos")
                        .version("1.0")
                        .description("API para la gestión de mecanicos en el sistema de Taller Mecanico"));
    }
}
