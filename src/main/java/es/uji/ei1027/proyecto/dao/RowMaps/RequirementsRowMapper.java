package es.uji.ei1027.proyecto.dao.RowMaps;

import es.uji.ei1027.proyecto.modelo.Requirements;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class RequirementsRowMapper implements RowMapper<Requirements> {

    public Requirements mapRow(ResultSet rs, int rowNum) throws SQLException {
        Requirements r = new Requirements();

        r.setIdRequirement(rs.getInt("idRequirement"));
        r.setRequirement(rs.getString("requirement"));

        return r;
    }
}