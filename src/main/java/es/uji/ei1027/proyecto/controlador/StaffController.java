package es.uji.ei1027.proyecto.controlador;

import es.uji.ei1027.proyecto.dao.OVIUserDAO;
import es.uji.ei1027.proyecto.dao.RequestDAO;
import es.uji.ei1027.proyecto.dao.patiDAO;
import es.uji.ei1027.proyecto.modelo.UserDetails;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import es.uji.ei1027.proyecto.modelo.OVIUser;
import es.uji.ei1027.proyecto.modelo.PATI;

@Controller
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private OVIUserDAO oviUserDAO;

    @Autowired
    private RequestDAO requestDAO;

    @Autowired
    private patiDAO patiDAO;

    // Comprobación de sesión staff reutilizable
    private boolean isStaff(HttpSession session) {
        UserDetails user = (UserDetails) session.getAttribute("user");
        return user != null && "STAFF".equals(user.getRol());
    }

    @GetMapping("/index")
    public String index(HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        return "Staff/index";
    }

    @GetMapping("/oviusers")
    public String listarOVIUsers(Model model, HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        model.addAttribute("usuarios", oviUserDAO.getOVIUsers());
        return "Staff/oviusers";
    }

    @GetMapping("/solicitudes")
    public String listarSolicitudes(Model model, HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        model.addAttribute("solicitudes", requestDAO.getPendingRequests());
        return "Staff/solicitudes";
    }

    @GetMapping("/patis")
    public String listarPATIs(Model model, HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        model.addAttribute("patis", patiDAO.getPATIs());
        return "Staff/patis";
    }

    @GetMapping("/solicitudes/aceptar/{idRequest}")
    public String aceptarSolicitud(@PathVariable int idRequest, HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        requestDAO.updateRequestStatus(idRequest, "Aceptada", 0);
        return "redirect:/staff/solicitudes";
    }

    @GetMapping("/solicitudes/denegar/{idRequest}")
    public String denegarSolicitud(@PathVariable int idRequest, HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        requestDAO.updateRequestStatus(idRequest, "Denegada", 0);
        return "redirect:/staff/solicitudes";
    }

    // OVIUser aceptar/denegar
    @GetMapping("/oviusers/aceptar/{dni}")
    public String aceptarOVIUser(@PathVariable String dni, HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        OVIUser u = oviUserDAO.getOVIUser(dni);
        u.setStatus("Aceptado");
        oviUserDAO.updateOVIUser(u);
        return "redirect:/staff/oviusers";
    }

    @GetMapping("/oviusers/denegar/{dni}")
    public String denegarOVIUser(@PathVariable String dni, HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        OVIUser u = oviUserDAO.getOVIUser(dni);
        u.setStatus("Denegado");
        oviUserDAO.updateOVIUser(u);
        return "redirect:/staff/oviusers";
    }

    // PATI aceptar/denegar
    @GetMapping("/patis/aceptar/{dni}")
    public String aceptarPATI(@PathVariable String dni, HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        PATI p = patiDAO.getPATI(dni);
        p.setStatus("Aceptado");
        patiDAO.updatePATI(p);
        return "redirect:/staff/patis";
    }

    @GetMapping("/patis/denegar/{dni}")
    public String denegarPATI(@PathVariable String dni, HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        PATI p = patiDAO.getPATI(dni);
        p.setStatus("Denegado");
        patiDAO.updatePATI(p);
        return "redirect:/staff/patis";
    }
}