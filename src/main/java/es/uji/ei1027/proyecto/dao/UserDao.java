package es.uji.ei1027.proyecto.dao;

import es.uji.ei1027.proyecto.modelo.UserDetails;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserDao {

    private final Map<String, UserDetails> knownUsers = new HashMap<>();

    public UserDao() {
        BasicPasswordEncryptor enc = new BasicPasswordEncryptor();

        // Staff hardcodeado en memoria
        UserDetails staff = new UserDetails();
        staff.setDni("admin");
        staff.setPassword(enc.encryptPassword("admin123"));
        staff.setRol("STAFF");
        knownUsers.put("admin", staff);
    }


    public UserDetails loadUserByUsername(String dni, String password) {
        UserDetails user = knownUsers.get(dni.trim());
        if (user == null) return null;

        BasicPasswordEncryptor enc = new BasicPasswordEncryptor();
        if (enc.checkPassword(password, user.getPassword())) {
            UserDetails safeUser = new UserDetails();
            safeUser.setDni(user.getDni());
            safeUser.setRol(user.getRol());
            return safeUser;
        }
        return null;
    }
}