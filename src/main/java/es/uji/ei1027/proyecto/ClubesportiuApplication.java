package es.uji.ei1027.proyecto;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.logging.Logger;


@SpringBootApplication
public class ClubesportiuApplication {
	/*// Plantilla para ejecutar operaciones sobre la conexión
	private JdbcTemplate jdbcTemplate;

	// Crea el jdbcTemplate a partir del DataSource que hemos configurado
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
*/
	private static final Logger log = Logger.getLogger(ClubesportiuApplication.class.getName());

	public static void main(String[] args) {
		// Autoconfigura la aplicación
		new SpringApplicationBuilder(ClubesportiuApplication.class).run(args);
	}
}

	/*// Función principal
	public void run(String... strings) throws Exception {
		// Ejemplo de consulta que no devuelve ninguna fila
		try {
			log.info("Selecciona una nadadora inexistente");
			Nadador n3 = jdbcTemplate.queryForObject(
					"SELECT * FROM Nadador WHERE nombre = 'No esta'",
					new NadadorRowMapper());
			log.info(n3.toString());
		}
		catch(EmptyResultDataAccessException e) {
			log.info("No se encuentra en la base de datos");
		}
	}

	void muestraNadador(String nomNadador){
		try {
			Nadador n2 = jdbcTemplate.queryForObject(
					"SELECT * FROM Nadador WHERE nombre = ?",
					new NadadorRowMapper(),
					nomNadador);
			log.info(n2.toString());
		} catch (EmptyResultDataAccessException e){
			log.info("Ese nadador/a no está en la base de datos");
		}
	}
}*/
