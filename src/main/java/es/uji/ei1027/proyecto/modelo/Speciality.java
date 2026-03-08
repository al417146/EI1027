package es.uji.ei1027.proyecto.modelo;

public class Speciality {
    int idSpeciality;
    String speciality;

    public int getIdSpeciality() {
        return idSpeciality;
    }

    public void setIdSpeciality(int idSpeciality) {
        this.idSpeciality = idSpeciality;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    @Override
    public String toString() {
        return "Speciality {" +
                "idSpeciality = " + idSpeciality +
                ", speciality = '" + speciality + '\'' +
                '}';
    }
}
