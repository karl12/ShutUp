package net.formicary.shutup.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "net.formicary.shutup")
@EntityScan(basePackages = "net.formicary.shutup")
@EnableJpaRepositories(basePackages = "net.formicary.shutup")
@PropertySource(value = { "classpath:application.properties" })
public class ShutUpApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShutUpApplication.class, args);
	}
}
