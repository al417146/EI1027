package es.uji.ei1027.proyecto.modelo;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Negotiation {
    int idNeg;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    Date dateStart;

    String DNICand;
    String status;

    public int getIdNeg() {
        return idNeg;
    }

    public void setIdNeg(int idNeg) {
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
