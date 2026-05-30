package es.uji.ei1027.proyecto.modelo;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Professional {
    String DNI, name, phone, mail, genre, address, uniqueSpeciality, historial;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    Date birthDate;

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUniqueSpeciality() {
        return uniqueSpeciality;
    }

    public void setUniqueSpeciality(String uniqueSpeciality) {
        this.uniqueSpeciality = uniqueSpeciality;
    }

    public Date getDate() {
        return birthDate;
    }

    public void setDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getHistorial() { return historial; }

    public void setHistorial(String historial) { this.historial = historial; }

    @Override
    public String toString() {
        return "Professional {" +
                "DNI = '" + DNI + '\'' +
                ", name = '" + name + '\'' +
                ", phone = '" + phone + '\'' +
                ", mail = '" + mail + '\'' +
                ", genre = '" + genre + '\'' +
                ", address = '" + address + '\'' +
                ", uniqueSpeciality = '" + uniqueSpeciality + '\'' +
                ", birthDate = " + birthDate + '\'' +
                ", historial = " + historial +// Corregido para usar el atributo real
                '}';
    }
}
