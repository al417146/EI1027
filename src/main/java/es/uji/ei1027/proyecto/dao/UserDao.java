package es.uji.ei1027.proyecto.dao;

import es.uji.ei1027.proyecto.modelo.UserDetails;

import java.util.Collection;

public interface UserDao {
    UserDetails loadUserByUsername(String username, String password);//Autenticar con nombre y contraseña
    Collection<UserDetails> listAllUsers();//listar usuarios
}
