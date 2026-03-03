package es.uji.ei1027.clubesportiu;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.jdbc.core.*;

import javax.sql.DataSource;

@SpringBootApplication
public class ClubesportiuApplication implements CommandLineRunner {

	private static final Logger log = Logger.getLogger(ClubesportiuApplication .class.getName());

	private JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		// Auto-configura l'aplicació
		new SpringApplicationBuilder(ClubesportiuApplication.class).run(args);
	}

	// Funció principal
	public void run(String... strings) throws Exception {
		log.info("Ací va el meu codi");
		log.info("Selecciona la nadadora Gemma Mengual");
		Nadador n1 = jdbcTemplate.queryForObject(
				"SELECT * FROM Nadador WHERE nom = 'Gemma Mengual'",
				new NadadorRowMapper());
		log.info(n1.toString());
		mostraNadador("Mireia Belmonte Garcia");
		log.info("Selecciona tots els nadadors");
		List<Nadador> nadadors = jdbcTemplate.query(
				"SELECT * FROM Nadador",
				new NadadorRowMapper());
		for (Nadador n: nadadors) {
			log.info(n.toString());
		}

	}

	public void mostraNadador(String nomNadador){
		log.info("Selecciona la nad. Mireia Belmonte Garcia (amb paràm.)");
		Nadador n2 = jdbcTemplate.queryForObject(
				"SELECT * FROM Nadador WHERE nom =?",
				new NadadorRowMapper(),
				nomNadador);
		log.info(n2.toString());
	}
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

}

