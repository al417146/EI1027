package es.uji.ei1027.proyecto.controlador;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(DuplicateKeyException.class)
    public ModelAndView handleDuplicateKey(DuplicateKeyException ex) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("errorName", "Clave duplicada");
        mav.addObject("message", "Ya existe un registro con esa clave. Verifique los datos.");
        return mav;
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ModelAndView handleEmptyResult(EmptyResultDataAccessException ex) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("errorName", "Datos no encontrados");
        mav.addObject("message", "No se encontró el elemento solicitado.");
        return mav;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleGeneric(Exception ex) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("errorName", "Error inesperado");
        mav.addObject("message", ex.getMessage());
        return mav;
    }
}