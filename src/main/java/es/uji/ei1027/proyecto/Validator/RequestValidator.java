package es.uji.ei1027.proyecto.Validator;

import es.uji.ei1027.proyecto.modelo.Request;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class RequestValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Request.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Request r = (Request) target;

        // Validar DNI del usuario OVI
        if (r.getDNIUser() == null || r.getDNIUser().trim().isEmpty()) {
            errors.rejectValue("DNIUser", "required", "El DNI del usuario OVI es obligatorio");
        }

        // Validar fecha de solicitud
        if (r.getDate() == null) {
            errors.rejectValue("date", "required", "La fecha de la solicitud es obligatoria");
        }

        // Validar estado
        if (r.getStatus() == null || r.getStatus().trim().isEmpty()) {
            errors.rejectValue("status", "required", "El estado de la solicitud es obligatorio");
        }

        // Validar idRequirement
        if (r.getIdRequirement() <= 0) {
            errors.rejectValue("idRequirement", "invalid", "El id del requisito debe ser mayor que 0");
        }

        // Validar DNI del candidato (si se ha elegido un profesional)
        // No es obligatorio en el momento de creación, pero si se asigna debe tener formato válido
        if (r.getDNICand() != null && r.getDNICand().trim().isEmpty()) {
            errors.rejectValue("DNICand", "invalid", "El DNI del candidato no puede estar vacío");
        }
    }
}
