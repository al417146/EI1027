package es.uji.ei1027.proyecto.dao;

import es.uji.ei1027.proyecto.modelo.UserDetails;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class FakeUserProvider implements UserDao{
    final Map<String, UserDetails> knownUsers = new HashMap<String, UserDetails>();


    /*public FakeUserProvider() {
        BasicPasswordEncryptor passwordEncryptor =
                new BasicPasswordEncryptor();
        UserDetails userAlice = new UserDetails();
        userAlice.setMail("alice");
        userAlice.setPassword(passwordEncryptor.encryptPassword("alice"));
        knownUsers.put("alice", userAlice);


        UserDetails userBob = new UserDetails();
        userBob.setUsername("bob");
        userBob.setPassword(passwordEncryptor.encryptPassword("bob"));
        knownUsers.put("bob", userBob);
    }*/


    @Override
    public UserDetails loadUserByUsername(String username, String password) {
        UserDetails user = knownUsers.get(username.trim());
        if (user == null)
            return null; // Usuari no trobat
        // Contrasenya
        BasicPasswordEncryptor passwordEncryptor =
                new BasicPasswordEncryptor();
        if (passwordEncryptor.checkPassword(password, user.getPassword())) {
            // Per seguretat el camp password no s’ha de tornar
            UserDetails safeUser = new UserDetails();
            safeUser.setMail(user.getMail());
            return safeUser;
        }
        else {
            return null; // bad login!
        }
    }


    @Override
    public Collection<UserDetails> listAllUsers() {
        return knownUsers.values();
    }
}

