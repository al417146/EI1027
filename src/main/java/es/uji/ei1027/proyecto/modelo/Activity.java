package es.uji.ei1027.proyecto.modelo;

import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

public class Activity {
    int idActivity;
    String name;
    String place;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date actDate;
    String dniProf;
    String type; // "formacio" o "divulgacio"
    Integer maxParticipants;
    String description;

    public int getIdActivity() { return idActivity; }
    public void setIdActivity(int idActivity) { this.idActivity = idActivity; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPlace() { return place; }
    public void setPlace(String place) { this.place = place; }
    public Date getActDate() { return actDate; }
    public void setActDate(Date actDate) { this.actDate = actDate; }
    public String getDniProf() { return dniProf; }
    public void setDniProf(String dniProf) { this.dniProf = dniProf; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Integer getMaxParticipants() { return maxParticipants; }
    public void setMaxParticipants(Integer maxParticipants) { this.maxParticipants = maxParticipants; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
