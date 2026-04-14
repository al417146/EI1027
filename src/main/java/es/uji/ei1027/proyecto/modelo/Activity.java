package es.uji.ei1027.proyecto.modelo;

import java.util.Date;

public class Activity {
    int idAct;
    String name;
    String place;
    Date actDate;
    String DNIProf;

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

    public Date getActDate() { return actDate; }

    public void setActDate(Date actDate) { this.actDate = actDate; }

    public String getDNIProf() { return DNIProf; }

    public void setDNIProf(String DNIProf) { this.DNIProf = DNIProf; }

    @Override
    public String toString() {
        return "Activity {" +
                " idAct = " + idAct +
                ", name = '" + name + '\'' +
                ", place = '" + place + '\'' +
                ", actDate = '" + actDate + '\'' +
                ", DNIProf = '" + DNIProf + '\'' +
                '}';
    }
}
