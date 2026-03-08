package es.uji.ei1027.proyecto.dao.RowMaps;

import es.uji.ei1027.proyecto.modelo.Professional;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class ProfessionalRowMapper implements RowMapper<Professional> {

    public Professional mapRow(ResultSet rs, int rowNum) throws SQLException {
        Professional p = new Professional();

        p.setDNI(rs.getString("DNI"));
        p.setName(rs.getString("name"));
        p.setPhone(rs.getString("phone"));
        p.setMail(rs.getString("mail"));
        p.setGenre(rs.getString("genre"));
        p.setAddress(rs.getString("address"));
        p.setUniqueSpeciality(rs.getString("uniqueSpeciality"));
        p.setAge(rs.getInt("age"));

        return p;
    }
}
