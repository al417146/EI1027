package es.uji.ei1027.proyecto.RowMaps;

import es.uji.ei1027.proyecto.modelo.Activity;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ActivityRowMapper implements RowMapper<Activity> {
    @Override
    public Activity mapRow(ResultSet rs, int rowNum) throws SQLException {
        Activity a = new Activity();
        a.setIdActivity(rs.getInt("idactivity"));
        a.setName(rs.getString("name"));
        a.setPlace(rs.getString("place"));
        java.sql.Date sqlDate = rs.getDate("actdate");
        a.setActDate(sqlDate != null ? new java.util.Date(sqlDate.getTime()) : null);
        a.setDniProf(rs.getString("dniprof"));
        a.setType(rs.getString("type"));
        a.setMaxParticipants(rs.getObject("maxparticipants") != null ? rs.getInt("maxparticipants") : null);
        a.setDescription(rs.getString("description"));
        return a;
    }
}
