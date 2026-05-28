package es.uji.ei1027.proyecto.dao;

import es.uji.ei1027.proyecto.RowMaps.RegistrationRowMapper;
import es.uji.ei1027.proyecto.modelo.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RegistrationDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addRegistration(Registration r) {
        jdbcTemplate.update(
            "INSERT INTO activityregistration (idactivity, dniuser, attended) VALUES (?,?,?)",
            r.getIdActivity(), r.getDniUser(), r.isAttended());
    }

    public void deleteRegistration(int idRegist) {
        jdbcTemplate.update("DELETE FROM activityregistration WHERE idregist=?", idRegist);
    }

    public List<Registration> getRegistrationsByActivity(int idActivity) {
        try {
            return jdbcTemplate.query(
                "SELECT * FROM activityregistration WHERE idactivity = ?",
                new RegistrationRowMapper(), idActivity);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    public List<Registration> getRegistrationsByUser(String dniUser) {
        try {
            return jdbcTemplate.query(
                "SELECT * FROM activityregistration WHERE dniuser = ?",
                new RegistrationRowMapper(), dniUser);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    public boolean isRegistered(int idActivity, String dniUser) {
        Integer count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM activityregistration WHERE idactivity = ? AND dniuser = ?",
            Integer.class, idActivity, dniUser);
        return count != null && count > 0;
    }

    public void updateAttendance(int idRegist, boolean attended) {
        jdbcTemplate.update(
            "UPDATE activityregistration SET attended = ? WHERE idregist = ?",
            attended, idRegist);
    }

    public Registration getRegistration(int idRegist) {
        try {
            return jdbcTemplate.queryForObject(
                "SELECT * FROM activityregistration WHERE idregist = ?",
                new RegistrationRowMapper(), idRegist);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
