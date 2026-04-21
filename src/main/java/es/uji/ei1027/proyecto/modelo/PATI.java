package es.uji.ei1027.proyecto.modelo;

import java.util.Date; // Importación necesaria
import java.util.HashMap;

public class PATI {

    String DNI;
    String name;
    Date birthDate;
    String gender;
    String phone;
    String mail;
    String address;
    String status;
    HashMap<Integer, String> specialties;

    public HashMap<Integer, String> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(HashMap<Integer, String> specialties) {
        this.specialties = specialties;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "PATI{" +
                "DNI='" + DNI + '\'' +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                ", mail='" + mail + '\'' +
                ", address='" + address + '\'' +
                ", status='" + status + '\'' +
                ", specialties=" + specialties +
                '}';
    }
}