package es.uji.ei1027.proyecto.RowMaps;

import es.uji.ei1027.proyecto.modelo.Registration;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationRowMapper implements RowMapper<Registration> {
    @Override
    public Registration mapRow(ResultSet rs, int rowNum) throws SQLException {
        Registration r = new Registration();
        r.setIdRegist(rs.getInt("idregist"));
        r.setIdActivity(rs.getInt("idactivity"));
        r.setDniUser(rs.getString("dniuser"));
        r.setAttended(rs.getBoolean("attended"));
        return r;
    }
}
