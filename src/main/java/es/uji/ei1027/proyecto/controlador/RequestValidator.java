package es.uji.ei1027.proyecto.controlador;

import es.uji.ei1027.proyecto.modelo.Request;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Request.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Request r = (Request) target;

        // Validar requisito obligatorio
        if (r.getIdRequirement() == 0) {
            errors.rejectValue("idRequirement", "obligatorio", "Debes seleccionar un requisito");
        }

        // Validar fecha
        if (r.getDate() == null) {
            errors.rejectValue("date", "obligatorio", "La fecha no puede estar vacía");
        }

        // Validar que no haya manipulación indebida
        if (r.getStatus() != null && !r.getStatus().equals("Pendiente")) {
            errors.rejectValue("status", "invalido", "El estado no puede ser modificado manualmente");
        }
    }
}
