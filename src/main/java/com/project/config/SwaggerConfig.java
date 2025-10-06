package com.project.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "EzeeRide API", version = "1.0", description = "Backend APIs"),
        security = @SecurityRequirement(name = "bearerAuth") // Apply globally
)
@SecurityScheme(
        name = "bearerAuth",                  // Name referenced in SecurityRequirement
        type = SecuritySchemeType.HTTP,       // HTTP auth type
        scheme = "bearer",                    // Bearer token
        bearerFormat = "JWT"                  // Optional, just info
)
public class SwaggerConfig {
    // No code needed here, annotation-based config
}
