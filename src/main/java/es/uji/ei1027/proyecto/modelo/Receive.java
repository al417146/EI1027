package es.uji.ei1027.proyecto.modelo;

public class Receive {
    String idRequest;
    String DNIPati;


    public String getDNIPati() {
        return DNIPati;
    }

    public void setDNIPati(String DNIPati) {
        this.DNIPati = DNIPati;
    }

    public String getidRequest() {
        return idRequest;
    }

    public void setIdRequest(String idRequest) {
        this.idRequest = idRequest;
    }

    @Override
    public String toString() {
        return "Contract {" +
                " DNIPati = " + DNIPati +
                ", idSpeciality = " + idRequest +
                '}';
    }
}
