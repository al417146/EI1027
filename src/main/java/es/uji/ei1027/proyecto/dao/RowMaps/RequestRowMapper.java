package es.uji.ei1027.proyecto.dao.RowMaps;

import es.uji.ei1027.proyecto.modelo.Request;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public final class RequestRowMapper implements RowMapper<Request> {

    public Request mapRow(ResultSet rs, int rowNum) throws SQLException {
        Request r = new Request();
        r.setDNIUser(rs.getString("DNIUser"));
        r.setDate(rs.getObject("date", Date.class));
        r.setIdRequest(rs.getInt("idRequest"));
        r.setStatus(rs.getString("status"));
        r.setIdContract(rs.getInt("idContract"));
        r.setIdNeg(rs.getString("idNeg"));
        r.setIdRequirement(rs.getInt("idRequirement"));

        return r;
    }
}
