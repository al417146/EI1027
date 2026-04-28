package es.uji.ei1027.proyecto.modelo;

import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import java.util.List;

public class Request {
    String DNIUser;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    Date date;

    int idRequest;
    String status;
    int idContract;
    String idNeg;        // id de la negociación
    Integer idRequirement;   // requisito asociado

    public String getDNIUser() { return DNIUser; }
    public void setDNIUser(String DNIUser) { this.DNIUser = DNIUser; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public int getIdRequest() { return idRequest; }
    public void setIdRequest(int idRequest) { this.idRequest = idRequest; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getIdContract() { return idContract; }
    public void setIdContract(int idContract) { this.idContract = idContract; }

    public String getIdNeg() { return idNeg; }
    public void setIdNeg(String idNeg) { this.idNeg = idNeg; }

    public int getIdRequirement() { return idRequirement; }
    public void setIdRequirement(int idRequirement) { this.idRequirement = idRequirement; }

    @Override
    public String toString() {
        return "Request {" +
                " DNIUser = '" + DNIUser + '\'' +
                ", date = " + date +
                ", idRequest = " + idRequest +
                ", status = '" + status + '\'' +
                ", idContract = '" + idContract + '\'' +
                ", idNeg = '" + idNeg + '\'' +
                ", idRequirement = " + idRequirement +
                '}';
    }
}