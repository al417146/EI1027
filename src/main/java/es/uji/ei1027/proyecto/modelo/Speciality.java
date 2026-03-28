package es.uji.ei1027.proyecto.modelo;

public class Speciality {
    String idSpeciality;
    String descrip;

    public String getIdSpeciality() {
        return idSpeciality;
    }

    public void setIdSpeciality(String idSpeciality) {
        this.idSpeciality = idSpeciality;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    @Override
    public String toString() {
        return "Speciality {" +
                "idSpeciality = " + idSpeciality +
                ", descrip = '" + descrip + '\'' +
                '}';
    }
}