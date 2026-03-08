package es.uji.ei1027.proyecto.dao.RowMaps;

import es.uji.ei1027.proyecto.modelo.PATI;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class PATIRowMapper implements RowMapper<PATI> {

    public PATI mapRow(ResultSet rs, int rowNum) throws SQLException {
        PATI pati = new PATI();

        pati.setDNI(rs.getString("DNI"));
        pati.setName(rs.getString("name"));
        pati.setAge(rs.getInt("age"));
        pati.setGender(rs.getString("gender"));
        pati.setPhone(rs.getString("phone"));
        pati.setMail(rs.getString("mail"));
        pati.setAddress(rs.getString("address"));
        pati.setIdSpeciality(rs.getInt("idSpeciality"));

        return pati;
    }
}