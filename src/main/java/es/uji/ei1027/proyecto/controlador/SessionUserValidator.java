package es.uji.ei1027.proyecto.controlador;

import es.uji.ei1027.proyecto.modelo.UserDetails;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class SessionUserValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return UserDetails.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        UserDetails user = (UserDetails) obj;

        if (user.getDni() == null || user.getDni().trim().equals("")) {
            errors.rejectValue("mail", "obligatorio", "El correo es obligatorio");
        }

        if (user.getPassword() == null || user.getPassword().trim().equals("")) {
            errors.rejectValue("password", "obligatorio", "La contraseña es obligatoria");
        }
    }
}
