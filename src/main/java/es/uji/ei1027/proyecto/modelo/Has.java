package es.uji.ei1027.proyecto.modelo;

public class Has {

    int DNIPati;
    String idSpeciality;


    public int getDNIPati() {
        return DNIPati;
    }

    public void setDNIPati(int DNIPati) {
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
        return "Has {" +
                " DNIPati = " + DNIPati +
                ", idSpeciality = " + idSpeciality +
                '}';

    }
}
