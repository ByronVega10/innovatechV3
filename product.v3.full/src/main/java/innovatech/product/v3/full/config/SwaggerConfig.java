package innovatech.product.v3.full.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("API 2026 Inventario de Productos")
                        .version("1.1")
                        .description("Documentacion de la API para el sistema de inventario de productos"));
    }
}
