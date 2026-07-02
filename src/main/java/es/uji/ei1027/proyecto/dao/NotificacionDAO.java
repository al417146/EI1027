package es.uji.ei1027.proyecto.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class NotificacionDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void enviar(String destinatario, String contenido) {
        jdbcTemplate.update(
            "INSERT INTO notificacion (destinatario, contenido) VALUES (?, ?)",
            destinatario, contenido);
    }

    public List<Map<String, Object>> getNotificaciones(String destinatario) {
        try {
            return jdbcTemplate.queryForList(
                "SELECT * FROM notificacion WHERE destinatario = ? ORDER BY fechaenvio DESC",
                destinatario);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public int countNoLeidas(String destinatario) {
        try {
            Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM notificacion WHERE destinatario = ? AND leido = false",
                Integer.class, destinatario);
            return count != null ? count : 0;
        } catch (Exception e) {
            return 0;
        }
    }

    public void marcarLeida(int idNotificacion) {
        jdbcTemplate.update("UPDATE notificacion SET leido = true WHERE idnotificacion = ?", idNotificacion);
    }

    public void marcarTodasLeidas(String destinatario) {
        jdbcTemplate.update("UPDATE notificacion SET leido = true WHERE destinatario = ?", destinatario);
    }

    public List<Map<String, Object>> getNotificacionesEnviadas() {
        try {
            return jdbcTemplate.queryForList(
                    "SELECT n.*, COALESCE(o.name, p.name, n.destinatario) as destinatarioname " +
                            "FROM notificacion n " +
                            "LEFT JOIN oviuser o ON n.destinatario = o.dni " +
                            "LEFT JOIN pap_pati p ON n.destinatario = p.dni " +
                            "ORDER BY n.fechaenvio DESC");
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
