package es.uji.ei1027.proyecto.dao;

import es.uji.ei1027.proyecto.RowMaps.RequestRowMapper;
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
        jdbcTemplate.update("INSERT INTO Request VALUES (?,?,?,?,?,?,?,?)",
                r.getDNIUser(),
                r.getDate(),
                r.getIdRequest(),
                r.getStatus(),
                r.getIdContract(),
                r.getIdNeg(),
                r.getIdRequirement(),
                r.getDNICand());

    }
    public List<Request> getRequestsByUser(String dniUser) {
        try {
            return jdbcTemplate.query("SELECT * FROM Request WHERE DNIUser = ? ORDER BY date DESC",
                    new Object[]{dniUser}, new RequestRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    // Solicitudes pendientes dirigidas a un profesional (PAP/PATI)
    public List<Request> getPendingRequestsForPati(String dniPati) {
        try {
            return jdbcTemplate.query("SELECT * FROM Request WHERE DNICand = ? AND status = 'Pendiente'",
                    new Object[]{dniPati}, new RequestRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

        public Request getRequestById(int id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Request WHERE idRequest = ?",
                    new Object[]{id}, new RequestRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    public void updateRequestStatus(int idRequest, String newStatus, int idContract) {
        String sql = "UPDATE Request SET status = ?, idContract = ? WHERE idRequest = ?";
        jdbcTemplate.update(sql, newStatus, idContract, idRequest);
    }

    public void deleteRequest(int idRequest){
        jdbcTemplate.update("DELETE FROM Request WHERE idRequest=?",
                idRequest);
    }

    public void updateRequest(Request r){
        jdbcTemplate.update("UPDATE Request SET DNIUser=?, date=?, status=?, " +
                        "idContract=?, idNeg=?, idRequirement=? WHERE idRequest=?",
                r.getDNIUser(),
                r.getDate(),
                r.getStatus(),
                r.getIdContract(),
                r.getIdNeg(),
                r.getIdRequirement(),
                r.getIdRequest()
        );

    }

    public Request getRequest(int idRequest){
        try{
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM Request WHERE idRequest=?",
                    new RequestRowMapper(), idRequest
                    );
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