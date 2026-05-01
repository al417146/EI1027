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

@Repository
public class ContractDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addContract(Contract c){
        jdbcTemplate.update("INSERT INTO Contract VALUES (?,?,?,?,?)",
                c.getIdContract(),
                c.getDateStart(),
                c.getDateEnd(),
                c.getIdRequest(),
                c.getDNICand());
    }

    public void deleteContract(int idContract){
        jdbcTemplate.update("DELETE FROM Contract WHERE idContract=?",
                        idContract);
    }

    public void updateContract(Contract c){
        jdbcTemplate.update("UPDATE Contract SET dateStart=?, " +
                        "dateEnd=?, idRequest=?, DNICand=? WHERE idContract=?",
                c.getDateStart(),
                c.getDateEnd(),
                c.getIdRequest(),
                c.getDNICand(),
                c.getIdContract());
    }

    public void updateContractEndDate(String idContract, Date dateEnd){
        jdbcTemplate.update("UPDATE Contract SET dateEnd = ? WHERE idContract = ?",
                dateEnd, idContract);
    }

    public Contract getContract(int idContract){
        try{
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM Contract WHERE idContract=?",
                    new ContractRowMapper(), idContract);
        } catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    public Contract getContractByPATI(String DNICand) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Contract WHERE DNICand = ?",
                    new Object[]{DNICand}, new ContractRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Contract> getContracts(){
        try{
            return jdbcTemplate.query("SELECT * FROM Contract", new ContractRowMapper());
        } catch(EmptyResultDataAccessException e){
            return new ArrayList<>();
        }
    }

    public Contract getContractById(String idContract) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Contract WHERE idContract = ?",
                    new Object[]{idContract}, new ContractRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Contract getContractsByPATI(String dniPati) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM Contract WHERE DNICand = ?",
                    new ContractRowMapper(), dniPati);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}