package es.uji.ei1027.proyecto.modelo;

public class Registration {
    int idRegist;
    int idActivity;
    String dniUser;
    boolean attended;

    public int getIdRegist() { return idRegist; }
    public void setIdRegist(int idRegist) { this.idRegist = idRegist; }
    public int getIdActivity() { return idActivity; }
    public void setIdActivity(int idActivity) { this.idActivity = idActivity; }
    public String getDniUser() { return dniUser; }
    public void setDniUser(String dniUser) { this.dniUser = dniUser; }
    public boolean isAttended() { return attended; }
    public void setAttended(boolean attended) { this.attended = attended; }
}
