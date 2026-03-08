package es.uji.ei1027.proyecto.dao.RowMaps;

import es.uji.ei1027.proyecto.modelo.Activity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class ActivityRowMapper implements RowMapper<Activity> {

    public Activity mapRow(ResultSet rs, int rowNum) throws SQLException {
        Activity activity = new Activity();

        activity.setIdAct(rs.getInt("idAct"));
        activity.setName(rs.getString("name"));
        activity.setPlace(rs.getString("place"));

        return activity;
    }
}
