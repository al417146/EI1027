package es.uji.ei1027.proyecto.dao;

import es.uji.ei1027.proyecto.dao.RowMaps.OVIUserRowMapper;
import es.uji.ei1027.proyecto.modelo.OVIUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class OVIUserDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addOVIUser(OVIUser u){
        jdbcTemplate.update("INSERT INTO OVIUser VALUES (?,?,?,?,?,?,?)",
                u.getDNI(),
                u.getName(),
                u.getAge(),
                u.getGender(),
                u.getPhone(),
                u.getMail(),
                u.getAddress());
    }

    public void deleteOVIUser(String DNI){
        jdbcTemplate.update("DELETE FROM OVIUser WHERE DNI=?",
                DNI);
    }

    public void updateOVIUser(OVIUser u){
        jdbcTemplate.update("UPDATE OVIUser SET name=?, age=?, gender=?, phone=?, mail=?, address=? WHERE DNI=?",
                u.getName(),
                u.getAge(),
                u.getGender(),
                u.getPhone(),
                u.getMail(),
                u.getAddress(),
                u.getDNI());
    }

    public OVIUser getOVIUser(String DNI){
        try{
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM OVIUser WHERE DNI=?",
                    new OVIUserRowMapper(),
                    DNI);
        } catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    public List<OVIUser> getOVIUsers(){
        try{
            return jdbcTemplate.query("SELECT * FROM OVIUser",
                    new OVIUserRowMapper());
        } catch(EmptyResultDataAccessException e){
            return new ArrayList<>();
        }
    }
}