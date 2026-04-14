package es.uji.ei1027.proyecto.modelo;

import java.util.Date;

public class Has {

    String DNIPati;
    String idSpeciality;


    public String getDNIPati() {
        return DNIPati;
    }

    public void setDNIPati(String DNIPati) {
        this.DNIPati = DNIPati;
    }

    public String getidSpeciality() {
        return idSpeciality;
    }

    public void setIdSpeciality(String idSpeciality) {
        this.idSpeciality = idSpeciality;
    }

    @Override
    public String toString() {
        return "Contract {" +
                " DNIPati = " + DNIPati +
                ", idSpeciality = " + idSpeciality +
                '}';

    }
}
