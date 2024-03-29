package com.tutorial.springboot.graphql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.tutorial.springboot.graphql.repository")
public class GraphqlSpringbootTutorialApplication {

	public static void main(String[] args) {
		SpringApplication.run(GraphqlSpringbootTutorialApplication.class, args);
	}

}
