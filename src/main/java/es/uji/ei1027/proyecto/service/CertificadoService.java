package es.uji.ei1027.proyecto.service;

import es.uji.ei1027.proyecto.dao.ProfessionalDAO;
import es.uji.ei1027.proyecto.modelo.Activity;
import es.uji.ei1027.proyecto.modelo.OVIUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CertificadoService {

    @Autowired
    private ProfessionalDAO professionalDAO;

    public String generarCertificado(OVIUser user, Activity activity) {
        return "========================================\n" +
               "         CERTIFICADO DE ASISTENCIA      \n" +
               "========================================\n" +
               "La Oficina para la Vida Independiente   \n" +
               "certifica que:                          \n\n" +
               "  " + user.getName() + "\n" +
               "  DNI: " + user.getDNI() + "\n\n" +
               "ha asistido a la actividad:\n\n" +
               "  " + activity.getName() + "\n" +
               "  Lugar: " + activity.getPlace() + "\n" +
               "  Fecha: " + activity.getActDate() + "\n\n" +
               "Firmado digitalmente por la OVI.\n" +
               "========================================\n";
    }

    public String enviarCertificado(String email, String certificado) {
        return "EMAIL ENVIADO A: " + email + "\n\n" + certificado;
    }

    public void guardarEnHistorialFormador(Activity activity, String certificado) {
        if (activity.getDniProf() == null || activity.getDniProf().isEmpty()) return;
        String historialActual = professionalDAO.getHistorial(activity.getDniProf());
        String nuevoHistorial = (historialActual != null ? historialActual + "\n\n" : "") + certificado;
        professionalDAO.updateHistorial(activity.getDniProf(), nuevoHistorial);
    }
}
