package es.uji.ei1027.clubesportiu;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@SpringBootApplication
public class ClubesportiuApplication implements CommandLineRunner {

	// Plantilla per a executar operacions sobre la connexió
	private JdbcTemplate jdbcTemplate;

	// Crea el jdbcTemplate a partir del DataSource que hem configurat
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private static final Logger log = Logger.getLogger(ClubesportiuApplication
			.class.getName());
	public static void main(String[] args) {
// Auto-configura l'aplicació
		new SpringApplicationBuilder(ClubesportiuApplication.class).run(args);
	}
	// Funció principal
	public void run(String... strings) throws Exception {
		log.info("Ací va el meu codii");
		log.info("Selecciona la nadadora Gemma Mengual");
		Nadador n1 = jdbcTemplate.queryForObject(
				"SELECT * FROM Nadador WHERE nom = 'Gemma Mengual'",
				new NadadorRowMapper());
		log.info(n1.toString());
	}


	void mostraNadador(String nomNadador) {
		// Nadador n = jdbcTemplate.query()
	}
}
