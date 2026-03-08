package es.uji.ei1027.proyecto.dao;

import es.uji.ei1027.proyecto.dao.RowMaps.RequirementsRowMapper;
import es.uji.ei1027.proyecto.modelo.Requirements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;


public class RequirementsDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addRequirement(Requirements r){
        jdbcTemplate.update("INSERT INTO Requirements VALUES (?,?)",
                r.getIdRequirement(),
                r.getRequirement());
    }

    public void deleteRequirement(int idRequirement){
        jdbcTemplate.update("DELETE FROM Requirements WHERE idRequirement=?",
                idRequirement);
    }

    public void updateRequirement(Requirements r){
        jdbcTemplate.update("UPDATE Requirements SET requirement=? WHERE idRequirement=?",
                r.getRequirement(),
                r.getIdRequirement());
    }

    public Requirements getRequirement(int id){
        try{
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM Requirements WHERE idRequirement=?",
                    new RequirementsRowMapper(),
                    id);
        } catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    public List<Requirements> getRequirements(){
        try{
            return jdbcTemplate.query("SELECT * FROM Requirements",
                    new RequirementsRowMapper());
        } catch(EmptyResultDataAccessException e){
            return new ArrayList<>();
        }
    }
}
