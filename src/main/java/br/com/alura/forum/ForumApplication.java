package br.com.alura.forum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSpringDataWebSupport
@EnableCaching
@EnableSwagger2
public class ForumApplication {
//para o build do projeto gerar um arquivo .war devemos extender de SpringBootServerInitializer e sobrescrever o método configure

	public static void main(String[] args) {
		SpringApplication.run(ForumApplication.class, args);
	}

}
