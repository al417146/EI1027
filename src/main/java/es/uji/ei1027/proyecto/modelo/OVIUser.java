package es.uji.ei1027.proyecto.modelo;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date; // Importación necesaria

public class OVIUser {

    String DNI;
    String name;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    Date birthDate;

    String gender;
    String phone;
    String mail;
    String address;
    String status;

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public Date getBirthDate() { // Actualizado
        return birthDate;
    }

    public void setBirthDate(Date birthDate) { // Actualizado
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "OVIUser {" +
                " DNI ='" + DNI + '\'' +
                ", name ='" + name + '\'' +
                ", birthDate =" + birthDate + // Actualizado
                ", gender ='" + gender + '\'' +
                ", phone =" + phone +
                ", mail ='" + mail + '\'' +
                ", address ='" + address + '\'' +
                ", status =" + status +
                '}';
    }
}