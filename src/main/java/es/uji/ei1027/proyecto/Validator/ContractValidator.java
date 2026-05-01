package es.uji.ei1027.proyecto.Validator;

import es.uji.ei1027.proyecto.modelo.Contract;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ContractValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Contract.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Contract c = (Contract) target;
        // Validar que el ID del contrato no sea nulo o vacío
        if (c.getIdContract() <= 0) {
            errors.rejectValue("idContract", "required", "El ID del contrato es obligatorio");
        }

        // Validar fecha de inicio
        if (c.getDateStart() == null) {
            errors.rejectValue("dateStart", "required", "La fecha de inicio es obligatoria");
        }

        // Validar fecha de fin (no puede ser anterior a la de inicio)
        if (c.getDateEnd() != null && c.getDateStart() != null
                && c.getDateEnd().before(c.getDateStart())) {
                errors.rejectValue("dateEnd", "invalid", "La fecha de fin no puede ser anterior a la fecha de inicio");
        }

        // Validar DNI del candidato
        if (c.getDNICand() == null || c.getDNICand().trim().isEmpty()) {
            errors.rejectValue("DNICand", "required", "El DNI del profesional es obligatorio");
        }

        // Validar que el idRequest sea mayor que 0 (si se espera que exista)
        if (c.getIdRequest() <= 0) {
            errors.rejectValue("idRequest", "invalid", "El ID de la solicitud no es válido");
        }
    }
}
