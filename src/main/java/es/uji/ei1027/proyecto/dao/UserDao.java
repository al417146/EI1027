package es.uji.ei1027.proyecto.dao;

import es.uji.ei1027.proyecto.modelo.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UserDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public UserDetails loadUserByUsername(String dni, String password) {
        try {
            UserDetails user = jdbcTemplate.queryForObject(
                    "SELECT * FROM userdetails WHERE dni = ? AND password = ?",
                    (rs, rowNum) -> {
                        UserDetails u = new UserDetails();
                        u.setDni(rs.getString("dni"));
                        u.setPassword(rs.getString("password"));
                        u.setRol(rs.getString("rol"));
                        return u;
                    },
                    dni.trim(), password
            );
            return user;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    public void addUser(String dni, String password, String rol) {
        jdbcTemplate.update(
                "INSERT INTO userdetails (dni, password, rol) VALUES (?, ?, ?)",
                dni, password, rol);
    }
}