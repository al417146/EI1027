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
        jdbcTemplate.update(
                "INSERT INTO request (dniuser, date, status, idcontract, idneg, idrequirement, dnicand, preferredgender, preferredzone, preferredspeciality) VALUES (?,?,?,?,?,?,?,?,?,?)",
                r.getDNIUser(),
                r.getDate(),
                r.getStatus(),
                r.getIdContract(),
                r.getIdNeg(),
                r.getIdRequirement(),
                r.getDNICand(),
                r.getPreferredGender(),
                r.getPreferredZone(),
                r.getPreferredSpeciality());
    }
    public List<Request> getRequestsByUser(String dniUser) {
        try {
            return jdbcTemplate.query("SELECT * FROM request WHERE dniuser = ? ORDER BY date DESC",
                    new Object[]{dniUser}, new RequestRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    // Solicitudes pendientes dirigidas a un profesional (PAP/PATI)
    public List<Request> getPendingRequestsForPati(String dniPati) {
        try {
            return jdbcTemplate.query("SELECT * FROM request WHERE dnicand = ? AND status = 'Pendiente'",
                    new Object[]{dniPati}, new RequestRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

        public Request getRequestById(int id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM request WHERE idrequest = ?",
                    new Object[]{id}, new RequestRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    public void updateRequestStatus(int idRequest, String newStatus, int idContract) {
        String sql = "UPDATE request SET status = ?, idcontract = ? WHERE idrequest = ?";
        jdbcTemplate.update(sql, newStatus, idContract, idRequest);
    }

    public void deleteRequest(int idRequest){
        jdbcTemplate.update("DELETE FROM request WHERE idrequest=?",
                idRequest);
    }

    public void updateRequest(Request r){
        jdbcTemplate.update("UPDATE request SET dniuser=?, date=?, status=?, " +
                        "idcontract=?, idneg=?, idrequirement=?, dnicand=? WHERE idrequest=?",
                r.getDNIUser(),
                r.getDate(),
                r.getStatus(),
                r.getIdContract(),
                r.getIdNeg(),
                r.getIdRequirement(),
                r.getDNICand(),
                r.getIdRequest()
        );
    }

    public Request getRequest(int idRequest){
        try{
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM request WHERE idrequest=?",
                    new RequestRowMapper(), idRequest
                    );
        } catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    public List<Request> getRequests(){
        try{
            return jdbcTemplate.query("SELECT * FROM request",
                    new RequestRowMapper());
        } catch(EmptyResultDataAccessException e){
            return new ArrayList<>();
        }
    }
    public List<Request> getPendingRequests() {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM request WHERE status = 'Pendiente' ORDER BY date DESC",
                    new RequestRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

}