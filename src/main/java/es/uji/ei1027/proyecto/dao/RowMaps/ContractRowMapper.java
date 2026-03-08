package es.uji.ei1027.proyecto.dao.RowMaps;

import es.uji.ei1027.proyecto.modelo.Contract;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public final class ContractRowMapper implements RowMapper<Contract> {

    public Contract mapRow(ResultSet rs, int rowNum) throws SQLException {
        Contract contract = new Contract();

        contract.setIdContract(rs.getString("idContract"));
        contract.setDateStart(rs.getObject("dateStart", Date.class));
        contract.setDateEnd(rs.getObject("dateEnd", Date.class));

        return contract;
    }
}