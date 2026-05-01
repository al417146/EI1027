package es.uji.ei1027.proyecto.modelo;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Contract {
    int idContract;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    Date dateStart;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    Date dateEnd;

    int idRequest;
    String DNICand;


    public int getIdContract() {
        return idContract;
    }

    public void setIdContract(int idContract) {
        this.idContract = idContract;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public int getIdRequest() {return idRequest;}

    public void setIdRequest(int idRequest) {this.idRequest = idRequest;}

    public String getDNICand() { return DNICand;}

    public void setDNICand(String DNICand) {this.DNICand = DNICand;}

    @Override
    public String toString() {
        return "Contract {" +
                " idContract = " + idContract +
                ", dateStart = " + dateStart +
                ", dateEnd = " + dateEnd +
                ", idRequest = " + idRequest +
                ", DNICand = " + DNICand +
                '}';
    }
}
