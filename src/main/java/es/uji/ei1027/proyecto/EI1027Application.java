package es.uji.ei1027.proyecto;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.logging.Logger;


@SpringBootApplication
public class EI1027Application {

	private static final Logger log = Logger.getLogger(EI1027Application.class.getName());

	public static void main(String[] args) {
		// Autoconfigura la aplicación
		new SpringApplicationBuilder(EI1027Application.class).run(args);
	}
}


