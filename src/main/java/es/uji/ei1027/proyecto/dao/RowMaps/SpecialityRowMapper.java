package es.uji.ei1027.proyecto.dao.RowMaps;

import es.uji.ei1027.proyecto.modelo.Speciality;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SpecialityRowMapper implements RowMapper<Speciality> {
    @Override
    public Speciality mapRow(ResultSet rs, int rowNum) throws SQLException {
        Speciality s = new Speciality();
        s.setIdSpeciality(rs.getString("idSpeciality"));
        s.setDescrip(rs.getString("descrip"));
        return s;
    }
}