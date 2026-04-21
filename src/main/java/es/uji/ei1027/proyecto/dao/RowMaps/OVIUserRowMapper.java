package es.uji.ei1027.proyecto.dao.RowMaps;

import es.uji.ei1027.proyecto.modelo.OVIUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date; // Añadido

public final class OVIUserRowMapper implements RowMapper<OVIUser> {

    public OVIUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        OVIUser user = new OVIUser();

        user.setDNI(rs.getString("DNI"));
        user.setName(rs.getString("name"));
        user.setBirthDate(rs.getObject("birth_date", Date.class));
        user.setGender(rs.getString("gender"));
        user.setPhone(rs.getString("phone"));
        user.setMail(rs.getString("mail"));
        user.setAddress(rs.getString("address"));
        user.setStatus(rs.getString("status"));

        return user;
    }
}