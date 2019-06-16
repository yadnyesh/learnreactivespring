package io.yadnyesh.learnreactivespring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class LearnreactivespringApplication {
	public static void main(String[] args) {
		SpringApplication.run(LearnreactivespringApplication.class, args);
	}
}
