package es.uji.ei1027.proyecto;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.logging.Logger;


@SpringBootApplication
public class EI1027Application {
	/*// Plantilla para ejecutar operaciones sobre la conexión
	private JdbcTemplate jdbcTemplate;

	// Crea el jdbcTemplate a partir del DataSource que hemos configurado
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
*/
	private static final Logger log = Logger.getLogger(EI1027Application.class.getName());

	public static void main(String[] args) {
		// Autoconfigura la aplicación
		new SpringApplicationBuilder(EI1027Application.class).run(args);
	}
}


