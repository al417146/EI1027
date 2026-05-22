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
        if (c.getDateStart() == null)
            errors.rejectValue("dateStart", "required", "La fecha de inicio es obligatoria");
        if (c.getDNICand() == null || c.getDNICand().trim().isEmpty())
            errors.rejectValue("DNICand", "required", "El profesional es obligatorio");
    }
}
