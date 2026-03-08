package es.uji.ei1027.proyecto.dao;

import es.uji.ei1027.proyecto.dao.RowMaps.ActivityRowMapper;
import es.uji.ei1027.proyecto.modelo.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class ActivityDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addActivity(Activity a){
        jdbcTemplate.update("INSERT INTO Activity VALUES (?,?,?)",
                a.getIdAct(),
                a.getName(),
                a.getPlace());
    }

    public void deleteActivity(int idAct){
        jdbcTemplate.update("DELETE FROM Activity WHERE idAct=?",
                idAct);
    }

    public void updateActivity(Activity a){
        jdbcTemplate.update("UPDATE Activity SET name=?, place=? WHERE idAct=?",
                a.getName(),
                a.getPlace(),
                a.getIdAct());
    }

    public Activity getActivity(int idAct){
        try{
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM Activity WHERE idAct=?",
                    new ActivityRowMapper(),
                    idAct);
        } catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    public List<Activity> getActivities(){
        try{
            return jdbcTemplate.query("SELECT * FROM Activity",
                    new ActivityRowMapper());
        } catch(EmptyResultDataAccessException e){
            return new ArrayList<>();
        }
    }
}