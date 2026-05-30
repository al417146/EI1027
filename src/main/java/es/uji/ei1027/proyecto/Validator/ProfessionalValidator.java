package es.uji.ei1027.proyecto.Validator;

import es.uji.ei1027.proyecto.modelo.Professional;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import java.util.regex.Pattern;

public class ProfessionalValidator implements Validator {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    @Override
    public boolean supports(Class<?> clazz) {
        return Professional.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Professional p = (Professional) target;
        if (p.getDNI() == null || p.getDNI().trim().isEmpty())
            errors.rejectValue("DNI", "required", "El DNI es obligatorio");
        if (p.getName() == null || p.getName().trim().isEmpty())
            errors.rejectValue("name", "required", "El nombre es obligatorio");
        if (p.getMail() != null && !p.getMail().trim().isEmpty() && !EMAIL_PATTERN.matcher(p.getMail()).matches())
            errors.rejectValue("mail", "invalid", "Formato de email inválido");
    }
}