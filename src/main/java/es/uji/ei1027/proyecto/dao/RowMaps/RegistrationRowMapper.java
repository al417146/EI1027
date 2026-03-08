package es.uji.ei1027.proyecto.dao.RowMaps;

import es.uji.ei1027.proyecto.modelo.Registration;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class RegistrationRowMapper implements RowMapper<Registration> {

    public Registration mapRow(ResultSet rs, int rowNum) throws SQLException {
        Registration r = new Registration();

        r.setDNIUser(rs.getString("DNIUser"));
        r.setDNIAssistant(rs.getString("DNIAssistant"));
        r.setCode(rs.getString("code"));
        r.setAttendace(rs.getBoolean("attendace"));

        return r;
    }
}
