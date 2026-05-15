package es.uji.ei1027.proyecto.dao;

import es.uji.ei1027.proyecto.RowMaps.OVIUserRowMapper;
import es.uji.ei1027.proyecto.modelo.OVIUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
@Repository

public class OVIUserDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addOVIUser(OVIUser u){
        jdbcTemplate.update(
                "INSERT INTO oviuser (dni, name, birth_date, gender, phone, mail, address, status) VALUES (?,?,?,?,?,?,?,?)",
                u.getDNI(),
                u.getName(),
                u.getBirthDate(),
                u.getGender(),
                u.getPhone(),
                u.getMail(),
                u.getAddress(),
                u.getStatus());
    }

    public void deleteOVIUser(String DNI){
        jdbcTemplate.update("DELETE FROM oviuser WHERE dni=?", DNI);
    }

    public void updateOVIUser(OVIUser u){
        jdbcTemplate.update(
                "UPDATE oviuser SET name=?, birth_date=?, gender=?, phone=?, mail=?, address=?, status=? WHERE dni=?",
                u.getName(),
                u.getBirthDate(),
                u.getGender(),
                u.getPhone(),
                u.getMail(),
                u.getAddress(),
                u.getStatus(),
                u.getDNI());
    }

    public OVIUser getOVIUser(String DNI){
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM oviuser WHERE dni=?",
                    new OVIUserRowMapper(), DNI);
        } catch(EmptyResultDataAccessException e) {
            return null;
        }
    }
    public List<OVIUser> getOVIUsers(){
        try {
            return jdbcTemplate.query("SELECT * FROM oviuser", new OVIUserRowMapper());
        } catch(EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
    public List<OVIUser> getOVIUsersByStatus(String status) {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM oviuser WHERE status = ?",
                    new OVIUserRowMapper(), status);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
}