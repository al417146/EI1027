package es.uji.ei1027.proyecto.modelo;

public class Professional {
    String DNI, name, phone, mail, genre, address, uniqueSpeciality;
    int age;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

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
                ", age = " + age +
                '}';
    }
}
