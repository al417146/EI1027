package es.uji.ei1027.proyecto.RowMaps;

import es.uji.ei1027.proyecto.modelo.Contract;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class ContractRowMapper implements RowMapper<Contract> {

    @Override
    public Contract mapRow(ResultSet rs, int rowNum) throws SQLException {
        Contract contract = new Contract();
        contract.setIdContract(rs.getInt("idcontract"));
        java.sql.Date sqlStart = rs.getDate("datestart");
        contract.setDateStart(sqlStart != null ? new java.util.Date(sqlStart.getTime()) : null);
        java.sql.Date sqlEnd = rs.getDate("dateend");
        contract.setDateEnd(sqlEnd != null ? new java.util.Date(sqlEnd.getTime()) : null);
        contract.setIdRequest(rs.getInt("idrequest"));
        contract.setDNICand(rs.getString("dnicand"));
        return contract;
    }
}