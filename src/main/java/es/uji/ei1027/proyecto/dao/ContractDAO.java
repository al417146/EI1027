package es.uji.ei1027.proyecto.dao;

import es.uji.ei1027.proyecto.RowMaps.ContractRowMapper;
import es.uji.ei1027.proyecto.modelo.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class ContractDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addContract(Contract c){
        jdbcTemplate.update(
                "INSERT INTO contract (datestart, dateend, idrequest, dnicand, status) VALUES (?,?,?,?,?)",
                c.getDateStart(),
                c.getDateEnd(),
                c.getIdRequest(),
                c.getDNICand(),
                c.getStatus());
    }

    public void deleteContract(int idContract){
        jdbcTemplate.update("DELETE FROM contract WHERE idcontract=?", idContract);
    }

    public void updateContract(Contract c){
        jdbcTemplate.update(
                "UPDATE contract SET datestart=?, dateend=?, idrequest=?, dnicand=? WHERE idcontract=?",
                c.getDateStart(),
                c.getDateEnd(),
                c.getIdRequest(),
                c.getDNICand(),
                c.getIdContract());
    }

    public void updateContractEndDate(int idContract, Date dateEnd){
        jdbcTemplate.update("UPDATE contract SET dateend = ? WHERE idcontract = ?",
                dateEnd, idContract);
    }

    public Contract getContract(int idContract){
        try{
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM contract WHERE idcontract=?",
                    new ContractRowMapper(), idContract);
        } catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    public Contract getContractByPATI(String dniCand) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM contract WHERE dnicand = ?",
                    new ContractRowMapper(), dniCand);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Contract> getContracts(){
        try{
            return jdbcTemplate.query("SELECT * FROM contract", new ContractRowMapper());
        } catch(EmptyResultDataAccessException e){
            return new ArrayList<>();
        }
    }

    public Contract getContractById(int idContract) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM contract WHERE idcontract = ?",
                    new ContractRowMapper(), idContract);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Contract> getContractsByUser(String dniUser) {
        try {
            return jdbcTemplate.query(
                    "SELECT c.* FROM contract c JOIN request r ON c.idrequest = r.idrequest WHERE r.dniuser = ?",
                    new ContractRowMapper(), dniUser);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    public List<Map<String, Object>> getContractsByUserWithName(String dniUser) {
        try {
            return jdbcTemplate.queryForList(
                    "SELECT c.*, p.name as candname " +
                            "FROM contract c " +
                            "JOIN request r ON c.idrequest = r.idrequest " +
                            "LEFT JOIN pap_pati p ON c.dnicand = p.dni " +
                            "WHERE r.dniuser = ?", dniUser);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void signarContracte(int idContract, String pdf) {
        jdbcTemplate.update(
                "UPDATE contract SET status = 'Activo', pdf = ? WHERE idcontract = ?",
                pdf, idContract);
    }

    public void updateDateEnd(int idContract, java.util.Date dateEnd) {
        jdbcTemplate.update(
                "UPDATE contract SET dateend = ? WHERE idcontract = ?",
                dateEnd, idContract);
    }

    public List<Map<String, Object>> getContractsByPATIWithName(String dniCand) {
        try {
            return jdbcTemplate.queryForList(
                    "SELECT c.*, o.name as username " +
                            "FROM contract c " +
                            "JOIN request r ON c.idrequest = r.idrequest " +
                            "LEFT JOIN oviuser o ON r.dniuser = o.dni " +
                            "WHERE c.dnicand = ?", dniCand);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    public void firmarPAP(int idContract, String pdf) {
        jdbcTemplate.update(
                "UPDATE contract SET status = 'Activo', pdf = ? WHERE idcontract = ?",
                pdf, idContract);
    }
}