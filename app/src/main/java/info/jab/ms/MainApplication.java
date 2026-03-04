package info.jab.ms;

import org.jspecify.annotations.NonNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApplication {

	public static void main(@NonNull String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

}
