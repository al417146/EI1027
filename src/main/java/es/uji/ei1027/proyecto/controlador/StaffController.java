package es.uji.ei1027.proyecto.controlador;

import es.uji.ei1027.proyecto.dao.OVIUserDAO;
import es.uji.ei1027.proyecto.dao.RequestDAO;
import es.uji.ei1027.proyecto.dao.patiDAO;
import es.uji.ei1027.proyecto.modelo.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private OVIUserDAO oviUserDAO;

    @Autowired
    private RequestDAO requestDAO;

    @Autowired
    private patiDAO patiDAO;

    private boolean isStaff(HttpSession session) {
        UserDetails user = (UserDetails) session.getAttribute("user");
        return user != null && "STAFF".equals(user.getRol());
    }

    @GetMapping("/index")
    public String index(HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        return "staff/index";
    }

    @GetMapping("/oviusers")
    public String listarOVIUsers(Model model, HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        model.addAttribute("usuarios", oviUserDAO.getOVIUsersByStatus("Pendiente"));
        return "staff/oviusers";
    }

    @GetMapping("/solicitudes")
    public String listarSolicitudes(Model model, HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        model.addAttribute("solicitudes", requestDAO.getPendingRequests());
        return "staff/solicitudes";
    }

    @GetMapping("/patis")
    public String listarPATIs(Model model, HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        model.addAttribute("patis", patiDAO.getPATIs());
        return "staff/patis";
    }

    // Matching: mostrar candidatos para una solicitud
    @GetMapping("/solicitudes/match/{idRequest}")
    public String matchCandidatos(@PathVariable int idRequest, Model model, HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        Request request = requestDAO.getRequest(idRequest);
        if (request == null) return "redirect:/staff/solicitudes";

        OVIUser user = oviUserDAO.getOVIUser(request.getDNIUser());

        List<PATI> candidates = patiDAO.findMatch(
                request.getPreferredZone(),
                request.getPreferredGender(),
                request.getPreferredSpeciality()
        );
        for (PATI p : candidates) {
            p.setSpecialties(patiDAO.getSpecialtiesForPati(p.getDNI()));
        }

        model.addAttribute("request", request);
        model.addAttribute("user", user);
        model.addAttribute("candidates", candidates);
        return "staff/match";
    }

    // Asignar candidato y cambiar estado a "Propuesta enviada"
    @PostMapping("/solicitudes/aprobar")
    public String aprobarSolicitud(@RequestParam int idRequest,
                                   @RequestParam String dniCand,
                                   HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        Request request = requestDAO.getRequest(idRequest);
        if (request != null) {
            request.setStatus("Propuesta enviada");
            request.setDNICand(dniCand);
            requestDAO.updateRequest(request);
        }
        return "redirect:/staff/solicitudes";
    }

    @GetMapping("/solicitudes/denegar/{idRequest}")
    public String denegarSolicitud(@PathVariable int idRequest, HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        requestDAO.updateRequestStatus(idRequest, "Denegada", 0);
        return "redirect:/staff/solicitudes";
    }

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