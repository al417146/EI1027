package es.uji.ei1027.proyecto.Validator;

import es.uji.ei1027.proyecto.modelo.Request;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Request.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Request r = (Request) target;

        // Zona preferida (obligatoria)
        if (r.getPreferredZone() == null || r.getPreferredZone().trim().isEmpty()) {
            errors.rejectValue("preferredZone", "required", "La zona preferida es obligatoria");
        }

        // Especialidad preferida (obligatoria)
        if (r.getPreferredSpeciality() == null || r.getPreferredSpeciality().trim().isEmpty()) {
            errors.rejectValue("preferredSpeciality", "required", "La especialidad requerida es obligatoria");
        }

        // El género preferido es opcional, no se valida la obligatoriedad.
    }
}