package es.uji.ei1027.proyecto.modelo;

import java.util.Date;

public class Contract {
    String idContract;
    Date dateStart;
    Date dateEnd;

    public String getIdContract() {
        return idContract;
    }

    public void setIdContract(String idContract) {
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

    @Override
    public String toString() {
        return "Contract {" +
                " idContract = " + idContract +
                ", dateStart = " + dateStart +
                ", dateEnd = " + dateEnd +
                '}';
    }
}
