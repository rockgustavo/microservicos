package com.rockgustavo.mscloudgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class MscloudgatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MscloudgatewayApplication.class, args);
	}

	@Bean
	RouteLocator routes(RouteLocatorBuilder builder) {
		return builder
				.routes()
				.route(r -> r.path("/api/cliente/**").uri("lb://msclients"))
				.route(r -> r.path("/api/cartao/**").uri("lb://mscards"))
				.route(r -> r.path("/api/avaliacao-credito/**").uri("lb://msavaliadorcredito"))
				.build();
	}

}
