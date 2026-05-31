package es.uji.ei1027.proyecto.controlador;

import es.uji.ei1027.proyecto.dao.*;
import es.uji.ei1027.proyecto.modelo.OVIUser;
import es.uji.ei1027.proyecto.modelo.PATI;
import es.uji.ei1027.proyecto.modelo.Request;
import es.uji.ei1027.proyecto.modelo.UserDetails;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

    @Autowired
    private RequestCandidatesDAO requestCandidatesDAO;

    @Autowired
    private ContractDAO cDAO;

    private boolean isStaff(HttpSession session) {
        UserDetails user = (UserDetails) session.getAttribute("user");
        return user != null && "STAFF".equals(user.getRol());
    }

    @GetMapping("/index")
    public String index(HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        return "staff/index";
    }

    // Listar todos los OVIUsers
    @GetMapping("/oviusers")
    public String listarOVIUsers(Model model, HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        model.addAttribute("usuarios", oviUserDAO.getOVIUsers());
        return "staff/oviusers";
    }

    // Aceptar OVIUser
    @GetMapping("/oviusers/aceptar/{dni}")
    public String aceptarOVIUser(@PathVariable String dni, HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        OVIUser u = oviUserDAO.getOVIUser(dni);
        u.setStatus("Aceptado");
        oviUserDAO.updateOVIUser(u);
        return "redirect:/staff/oviusers";
    }

    // Denegar OVIUser
    @GetMapping("/oviusers/denegar/{dni}")
    public String denegarOVIUser(@PathVariable String dni, HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        OVIUser u = oviUserDAO.getOVIUser(dni);
        u.setStatus("Denegado");
        oviUserDAO.updateOVIUser(u);
        return "redirect:/staff/oviusers";
    }

    // Listar solicitudes pendientes (todas las que tienen estado "Pendiente")
    @GetMapping("/solicitudes")
    public String listarSolicitudes(Model model, HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        model.addAttribute("solicitudes", requestDAO.getRequestsWithUserName("Pendiente"));
        return "staff/solicitudes";
    }

    // Aceptar solicitud
    @GetMapping("/solicitudes/aceptar/{idRequest}")
    public String aceptarSolicitud(@PathVariable int idRequest, HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        requestDAO.updateRequestStatus(idRequest, "Aceptada", 0);
        return "redirect:/staff/solicitudes";
    }

    // Denegar solicitud
    @GetMapping("/solicitudes/denegar/{idRequest}")
    public String denegarSolicitud(@PathVariable int idRequest, HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        requestDAO.updateRequestStatus(idRequest, "Denegada", 0);
        return "redirect:/staff/solicitudes";
    }

    // Listar todos los profesionales (PATI)
    @GetMapping("/patis")
    public String listarPATIs(Model model, HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        model.addAttribute("patis", patiDAO.getPATIs());
        return "staff/patis";
    }

    // Aceptar PATI
    @GetMapping("/patis/aceptar/{dni}")
    public String aceptarPATI(@PathVariable String dni, HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        PATI p = patiDAO.getPATI(dni);
        p.setStatus("Aceptado");
        patiDAO.updatePATI(p);
        return "redirect:/staff/patis";
    }

    // Denegar PATI
    @GetMapping("/patis/denegar/{dni}")
    public String denegarPATI(@PathVariable String dni, HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        PATI p = patiDAO.getPATI(dni);
        p.setStatus("Denegado");
        patiDAO.updatePATI(p);
        return "redirect:/staff/patis";
    }

    // Mostrar formulario para seleccionar candidatos para una solicitud
    @GetMapping("/match/{idRequest}")
    public String showMatchForm(@PathVariable int idRequest, Model model, HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";

        Request request = requestDAO.getRequest(idRequest);
        if (request == null) {
            model.addAttribute("error", "Solicitud no encontrada");
            return "error";
        }

        OVIUser user = oviUserDAO.getOVIUser(request.getDNIUser());
        List<PATI> candidates = patiDAO.findMatch(
                request.getPreferredZone(),
                request.getPreferredGender(),
                request.getPreferredSpeciality()
        );

        for (PATI p : candidates)
            p.setSpecialties(patiDAO.getSpecialtiesForPati(p.getDNI()));


        model.addAttribute("request", request);
        model.addAttribute("user", user);
        model.addAttribute("candidates", candidates);
        return "staff/match";
    }

    // Procesar la selección de candidatos y enviar propuesta
    @PostMapping("/solicitudes/proposta")
    public String enviarProposta(@RequestParam int idRequest,
                                 @RequestParam(required = false) List<String> dniCands,
                                 HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        if (dniCands == null || dniCands.isEmpty())
            return "redirect:/staff/solicitudes/match/" + idRequest;

        requestCandidatesDAO.deleteCandidates(idRequest);
        for (String dni : dniCands) {
            requestCandidatesDAO.addCandidate(idRequest, dni);
        }

        Request request = requestDAO.getRequest(idRequest);
        if (request != null) {
            request.setStatus("Propuesta enviada");
            request.setDNICand(null);
            requestDAO.updateRequest(request);
        }
        return "redirect:/staff/solicitudes";
    }

    /*@GetMapping("/match/{idRequest}")
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
    }*/
}