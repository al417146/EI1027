package es.uji.ei1027.proyecto.modelo;

public class Requirements{
    String requirement;
    int idRequirement;

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public int getIdRequirement() {
        return idRequirement;
    }

    public void setIdRequirement(int idRequirement) {
        this.idRequirement = idRequirement;
    }

    @Override
    public String toString() {
        return "Requirements {" +
                " requirement ='" + requirement + '\'' +
                ", idRequirement ='" + idRequirement + '\'' +
                '}';
    }
}
