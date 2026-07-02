package es.uji.ei1027.proyecto.controlador;

import es.uji.ei1027.proyecto.dao.NotificacionDAO;
import es.uji.ei1027.proyecto.dao.OVIUserDAO;
import es.uji.ei1027.proyecto.dao.patiDAO;
import es.uji.ei1027.proyecto.modelo.OVIUser;
import es.uji.ei1027.proyecto.modelo.PATI;
import es.uji.ei1027.proyecto.modelo.UserDetails;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class NotificacionController {

    @Autowired
    private NotificacionDAO notificacionDAO;

    @Autowired
    private OVIUserDAO oviUserDAO;

    @Autowired
    private patiDAO patiDao;

    private boolean isStaff(HttpSession session) {
        UserDetails user = (UserDetails) session.getAttribute("user");
        return user != null && "STAFF".equals(user.getRol());
    }

    @GetMapping("/staff/notificaciones")
    public String gestionNotificaciones(Model model, HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        model.addAttribute("oviusers", oviUserDAO.getOVIUsers());
        model.addAttribute("patis", patiDao.getPATIs());
        model.addAttribute("enviadas", notificacionDAO.getNotificacionesEnviadas());
        return "staff/notificaciones";
    }

    @PostMapping("/staff/notificaciones/enviar")
    public String enviar(@RequestParam String destinatario,
                         @RequestParam String contenido,
                         HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        notificacionDAO.enviar(destinatario, contenido);
        return "redirect:/staff/notificaciones?enviado";
    }

    @GetMapping("/notificaciones")
    public String verNotificaciones(Model model, HttpSession session) {
        UserDetails user = (UserDetails) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        notificacionDAO.marcarTodasLeidas(user.getDni());
        model.addAttribute("notificaciones", notificacionDAO.getNotificaciones(user.getDni()));
        return "notificaciones";
    }
}
