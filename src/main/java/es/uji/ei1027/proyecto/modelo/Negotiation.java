package es.uji.ei1027.proyecto.modelo;

import java.util.Date;

public class Negotiation {
    String idNeg;
    Date dateStart;
    String DNICand;
    String status;

    public String getIdNeg() {
        return idNeg;
    }

    public void setIdNeg(String idNeg) {
        this.idNeg = idNeg;
    }

    public Date getDate() {
        return dateStart;
    }

    public void setDate(Date dateStart) {
        this.dateStart = dateStart;
    }

    public String getDNICand() {
        return DNICand;
    }

    public void setDNICand(String DNICand) {
        this.DNICand = DNICand;
    }

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}

    @Override
    public String toString() {
        return "Negotiation {" +
                " idNeg = '" + idNeg + '\'' +
                ", date = " + dateStart +
                ", DNICand = '" + DNICand + '\'' +
                ", status = '" + status + '\'' +
                '}';
    }
}
