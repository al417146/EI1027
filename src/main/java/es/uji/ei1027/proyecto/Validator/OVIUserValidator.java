package es.uji.ei1027.proyecto.Validator;

import es.uji.ei1027.proyecto.dao.patiDAO;
import es.uji.ei1027.proyecto.modelo.OVIUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Date;
import java.util.regex.Pattern;


public class OVIUserValidator implements Validator {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^[0-9]{9}$");


    @Override
    public boolean supports(Class<?> clazz) {
        return OVIUser.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {


        OVIUser user = (OVIUser) target;


        // ---- DNI ----
        if (user.getDNI() == null || user.getDNI().isEmpty())
            errors.rejectValue("DNI","obligatorio", "El DNI es obligatorio");


        // ---- NOMBRE ----
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            errors.rejectValue("name", "obligatorio", "El nombre es obligatorio");
        }

        // ---- FECHA DE NACIMIENTO (edad > 0) ----
        if (user.getBirthDate() == null) {
            errors.rejectValue("birthDate", "obligatorio", "La fecha de nacimiento es obligatoria");
        }else {

            Date birthday = user.getBirthDate();
            Date now = new Date();

            //Si ha puesto una fecha futura
            if (birthday.after(now))
                errors.rejectValue("birthDate", "incorrecto", "La fecha de nacimiento no puede ser futura");

            else {
                //Calculamos la edad
                long diff = now.getTime() - birthday.getTime();
                long edadEnMil = 1000L * 60 * 60 * 24 * 365; //Milisegundos por año
                int age = (int) (diff / edadEnMil);

                if (age <= 0){
                    errors.rejectValue("birthDate", "incorrecto", "La edad debe de ser mayor a 0");
                }
            }
        }

        // ---- EMAIL (obligatorio y formato) ----
        String email = user.getMail();
        if (email == null || email.trim().isEmpty())
            errors.rejectValue("mail", "obligatorio", "El email es obligatorio");
        else if (!EMAIL_PATTERN.matcher(email).matches())
            errors.rejectValue("mail", "formato", "Formato de email inválido (debe contener @ y dominio)");


        // ---- TELÉFONO (obligatorio y formato 9 dígitos) ----
        String phone = user.getPhone();
        if (phone == null || phone.trim().isEmpty())
            errors.rejectValue("phone", "obligatorio", "El teléfono es obligatorio");
        else if (!PHONE_PATTERN.matcher(phone).matches()) {
            errors.rejectValue("phone", "formato", "El teléfono debe de tener 9 dígitos");
        }


    }



}
