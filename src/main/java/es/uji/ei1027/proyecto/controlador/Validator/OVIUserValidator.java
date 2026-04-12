package es.uji.ei1027.proyecto.controlador.Validator;

import es.uji.ei1027.proyecto.dao.patiDAO;
import es.uji.ei1027.proyecto.modelo.OVIUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


public class OVIUserValidator implements Validator {

    private patiDAO PATIDao;

    @Autowired
    public void setPatiDAO(patiDAO PATIDao) {
        this.PATIDao = PATIDao;
    }

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
    public void validateHasPATIs(String dni, Errors errors) {
        if (dni == null || dni.trim().isEmpty()) {
            errors.reject("dni_vacio", "No se pueden ver los PATI asociados debido a que hubo un error al leer el DNI");
            return;
        }

        // Llamamos al método del DAO que nos diga cuántos PATIs tiene este OVIUser
        int cantidad = patiDAO.countPATIsByOVIUser(dni);  // ver paso 2
        if (cantidad == 0) {
            errors.reject("sin_patis", "No tienes ningún PATI asociado ¡Prueba con uno a ver como sale!");
        }
    }
}
