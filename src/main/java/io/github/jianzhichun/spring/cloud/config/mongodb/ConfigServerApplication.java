package io.github.jianzhichun.spring.cloud.config.mongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.jianzhichun.spring.cloud.config.mongodb.MongodbConfigServer.EnableMongoConfigServer;

@SpringBootApplication
@EnableMongoConfigServer
public class ConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigServerApplication.class, args);
	}
}
