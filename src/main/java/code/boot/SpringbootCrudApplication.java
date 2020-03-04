package code.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootCrudApplication {
	private static final Logger logger = LoggerFactory.getLogger(SpringbootCrudApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringbootCrudApplication.class, args);
		logger.info("--Application Started--");
	}
}