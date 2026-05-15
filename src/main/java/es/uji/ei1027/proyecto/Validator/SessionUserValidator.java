package es.uji.ei1027.proyecto.Validator;

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

        if (user.getDni() == null || user.getDni().trim().isEmpty()) {
            errors.rejectValue("dni", "obligatorio", "El DNI es obligatorio");
        }

        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            errors.rejectValue("password", "obligatorio", "La contraseña es obligatoria");
        }
    }
}