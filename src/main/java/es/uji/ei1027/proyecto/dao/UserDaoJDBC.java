package es.uji.ei1027.proyecto.dao;

import es.uji.ei1027.proyecto.modelo.UserDetails;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class UserDaoJDBC extends UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public UserDetails loadUserByUsername(String mail, String password) {
        try {
            UserDetails user = jdbcTemplate.queryForObject(
                    "SELECT * FROM User WHERE mail = ?",
                    new BeanPropertyRowMapper<>(UserDetails.class),
                    mail
            );

            BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();

            if (encryptor.checkPassword(password, user.getPassword())) {
                UserDetails safe = new UserDetails();
                safe.setDni(user.getDni());
                return safe;
            }

            return null;

        } catch (Exception e) {
            return null; // usuario no encontrado
        }
    }


    public Collection<UserDetails> listAllUsers() {
        return jdbcTemplate.query(
                "SELECT * FROM User",
                new BeanPropertyRowMapper<>(UserDetails.class)
        );
    }
}
