package es.uji.ei1027.proyecto.dao.RowMaps;

import es.uji.ei1027.proyecto.modelo.Negotiation;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public final class NegotiationRowMapper implements RowMapper<Negotiation> {

    public Negotiation mapRow(ResultSet rs, int rowNum) throws SQLException {
        Negotiation n = new Negotiation();

        n.setIdNeg(rs.getString("idNeg"));
        n.setDate(rs.getObject("date", Date.class));
        n.setDNICand(rs.getString("DNICand"));

        return n;
    }
}