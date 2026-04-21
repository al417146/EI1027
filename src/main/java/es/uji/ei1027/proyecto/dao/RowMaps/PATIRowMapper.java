package es.uji.ei1027.proyecto.dao.RowMaps;

import es.uji.ei1027.proyecto.modelo.PATI;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date; // Añadido

public final class PATIRowMapper implements RowMapper<PATI> {

    public PATI mapRow(ResultSet rs, int rowNum) throws SQLException {
        PATI pati = new PATI();

        pati.setDNI(rs.getString("DNI"));
        pati.setName(rs.getString("name"));
        // Cambiado de setAge(rs.getInt("age")) a setBirthDate
        pati.setBirthDate(rs.getObject("birth_date", Date.class));
        pati.setGender(rs.getString("gender"));
        pati.setPhone(rs.getString("phone"));
        pati.setMail(rs.getString("mail"));
        pati.setAddress(rs.getString("address"));
        pati.setStatus(rs.getString("idStatus"));

        return pati;
    }
}