package es.uji.ei1027.proyecto.dao;

import es.uji.ei1027.proyecto.modelo.UserDetails;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;
    private final BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // Autenticación con contraseña encriptada (como en FakeUserProvider)
    public UserDetails loadUserByUsername(String dni, String rawPassword) {
        try {
            UserDetails user = jdbcTemplate.queryForObject(
                    "SELECT dni, password, rol FROM userdetails WHERE dni = ?",
                    (rs, rowNum) -> {
                        UserDetails u = new UserDetails();
                        u.setDni(rs.getString("dni"));
                        u.setPassword(rs.getString("password")); // contraseña encriptada
                        u.setRol(rs.getString("rol"));
                        return u;
                    },
                    dni.trim()
            );
            if (user != null && passwordEncryptor.checkPassword(rawPassword, user.getPassword())) {
                // Devolvemos solo los datos seguros (sin la contraseña)
                UserDetails safeUser = new UserDetails();
                safeUser.setDni(user.getDni());
                safeUser.setRol(user.getRol());
                return safeUser;
            }
            return null;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    // Para añadir usuarios con la contraseña encriptada
    public void addUser(String dni, String rawPassword, String rol) {
        String encryptedPassword = passwordEncryptor.encryptPassword(rawPassword);
        jdbcTemplate.update(
                "INSERT INTO userdetails (dni, password, rol) VALUES (?, ?, ?)",
                dni, encryptedPassword, rol
        );
    }

    public void encryptExistingPasswords() {
        List<UserDetails> users = jdbcTemplate.query(
                "SELECT dni, password, rol FROM userdetails",
                (rs, rowNum) -> {
                    UserDetails u = new UserDetails();
                    u.setDni(rs.getString("dni"));
                    u.setPassword(rs.getString("password"));
                    u.setRol(rs.getString("rol"));
                    return u;
                });
        for (UserDetails u : users) {
            String encrypted = passwordEncryptor.encryptPassword(u.getPassword());
            jdbcTemplate.update("UPDATE userdetails SET password=? WHERE dni=?",
                    encrypted, u.getDni());
        }
    }
}