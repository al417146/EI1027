package es.uji.ei1027.proyecto.modelo;

public class Activity {
    int idAct;
    String name;
    String place;

    public int getIdAct() {
        return idAct;
    }

    public void setIdAct(int idAct) {
        this.idAct = idAct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "Activity {" +
                " idAct = " + idAct +
                ", name = '" + name + '\'' +
                ", place = '" + place + '\'' +
                '}';
    }
}
