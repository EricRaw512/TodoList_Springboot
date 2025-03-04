package com.eric.todolist.config.documentation;

import com.eric.todolist.util.constant.GlobalConstant;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(GlobalConstant.DOCUMENTATION_TITLE)
                        .version(GlobalConstant.DOCUMENTATION_VERSION)
                        .description(GlobalConstant.DOCUMENTATION_DESCRIPTION))
                .components(new Components()
                                .addSecuritySchemes("bearerAuth", new SecurityScheme() // Define security scheme
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT"))
                )
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }

    @Bean
    public GroupedOpenApi checklistApi() {
        return GroupedOpenApi.builder()
                .group(GlobalConstant.CHECKLIST)
                .pathsToMatch("/api/checklist/**")
                .packagesToScan(GlobalConstant.DOCUMENTATION_PACKAGE)
                .build();
    }

    @Bean
    public GroupedOpenApi checklistItemApi() {
        return GroupedOpenApi.builder()
                .group(GlobalConstant.CHECKLIST_ITEM)
                .pathsToMatch("/api/checklist/{checklistId}/item/**")
                .packagesToScan(GlobalConstant.DOCUMENTATION_PACKAGE)
                .build();
    }

    @Bean
    public GroupedOpenApi authenticationApi() {
        return GroupedOpenApi.builder()
                .group(GlobalConstant.AUTHENTICATION)
                .pathsToMatch("/api/login", "/api/register")
                .packagesToScan(GlobalConstant.DOCUMENTATION_PACKAGE)
                .build();
    }

    @Bean
    public GroupedOpenApi userManagementApi() {
        return GroupedOpenApi.builder()
                .group(GlobalConstant.USER_MANAGEMENT)
                .group("Admin")
                .pathsToMatch("/api/user/**")
                .packagesToScan(GlobalConstant.DOCUMENTATION_PACKAGE)
                .build();
    }
}