package com.tb.gconnect.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author abdul.rehman4 12th/07/2019
 * @version 1.0
 * @since v1.0
 * Configurations for Swager-2.0
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.tb.gconnect")).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Blobal Connect System REST APIs",
                "Blobal ConnectREST APIs",
                "v1",
                "Terms of service",
                new Contact("Blobal Connect", "comming soon", "abdulrehman.abdul.qau@gmail.com"),
                "License of API", "API license URL", Collections.emptyList());
    }

}