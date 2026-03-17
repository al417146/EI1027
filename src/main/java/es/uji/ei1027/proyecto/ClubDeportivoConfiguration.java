/*package es.uji.ei1027.proyecto;

import es.uji.ei1027.proyecto.categoria.Categoria;
import es.uji.ei1027.clubesportiu.categoria.CategoriaFederacion;
import es.uji.ei1027.clubesportiu.categoria.CategoriaWorldCup;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class ClubDeportivoConfiguration {

    // Configura el acceso a la base de datos (DataSource)
    // a partir de las propiedades a src/main/resources/applications.properties
    // que comienzan por el prefijo spring.datasource
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public Categoria categoriaF(){
        return new CategoriaFederacion();
    }

    @Bean
    @Primary
    public Categoria categoriaWC(){
        return new CategoriaWorldCup();
    }

    @Bean
    public Categoria categoria(){
        return new CategoriaWorldCup();
    }
}*/
