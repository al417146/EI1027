package es.uji.ei1027.proyecto.dao;

import es.uji.ei1027.proyecto.dao.RowMaps.SpecialityRowMapper;
import es.uji.ei1027.proyecto.modelo.Speciality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SpecialityDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addSpeciality(Speciality speciality) {
        jdbcTemplate.update("INSERT INTO Speciality VALUES (?, ?)",
                speciality.getIdSpeciality(),
                speciality.getDescrip());
    }

    public void deleteSpeciality(String idSpeciality) {
        jdbcTemplate.update("DELETE FROM Speciality WHERE idSpeciality=?", idSpeciality);
    }

    public void updateSpeciality(Speciality speciality) {
        jdbcTemplate.update("UPDATE Speciality SET descrip=? WHERE idSpeciality=?",
                speciality.getDescrip(),
                speciality.getIdSpeciality());
    }

    public Speciality getSpeciality(String idSpeciality) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM Speciality WHERE idSpeciality=?",
                    new SpecialityRowMapper(),
                    idSpeciality);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Speciality> getSpecialities() {
        try {
            return jdbcTemplate.query("SELECT * FROM Speciality", new SpecialityRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
}