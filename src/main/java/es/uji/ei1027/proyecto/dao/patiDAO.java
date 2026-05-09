package es.uji.ei1027.proyecto.dao;

import es.uji.ei1027.proyecto.RowMaps.PATIRowMapper;
import es.uji.ei1027.proyecto.modelo.PATI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class patiDAO {
    private JdbcTemplate jdbcTemplate;

    public int countPATIsByOVIUser(String dni) {
        String sql = "SELECT COUNT(*) FROM PATI p " +
                "JOIN Contract c ON p.DNI = c.DNICand " +
                "JOIN Request r ON c.idContract = r.idContract " +
                "JOIN OVIUser o ON o.DNI = r.DNIUser " +
                "WHERE o.DNI = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, dni);
    }

    @Autowired
    public void setDataSource(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<PATI> getPATIsByOVIUser(String dniOviUser) {
        try {
            List<PATI> patis = jdbcTemplate.query(
                    "SELECT p.* " +
                            "FROM PATI p " +
                            "JOIN Contract c ON p.DNI = c.DNICand " +
                            "JOIN Request r ON c.idContract = r.idContract " +
                            "JOIN OVIUser o ON o.DNI = r.DNIUser " +
                            "WHERE o.DNI = ?",
                    new PATIRowMapper(),
                    dniOviUser
            );
            for (PATI pati : patis) {
                HashMap<Integer, String> specs = getSpecialtiesForPati(pati.getDNI());
                pati.setSpecialties(specs);
            }
            return patis;
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    public HashMap<Integer, String> getSpecialtiesForPati(String dniPati) {
        try {
            String sql = "SELECT s.idSpeciality, s.descrip FROM Speciality s " +
                    "JOIN Has h ON s.idSpeciality = h.idSpeciality " +
                    "WHERE h.DNIPati = ?";
            return jdbcTemplate.query(sql, rs -> {
                HashMap<Integer, String> hashMap = new HashMap<>();
                while (rs.next()) {
                    hashMap.put(rs.getInt("idSpeciality"), rs.getString("descrip"));
                }
                return hashMap;
            }, dniPati);
        } catch (EmptyResultDataAccessException e) {
            return new HashMap<>();
        }
    }

    public List<PATI> getAvailablePATIs() {
        try {
            List<PATI> patis = jdbcTemplate.query("SELECT * FROM PATI WHERE status = 'Aceptado'", new PATIRowMapper());
            for (PATI p : patis) {
                p.setSpecialties(getSpecialtiesForPati(p.getDNI()));
            }
            return patis;
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    public void addPATI(PATI p){
        jdbcTemplate.update("INSERT INTO PATI VALUES (?,?,?,?,?,?,?,?)",
                p.getDNI(),
                p.getName(),
                p.getBirthDate(),
                p.getGender(),
                p.getPhone(),
                p.getMail(),
                p.getAddress(),
                p.getStatus());
    }

    public void deletePATI(String DNI){
        jdbcTemplate.update("DELETE FROM PATI WHERE DNI=?",
                DNI);
    }

    public void updatePATI(PATI p){
        jdbcTemplate.update("UPDATE PATI SET name=?, birth_date=?, " +
                        "gender=?, phone=?, mail=?, address=?, status=? WHERE DNI=?",
                p.getName(),
                p.getBirthDate(),
                p.getGender(),
                p.getPhone(),
                p.getMail(),
                p.getAddress(),
                p.getStatus(),
                p.getDNI());
    }

    public PATI getPATI(String DNI){
        try{
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM PATI WHERE DNI=?",
                    new PATIRowMapper(),
                    DNI);
        } catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    public List<PATI> getPATIs(){
        try{
            return jdbcTemplate.query("SELECT * FROM PATI",
                    new PATIRowMapper());
        } catch(EmptyResultDataAccessException e){
            return new ArrayList<>();
        }
    }

    public List<PATI> findMatch(String address, String gender, String topic) {
        try {
            String sql =
                    "SELECT DISTINCT p.* " +
                            "FROM PATI p " +
                            "JOIN HAS h ON p.DNI = h.DNIPati " +
                            "JOIN SPECIALITY s ON h.idSpeciality = s.idSpeciality " +
                            "WHERE p.status = 'Aceptado' " +
                            "AND p.gender = ? " +
                            "AND p.address LIKE ? " +
                            "AND s.speciality = ?";

            return jdbcTemplate.query(
                    sql,
                    new PATIRowMapper(),
                    gender,
                    "%" + address + "%",
                    topic
            );

        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }



}