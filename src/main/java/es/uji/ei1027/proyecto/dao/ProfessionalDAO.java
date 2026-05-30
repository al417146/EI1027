package es.uji.ei1027.proyecto.dao;

import es.uji.ei1027.proyecto.RowMaps.ProfessionalRowMapper;
import es.uji.ei1027.proyecto.modelo.Professional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProfessionalDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addProfessional(Professional p) {
        jdbcTemplate.update(
            "INSERT INTO professional (dni, name, genre, phone, email, address, speciality) VALUES (?,?,?,?,?,?,?)",
            p.getDNI(), p.getName(), p.getGenre(), p.getPhone(),
            p.getMail(), p.getAddress(), p.getUniqueSpeciality());
    }

    public void deleteProfessional(String dni) {
        jdbcTemplate.update("DELETE FROM professional WHERE dni=?", dni);
    }

    public void updateProfessional(Professional p) {
        jdbcTemplate.update(
            "UPDATE professional SET name=?, genre=?, phone=?, email=?, address=?, speciality=? WHERE dni=?",
            p.getName(), p.getGenre(), p.getPhone(), p.getMail(),
            p.getAddress(), p.getUniqueSpeciality(), p.getDNI());
    }

    public Professional getProfessional(String dni) {
        try {
            return jdbcTemplate.queryForObject(
                "SELECT * FROM professional WHERE dni=?",
                new ProfessionalRowMapper(), dni);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Professional> getProfessionals() {
        try {
            return jdbcTemplate.query("SELECT * FROM professional",
                new ProfessionalRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    public String getHistorial(String dni) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT historial FROM professional WHERE dni = ?",
                    String.class, dni);
        } catch (Exception e) {
            return null;
        }
    }

    public void updateHistorial(String dni, String historial) {
        jdbcTemplate.update(
                "UPDATE professional SET historial = ? WHERE dni = ?",
                historial, dni);
    }
}
