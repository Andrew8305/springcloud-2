package de.codeboje.springbootbook.registry;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@EnableConfigServer
public class ConfigServer {
	public static void main(String[] args) {
		new SpringApplicationBuilder(ConfigServers.class).properties("spring.config.name=configserver")
				.run(args);
	}
}
