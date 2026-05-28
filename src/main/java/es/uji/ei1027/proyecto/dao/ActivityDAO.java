package es.uji.ei1027.proyecto.dao;

import es.uji.ei1027.proyecto.RowMaps.ActivityRowMapper;
import es.uji.ei1027.proyecto.modelo.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ActivityDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Activity> getActivities() {
        try {
            return jdbcTemplate.query("SELECT * FROM activity ORDER BY actdate", new ActivityRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    public List<Activity> getActivitiesByType(String type) {
        try {
            return jdbcTemplate.query("SELECT * FROM activity WHERE type = ? ORDER BY actdate",
                new ActivityRowMapper(), type);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    public Activity getActivity(int idActivity) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM activity WHERE idactivity = ?",
                new ActivityRowMapper(), idActivity);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void addActivity(Activity a) {
        jdbcTemplate.update(
            "INSERT INTO activity (name, place, actdate, dniprof, type, maxparticipants, description) VALUES (?,?,?,?,?,?,?)",
            a.getName(), a.getPlace(), a.getActDate(), a.getDniProf(),
            a.getType(), a.getMaxParticipants(), a.getDescription());
    }

    public void updateActivity(Activity a) {
        jdbcTemplate.update(
            "UPDATE activity SET name=?, place=?, actdate=?, dniprof=?, type=?, maxparticipants=?, description=? WHERE idactivity=?",
            a.getName(), a.getPlace(), a.getActDate(), a.getDniProf(),
            a.getType(), a.getMaxParticipants(), a.getDescription(), a.getIdActivity());
    }

    public void deleteActivity(int idActivity) {
        jdbcTemplate.update("DELETE FROM activity WHERE idactivity=?", idActivity);
    }

    public int countRegistrations(int idActivity) {
        Integer count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM activityregistration WHERE idactivity = ?",
            Integer.class, idActivity);
        return count != null ? count : 0;
    }
}
