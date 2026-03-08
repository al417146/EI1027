package es.uji.ei1027.proyecto.dao.RowMaps;

import es.uji.ei1027.proyecto.modelo.OVIUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class OVIUserRowMapper implements RowMapper<OVIUser> {

    public OVIUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        OVIUser user = new OVIUser();

        user.setDNI(rs.getString("DNI"));
        user.setName(rs.getString("name"));
        user.setAge(rs.getInt("age"));
        user.setGender(rs.getString("gender"));
        user.setPhone(rs.getString("phone"));
        user.setMail(rs.getString("mail"));
        user.setAddress(rs.getString("address"));

        return user;
    }
}