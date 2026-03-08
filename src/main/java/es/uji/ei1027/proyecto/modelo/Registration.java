package es.uji.ei1027.proyecto.modelo;

public class Registration {
    String DNIUser, DNIAssistant, code;
    boolean attendace;

    public String getDNIUser() {
        return DNIUser;
    }

    public void setDNIUser(String DNIUser) {
        this.DNIUser = DNIUser;
    }

    public String getDNIAssistant() {
        return DNIAssistant;
    }

    public void setDNIAssistant(String DNIAssistant) {
        this.DNIAssistant = DNIAssistant;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isAttendace() {
        return attendace;
    }

    public void setAttendace(boolean attendace) {
        this.attendace = attendace;
    }

    @Override
    public String toString() {
        return "Registration {" +
                " DNIUser = '" + DNIUser + '\'' +
                ", DNIAssistant = '" + DNIAssistant + '\'' +
                ", code = '" + code + '\'' +
                ", attendace = " + attendace +
                '}';
    }
}
