package es.uji.ei1027.proyecto.RowMaps;
import es.uji.ei1027.proyecto.modelo.OVIUser;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
public final class OVIUserRowMapper implements RowMapper<OVIUser> {

    public OVIUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        OVIUser user = new OVIUser();
        user.setDNI(rs.getString("dni"));
        user.setName(rs.getString("name"));
        java.sql.Date sqlDate = rs.getDate("birth_date");
        user.setBirthDate(sqlDate != null ? new java.util.Date(sqlDate.getTime()) : null);
        user.setGender(rs.getString("gender"));
        user.setPhone(rs.getString("phone"));
        user.setMail(rs.getString("mail"));
        user.setAddress(rs.getString("address"));
        user.setStatus(rs.getString("status"));
        return user;
    }
}