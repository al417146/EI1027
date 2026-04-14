package es.uji.ei1027.proyecto.dao.RowMaps;

import es.uji.ei1027.proyecto.modelo.Activity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public final class ActivityRowMapper implements RowMapper<Activity> {

    @Override
    public Activity mapRow(ResultSet rs, int rowNum) throws SQLException {
        Activity activity = new Activity();

        activity.setIdAct(rs.getInt("idAct"));
        activity.setName(rs.getString("name"));
        activity.setPlace(rs.getString("place"));
        activity.setActDate(rs.getObject("actDate", Date.class));
        activity.setDNIProf(rs.getString("DNIProf"));

        return activity;
    }
}

