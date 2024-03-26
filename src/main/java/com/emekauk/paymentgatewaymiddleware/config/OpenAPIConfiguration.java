package com.emekauk.paymentgatewaymiddleware.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//

import java.util.List;


@Configuration
public class OpenAPIConfiguration {

@Value("${emeka.openapi.dev-url}")
private String devUrl;



        @Bean
        public OpenAPI myOpenAPI() {
                Server devServer = new Server();
                devServer.setUrl(devUrl);
                devServer.setDescription("Server URL in Development environment");

                Contact contact = new Contact();
                contact.setEmail("emekaukwuoma546@gmail.com");
                contact.setName("Emeka Ukwuoma");
                contact.setUrl("http://loclahost/8080");

                License myLicense = new License().name("N/A").url("N/A");

                Info info = new Info()
                        .title("Payment Gateway Middleware API")
                        .version("1.0")
                        .contact(contact)
                        .description("""
                                This is a mocked RESTful API that simulates a simple payment
                                gateway middleware, facilitating transactions between a mock retail application
                                and a banking service.
                                """).termsOfService("N/A")
                        .license(myLicense);

                return new OpenAPI().info(info).servers(List.of(devServer));
        }

}
