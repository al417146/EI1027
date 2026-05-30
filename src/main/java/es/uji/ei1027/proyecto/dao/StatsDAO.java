package es.uji.ei1027.proyecto.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
public class StatsDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // Servicios activos: contratos con fecha inicio <= hoy y (fecha fin nula o > hoy)
    public int countActiveServices() {
        String sql = "SELECT COUNT(*) FROM contract WHERE (dateend IS NULL OR dateend > CURRENT_DATE) AND datestart <= CURRENT_DATE";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    // Solicitudes pendientes (estado 'Pendiente' o 'Propuesta enviada')
    public int countPendingRequests() {
        String sql = "SELECT COUNT(*) FROM request WHERE status IN ('Pendiente', 'Propuesta enviada')";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    // Usuarios OVI con al menos un contrato activo
    public int countUsersWithActiveContracts() {
        String sql = "SELECT COUNT(DISTINCT r.dniuser) FROM request r " +
                "JOIN contract c ON r.idrequest = c.idrequest " +
                "WHERE (c.dateend IS NULL OR c.dateend > CURRENT_DATE) AND c.datestart <= CURRENT_DATE";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    // Usuarios OVI que tienen solicitudes pendientes (demandantes pendientes de asistente)
    public int countUsersWithPendingRequests() {
        String sql = "SELECT COUNT(DISTINCT dniuser) FROM request WHERE status IN ('Pendiente', 'Propuesta enviada')";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    // Total de usuarios OVI registrados
    public int countTotalOVIUsers() {
        String sql = "SELECT COUNT(*) FROM oviuser";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    // Profesionales (PAP/PATI) aceptados
    public int countActivePATIs() {
        String sql = "SELECT COUNT(*) FROM pap_pati WHERE status = 'Aceptado'";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    // Estadísticas de asistencia a actividades de formación
    public List<Map<String, Object>> getTrainingAttendanceStats() {
        String sql = "SELECT a.name AS activity_name, " +
                "COUNT(ar.idregist) AS total_registrations, " +
                "SUM(CASE WHEN ar.attended = true THEN 1 ELSE 0 END) AS attended, " +
                "ROUND(100.0 * SUM(CASE WHEN ar.attended = true THEN 1 ELSE 0 END) / NULLIF(COUNT(ar.idregist), 0), 1) AS attendance_rate " +
                "FROM activity a " +
                "LEFT JOIN activityregistration ar ON a.idactivity = ar.idactivity " +
                "WHERE a.type = 'formacion' " +
                "GROUP BY a.idactivity, a.name " +
                "ORDER BY a.actdate DESC";
        return jdbcTemplate.queryForList(sql);
    }

    // Resumen de estados de todas las solicitudes
    public List<Map<String, Object>> getRequestStatusSummary() {
        String sql = "SELECT status, COUNT(*) AS count FROM request GROUP BY status";
        return jdbcTemplate.queryForList(sql);
    }

    // Top 3 profesionales con más contratos asignados
    public List<Map<String, Object>> getTopProfessionalsByContracts(int limit) {
        String sql = "SELECT p.name, p.dni, COUNT(c.idcontract) AS contract_count " +
                "FROM pap_pati p " +
                "JOIN contract c ON p.dni = c.dnicand " +
                "GROUP BY p.dni, p.name " +
                "ORDER BY contract_count DESC " +
                "LIMIT ?";
        return jdbcTemplate.queryForList(sql, limit);
    }
}