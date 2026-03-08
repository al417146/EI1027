package es.uji.ei1027.proyecto.dao;

import es.uji.ei1027.proyecto.dao.RowMaps.PATIRowMapper;
import es.uji.ei1027.proyecto.modelo.PATI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class patiDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addPATI(PATI p){
        jdbcTemplate.update("INSERT INTO PATI VALUES (?,?,?,?,?,?,?,?)",
                p.getDNI(),
                p.getName(),
                p.getAge(),
                p.getGender(),
                p.getPhone(),
                p.getMail(),
                p.getAddress(),
                p.getIdSpeciality());
    }

    public void deletePATI(String DNI){
        jdbcTemplate.update("DELETE FROM PATI WHERE DNI=?",
                DNI);
    }

    public void updatePATI(PATI p){
        jdbcTemplate.update("UPDATE PATI SET name=?, age=?, gender=?, phone=?, mail=?, address=?, idSpeciality=? WHERE DNI=?",
                p.getName(),
                p.getAge(),
                p.getGender(),
                p.getPhone(),
                p.getMail(),
                p.getAddress(),
                p.getIdSpeciality(),
                p.getDNI());
    }

    public PATI getPATI(String DNI){
        try{
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM PATI WHERE DNI=?",
                    new PATIRowMapper(),
                    DNI);
        } catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    public List<PATI> getPATIs(){
        try{
            return jdbcTemplate.query("SELECT * FROM PATI",
                    new PATIRowMapper());
        } catch(EmptyResultDataAccessException e){
            return new ArrayList<>();
        }
    }
}
