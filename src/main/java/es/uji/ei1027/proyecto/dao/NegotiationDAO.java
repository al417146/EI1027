package es.uji.ei1027.proyecto.dao;

import es.uji.ei1027.proyecto.RowMaps.NegotiationRowMapper;
import es.uji.ei1027.proyecto.modelo.Negotiation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class NegotiationDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addNegotiation(Negotiation n){
        jdbcTemplate.update("INSERT INTO Negotiation VALUES (?,?,?,?)",
                n.getIdNeg(),
                n.getDate(),
                n.getDNICand(),
                n.getStatus());
    }

    public void deleteNegotiation(int idNeg){
        jdbcTemplate.update("DELETE FROM Negotiation WHERE idNeg=?",
                idNeg);
    }

    public void updateNegotiation(Negotiation n){
        jdbcTemplate.update("UPDATE Negotiation SET date=?, DNICand=?, status=? WHERE idNeg=?",
                n.getDate(), n.getDNICand(), n.getStatus(), n.getIdNeg());
    }

    public Negotiation getNegotiation(int idNeg){
        try{
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM Negotiation WHERE idNeg=?",
                    new NegotiationRowMapper(), idNeg);
        } catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    public List<Negotiation> getNegotiations(){
        try{
            return jdbcTemplate.query("SELECT * FROM Negotiation", new NegotiationRowMapper());
        } catch(EmptyResultDataAccessException e){
            return new ArrayList<>();
        }
    }
}