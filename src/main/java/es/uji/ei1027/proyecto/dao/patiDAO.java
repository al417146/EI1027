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
        String sql = "SELECT COUNT(*) FROM pap_pati p " +
                "JOIN contract c ON p.dni = c.dnicand " +
                "JOIN request r ON c.idrequest = r.idrequest " +
                "LEFT JOIN oviuser o ON o.dni = r.dniuser " +
                "WHERE r.dniuser = ?";
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
                            "FROM pap_pati p " +
                            "JOIN contract c ON p.dni = c.dnicand " +
                            "JOIN request r ON c.idrequest = r.idrequest " +
                            "LEFT JOIN oviuser o ON o.dni = r.dniuser " +
                            "WHERE r.dniuser = ?",
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
            String sql = "SELECT s.idspeciality, s.descrip FROM speciality s " +
                    "JOIN has h ON s.idspeciality = h.idspeciality " +
                    "WHERE h.dnipati = ?";
            return jdbcTemplate.query(sql, rs -> {
                HashMap<Integer, String> hashMap = new HashMap<>();
                while (rs.next()) {
                    hashMap.put(rs.getInt("idspeciality"), rs.getString("descrip"));
                }
                return hashMap;
            }, dniPati);
        } catch (EmptyResultDataAccessException e) {
            return new HashMap<>();
        }
    }

    public List<PATI> getAvailablePATIs() {
        try {
            List<PATI> patis = jdbcTemplate.query(
                    "SELECT * FROM pap_pati WHERE status = 'Aceptado'",
                    new PATIRowMapper());
            for (PATI p : patis) {
                p.setSpecialties(getSpecialtiesForPati(p.getDNI()));
            }
            return patis;
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    public void addPATI(PATI p){
        jdbcTemplate.update("INSERT INTO pap_pati (dni, name, birth_date, gender, phone, mail, address, status) VALUES (?,?,?,?,?,?,?,?)",
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
        jdbcTemplate.update("DELETE FROM pap_pati WHERE dni=?", DNI);
    }

    public void updatePATI(PATI p){
        jdbcTemplate.update("UPDATE pap_pati SET name=?, birth_date=?, " +
                        "gender=?, phone=?, mail=?, address=?, status=? WHERE dni=?",
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
                    "SELECT * FROM pap_pati WHERE dni=?",
                    new PATIRowMapper(),
                    DNI);
        } catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    public List<PATI> getPATIs(){
        try{
            return jdbcTemplate.query("SELECT * FROM pap_pati", new PATIRowMapper());
        } catch(EmptyResultDataAccessException e){
            return new ArrayList<>();
        }
    }

    public List<PATI> findMatch(String address, String gender, String topic) {
        try {
            String sql =
                    "SELECT DISTINCT p.* " +
                            "FROM pap_pati p " +
                            "WHERE p.status = 'Aceptado' " +
                            "AND (? IS NULL OR ? = '' OR p.gender = ?) " +
                            "AND (? IS NULL OR ? = '' OR p.address ILIKE ?)";

            return jdbcTemplate.query(
                    sql,
                    new PATIRowMapper(),
                    gender, gender, gender,
                    address, address, "%" + address + "%"
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }

    }
    public void addSpecialityToPATI(String dniPati, int idSpeciality) {
        jdbcTemplate.update(
                "INSERT INTO has (idspeciality, dnipati) VALUES (?, ?)",
                idSpeciality, dniPati);
    }
}