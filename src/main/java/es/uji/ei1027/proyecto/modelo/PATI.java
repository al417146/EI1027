package es.uji.ei1027.proyecto.modelo;


//Se ha puesto solamente PATI, puesto que no dejaba poner PAP/PATI

public class PATI {

    String DNI;
    String name;
    int age;
    String gender;
    String phone;
    String mail;
    String address;
    String status;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public void setIdSpeciality(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PAP/PATI {" +
                " DNI = '" + DNI + '\'' +
                ", name = '" + name + '\'' +
                ", age = " + age +
                ", gender = '" + gender + '\'' +
                ", phone = " + phone +
                ", mail = '" + mail + '\'' +
                ", address = '" + address + '\'' +
                ", status = " + status +
                '}';
    }
}
