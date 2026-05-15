package es.uji.ei1027.proyecto.RowMaps;
import es.uji.ei1027.proyecto.modelo.Request;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
public final class RequestRowMapper implements RowMapper<Request> {
    public Request mapRow(ResultSet rs, int rowNum) throws SQLException {
        Request r = new Request();
        r.setDNIUser(rs.getString("dniuser"));
        java.sql.Date sqlDate = rs.getDate("date");
        r.setDate(sqlDate != null ? new java.util.Date(sqlDate.getTime()) : null);
        r.setIdRequest(rs.getInt("idrequest"));
        r.setStatus(rs.getString("status"));
        r.setIdContract(rs.getInt("idcontract"));
        r.setIdNeg(rs.getString("idneg"));
        r.setIdRequirement(rs.getInt("idrequirement"));
        r.setDNICand(rs.getString("dnicand"));
        r.setPreferredGender(rs.getString("preferredgender"));
        r.setPreferredZone(rs.getString("preferredzone"));
        r.setPreferredSpeciality(rs.getString("preferredspeciality"));
        return r;
    }
}