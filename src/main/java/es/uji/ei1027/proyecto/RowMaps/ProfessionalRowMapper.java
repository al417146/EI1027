package es.uji.ei1027.proyecto.RowMaps;
import es.uji.ei1027.proyecto.modelo.Professional;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
public final class ProfessionalRowMapper implements RowMapper<Professional> {
    public Professional mapRow(ResultSet rs, int rowNum) throws SQLException {
        Professional p = new Professional();
        p.setDNI(rs.getString("dni"));
        p.setName(rs.getString("name"));
        p.setPhone(rs.getString("phone"));
        p.setMail(rs.getString("email"));
        p.setGenre(rs.getString("genre"));
        p.setAddress(rs.getString("address"));
        p.setUniqueSpeciality(rs.getString("speciality"));
        p.setHistorial(rs.getString("historial"));
        return p;
    }
}
