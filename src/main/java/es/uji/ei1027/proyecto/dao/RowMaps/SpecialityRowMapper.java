package es.uji.ei1027.proyecto.dao.RowMaps;

import es.uji.ei1027.proyecto.modelo.Speciality;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class SpecialityRowMapper implements RowMapper<Speciality> {

    public Speciality mapRow(ResultSet rs, int rowNum) throws SQLException {
        Speciality speciality = new Speciality();

        speciality.setIdSpeciality(rs.getInt("idSpeciality"));
        speciality.setSpeciality(rs.getString("descrip"));

        return speciality;
    }
}