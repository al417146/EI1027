package es.uji.ei1027.proyecto.dao.RowMaps;

import es.uji.ei1027.proyecto.modelo.Request;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public final class RequestRowMapper implements RowMapper<Request> {

    public Request mapRow(ResultSet rs, int rowNum) throws SQLException {
        Request request = new Request();

        request.setDNIUser(rs.getString("DNIUser"));
        request.setDate(rs.getObject("date", Date.class));
        request.setIdRequirement(rs.getInt("idRequirement"));
        request.setDNICand(rs.getString("DNICand"));

        return request;
    }
}
