package es.uji.ei1027.proyecto.modelo;

import java.util.Date;

public class Request {
    String DNIUser;
    Date date;
    int idRequest;
    String status;

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

    public int getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(int idRequest) {
        this.idRequest = idRequest;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Request {" +
                " DNIUser = '" + DNIUser + '\'' +
                ", date = " + date +
                ", idRequest = " + idRequest +
                ", status = '" + status + '\'' +
                '}';
    }
}
