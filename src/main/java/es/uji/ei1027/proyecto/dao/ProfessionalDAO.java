package es.uji.ei1027.proyecto.dao;

import es.uji.ei1027.proyecto.dao.RowMaps.ProfessionalRowMapper;
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
    public void setDataSource(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addProfessional(Professional p){
        jdbcTemplate.update("INSERT INTO Professional VALUES (?,?,?,?,?,?,?,?)",
                p.getDNI(),
                p.getName(),
                p.getPhone(),
                p.getMail(),
                p.getGenre(),
                p.getAddress(),
                p.getUniqueSpeciality(),
                p.getAge());
    }

    public void deleteProfessional(String DNI){
        jdbcTemplate.update("DELETE FROM Professional WHERE DNI=?",
                DNI);
    }

    public void updateProfessional(Professional p){
        jdbcTemplate.update("UPDATE Professional SET name=?, phone=?, mail=?, genre=?, address=?, uniqueSpeciality=?, age=? WHERE DNI=?",
                p.getName(),
                p.getPhone(),
                p.getMail(),
                p.getGenre(),
                p.getAddress(),
                p.getUniqueSpeciality(),
                p.getAge(),
                p.getDNI());
    }

    public Professional getProfessional(String DNI){
        try{
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM Professional WHERE DNI=?",
                    new ProfessionalRowMapper(),
                    DNI);
        } catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    public List<Professional> getProfessionals(){
        try{
            return jdbcTemplate.query("SELECT * FROM Professional",
                    new ProfessionalRowMapper());
        } catch(EmptyResultDataAccessException e){
            return new ArrayList<>();
        }
    }
}