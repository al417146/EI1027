package es.uji.ei1027.proyecto.dao;

import es.uji.ei1027.proyecto.dao.RowMaps.PATIRowMapper;
import es.uji.ei1027.proyecto.modelo.PATI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
@Repository
public class patiDAO {
    private JdbcTemplate jdbcTemplate;

    public int countPATIsByOVIUser(String dni) {
        String sql = "SELECT COUNT(*) FROM PATI p " +
                "JOIN Contract c ON p.DNI = c.DNICand " +
                "JOIN Request r ON c.idContract = r.idContract " +
                "JOIN OVIUser o ON o.DNI = r.DNIUser " +
                "WHERE o.DNI = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, dni);
    }

    @Autowired
    public void setDataSource(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<PATI> getPATIsByOVIUser(String dniOviUser) {
        try {
            return jdbcTemplate.query(
                    "SELECT p.* " +
                            "FROM PATI p " +
                            "JOIN Contract c ON p.DNI = c.DNICand " +
                            "JOIN Request r ON c.idContract = r.idContract " +
                            "JOIN OVIUser o ON o.DNI = r.DNIUser " +
                            "WHERE o.DNI = ?",
                    new PATIRowMapper(),
                    dniOviUser
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    public void addPATI(PATI p){
        jdbcTemplate.update("INSERT INTO PATI VALUES (?,?,?,?,?,?,?,?)",
                p.getDNI(),
                p.getName(),
                p.getBirthDate(),
                p.getGender(),
                p.getPhone(),
                p.getMail(),
                p.getAddress(),
                p.getStatus());
    }

    public void deletePATI(String DNI){
        jdbcTemplate.update("DELETE FROM PATI WHERE DNI=?",
                DNI);
    }

    public void updatePATI(PATI p){
        jdbcTemplate.update("UPDATE PATI SET name=?, birth_date=?, gender=?, phone=?, mail=?, address=? WHERE DNI=?",
                p.getName(),
                p.getBirthDate(),
                p.getGender(),
                p.getPhone(),
                p.getMail(),
                p.getAddress(),
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