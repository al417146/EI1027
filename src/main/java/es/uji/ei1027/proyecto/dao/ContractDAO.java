package es.uji.ei1027.proyecto.dao;

import es.uji.ei1027.proyecto.dao.RowMaps.ContractRowMapper;
import es.uji.ei1027.proyecto.modelo.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class ContractDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addContract(Contract c){
        jdbcTemplate.update("INSERT INTO Contract VALUES (?,?,?)",
                c.getIdContract(),
                c.getDateStart(),
                c.getDateEnd());
    }

    public void deleteContract(String idContract){
        jdbcTemplate.update("DELETE FROM Contract WHERE idContract=?",
                idContract);
    }

    public void updateContract(Contract c){
        jdbcTemplate.update("UPDATE Contract SET dateStart=?, dateEnd=? WHERE idContract=?",
                c.getDateStart(),
                c.getDateEnd(),
                c.getIdContract());
    }

    public Contract getContract(String idContract){
        try{
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM Contract WHERE idContract=?",
                    new ContractRowMapper(),
                    idContract);
        } catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    public List<Contract> getContracts(){
        try{
            return jdbcTemplate.query("SELECT * FROM Contract",
                    new ContractRowMapper());
        } catch(EmptyResultDataAccessException e){
            return new ArrayList<>();
        }
    }
}