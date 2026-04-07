package es.uji.ei1027.proyecto.controlador.Validator;

import es.uji.ei1027.proyecto.modelo.OVIUser;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


public class OVIUserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return OVIUser.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        OVIUser user = (OVIUser) target;

        //Validamos el DNI, nombre y edad

        if (user.getDNI() == null || user.getDNI().isEmpty())
            errors.rejectValue("DNI","obligatorio", "El DNI es obligatorio");

        if (user.getName() == null || user.getName().trim().isEmpty()) {
            errors.rejectValue("name", "obligatorio", "El nombre es obligatorio");
        }

        if (user.getAge() <= 0) {
            errors.rejectValue("age", "incorrecto", "La edad debe ser mayor que 0");
        }
    }
}
