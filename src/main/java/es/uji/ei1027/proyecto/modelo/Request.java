package es.uji.ei1027.proyecto.modelo;

import java.util.Date;

public class Request {
    String DNIUser;
    Date date;
    int idRequirement;
    String DNICand;

    public String getDNIUser() {
        return DNIUser;
    }

    public void setDNIUser(String DNIUser) {
        this.DNIUser = DNIUser;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getIdRequirement() {
        return idRequirement;
    }

    public void setIdRequirement(int idRequirement) {
        this.idRequirement = idRequirement;
    }

    public String getDNICand() {
        return DNICand;
    }

    public void setDNICand(String DNICand) {
        this.DNICand = DNICand;
    }

    @Override
    public String toString() {
        return "Request {" +
                " DNIUser = '" + DNIUser + '\'' +
                ", date = " + date +
                ", idRequirement = " + idRequirement +
                ", DNICand = '" + DNICand + '\'' +
                '}';
    }
}
