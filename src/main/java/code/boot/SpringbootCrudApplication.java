package code.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SpringbootCrudApplication extends SpringBootServletInitializer {

	private static final Logger logger = LoggerFactory.getLogger(SpringbootCrudApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringbootCrudApplication.class, args);
		logger.info("--Application Started--");
	}

}