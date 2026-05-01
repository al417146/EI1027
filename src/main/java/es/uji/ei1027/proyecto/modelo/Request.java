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
    int idRequirement;   // requisito asociado

    String DNICand;      // DNI del PAP/PATI

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

    public String getDNICand() {
        return DNICand;
    }
    public void setDNICand(String DNICand) {
        this.DNICand = DNICand;
    }
}