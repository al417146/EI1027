package es.uji.ei1027.proyecto.modelo;

public class UserDetails {
    String dni;
    String password;
    String rol;

    public String getDni(){ return dni; }
    public void setDni(String dni){ this.dni = dni;}
    public String getPassword(){return password;}
    public void setPassword( String password){this.password = password;}
    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
}
