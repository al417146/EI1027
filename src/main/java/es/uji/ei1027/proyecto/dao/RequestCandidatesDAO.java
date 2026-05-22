package es.uji.ei1027.proyecto.dao;

import es.uji.ei1027.proyecto.modelo.PATI;
import es.uji.ei1027.proyecto.RowMaps.PATIRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RequestCandidatesDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addCandidate(int idRequest, String dniCand) {
        jdbcTemplate.update(
            "INSERT INTO request_candidates (idrequest, dnicand) VALUES (?, ?)",
            idRequest, dniCand);
    }

    public void deleteCandidates(int idRequest) {
        jdbcTemplate.update(
            "DELETE FROM request_candidates WHERE idrequest = ?", idRequest);
    }

    public List<PATI> getCandidatesForRequest(int idRequest) {
        try {
            return jdbcTemplate.query(
                "SELECT p.* FROM pap_pati p " +
                "JOIN request_candidates rc ON p.dni = rc.dnicand " +
                "WHERE rc.idrequest = ?",
                new PATIRowMapper(), idRequest);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
