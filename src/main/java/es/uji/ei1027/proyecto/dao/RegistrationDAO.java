package es.uji.ei1027.proyecto.dao;

import es.uji.ei1027.proyecto.dao.RowMaps.RegistrationRowMapper;
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
    public void setDataSource(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addRegistration(Registration r){
        jdbcTemplate.update("INSERT INTO Registration VALUES (?,?,?,?)",
                r.getDNIUser(),
                r.getDNIAssistant(),
                r.getCode(),
                r.isAttendace());
    }

    public void deleteRegistration(String DNIUser){
        jdbcTemplate.update("DELETE FROM Registration WHERE DNIUser=?",
                DNIUser);
    }

    public void updateRegistration(Registration r){
        jdbcTemplate.update("UPDATE Registration SET DNIAssistant=?, code=?, attendace=? WHERE DNIUser=?",
                r.getDNIAssistant(),
                r.getCode(),
                r.isAttendace(),
                r.getDNIUser());
    }

    public Registration getRegistration(String DNIUser){
        try{
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM Registration WHERE DNIUser=?",
                    new RegistrationRowMapper(),
                    DNIUser);
        } catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    public List<Registration> getRegistrations(){
        try{
            return jdbcTemplate.query("SELECT * FROM Registration",
                    new RegistrationRowMapper());
        } catch(EmptyResultDataAccessException e){
            return new ArrayList<>();
        }
    }
}