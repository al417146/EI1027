package es.uji.ei1027.proyecto.modelo;

import java.util.Date;

public class Negotiation {
    String idNeg;
    Date date;
    String DNICand;

    public String getIdNeg() {
        return idNeg;
    }

    public void setIdNeg(String idNeg) {
        this.idNeg = idNeg;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDNICand() {
        return DNICand;
    }

    public void setDNICand(String DNICand) {
        this.DNICand = DNICand;
    }

    @Override
    public String toString() {
        return "Negotiation {" +
                " idNeg = '" + idNeg + '\'' +
                ", date = " + date +
                ", DNICand = '" + DNICand + '\'' +
                '}';
    }
}
