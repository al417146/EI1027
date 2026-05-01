package es.uji.ei1027.proyecto.RowMaps;

import es.uji.ei1027.proyecto.modelo.Speciality;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public final class SpecialityRowMapper implements RowMapper<Speciality> {
    @Override
    public Speciality mapRow(ResultSet rs, int rowNum) throws SQLException {
        Speciality s = new Speciality();
        s.setIdSpeciality(rs.getString("idSpeciality"));
        s.setDescrip(rs.getString("descrip"));
        return s;
    }
}