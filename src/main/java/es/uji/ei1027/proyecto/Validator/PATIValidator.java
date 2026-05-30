package es.uji.ei1027.proyecto.Validator;

import es.uji.ei1027.proyecto.modelo.PATI;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import java.util.regex.Pattern;

public class PATIValidator implements Validator {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[0-9]{9}$");

    @Override
    public boolean supports(Class<?> clazz) {
        return PATI.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PATI p = (PATI) target;

        if (p.getDNI() == null || p.getDNI().trim().isEmpty())
            errors.rejectValue("DNI", "required", "El DNI es obligatorio");

        if (p.getName() == null || p.getName().trim().isEmpty())
            errors.rejectValue("name", "required", "El nombre es obligatorio");

        if (p.getMail() == null || p.getMail().trim().isEmpty())
            errors.rejectValue("mail", "required", "El email es obligatorio");
        else if (!EMAIL_PATTERN.matcher(p.getMail()).matches())
            errors.rejectValue("mail", "invalid", "Formato de email inválido");

        if (p.getPhone() == null || p.getPhone().trim().isEmpty())
            errors.rejectValue("phone", "required", "El teléfono es obligatorio");
        else if (!PHONE_PATTERN.matcher(p.getPhone()).matches())
            errors.rejectValue("phone", "invalid", "El teléfono debe tener 9 dígitos");

        if (p.getPassword() == null || p.getPassword().trim().isEmpty())
            errors.rejectValue("password", "required", "La contraseña es obligatoria");
    }
}