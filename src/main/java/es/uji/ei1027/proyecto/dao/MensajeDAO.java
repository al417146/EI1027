package es.uji.ei1027.proyecto.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class MensajeDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void enviarMensaje(int idRequest, String senderDNI, String contenido) {
        jdbcTemplate.update(
            "INSERT INTO mensaje (idrequest, senderDNI, contenido) VALUES (?, ?, ?)",
            idRequest, senderDNI, contenido);
    }

    public List<Map<String, Object>> getMensajes(int idRequest) {
        try {
            return jdbcTemplate.queryForList(
                "SELECT m.*, " +
                "COALESCE(o.name, p.name, m.senderdni) as sendername " +
                "FROM mensaje m " +
                "LEFT JOIN oviuser o ON m.senderdni = o.dni " +
                "LEFT JOIN pap_pati p ON m.senderdni = p.dni " +
                "WHERE m.idrequest = ? ORDER BY m.fechaenvio ASC", idRequest);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
