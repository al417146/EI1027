package es.uji.ei1027.proyecto.RowMaps;
import es.uji.ei1027.proyecto.modelo.PATI;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
public final class PATIRowMapper implements RowMapper<PATI> {
    public PATI mapRow(ResultSet rs, int rowNum) throws SQLException {
        PATI pati = new PATI();
        pati.setDNI(rs.getString("dni"));
        pati.setName(rs.getString("name"));
        java.sql.Date sqlDate = rs.getDate("birth_date");
        pati.setBirthDate(sqlDate != null ? new java.util.Date(sqlDate.getTime()) : null);
        pati.setGender(rs.getString("gender"));
        pati.setPhone(rs.getString("phone"));
        pati.setMail(rs.getString("mail"));
        pati.setAddress(rs.getString("address"));
        pati.setStatus(rs.getString("status"));
        return pati;
    }
}