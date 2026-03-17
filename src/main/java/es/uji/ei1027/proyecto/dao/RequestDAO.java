package es.uji.ei1027.proyecto.dao;

import es.uji.ei1027.proyecto.dao.RowMaps.RequestRowMapper;
import es.uji.ei1027.proyecto.modelo.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
@Repository
public class RequestDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addRequest(Request r){
        jdbcTemplate.update("INSERT INTO Request VALUES (?,?,?,?)",
                r.getDNIUser(),
                r.getDate(),
                r.getIdRequirement(),
                r.getDNICand());
    }

    public void deleteRequest(String DNIUser){
        jdbcTemplate.update("DELETE FROM Request WHERE DNIUser=?",
                DNIUser);
    }

    public void updateRequest(Request r){
        jdbcTemplate.update("UPDATE Request SET date=?, idRequirement=?, DNICand=? WHERE DNIUser=?",
                r.getDate(),
                r.getIdRequirement(),
                r.getDNICand(),
                r.getDNIUser());
    }

    public Request getRequest(String DNIUser){
        try{
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM Request WHERE DNIUser=?",
                    new RequestRowMapper(),
                    DNIUser);
        } catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    public List<Request> getRequests(){
        try{
            return jdbcTemplate.query("SELECT * FROM Request",
                    new RequestRowMapper());
        } catch(EmptyResultDataAccessException e){
            return new ArrayList<>();
        }
    }
}