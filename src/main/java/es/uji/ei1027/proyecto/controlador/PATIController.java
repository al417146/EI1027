package es.uji.ei1027.proyecto.controlador;

import es.uji.ei1027.proyecto.dao.ActivityDAO;
import es.uji.ei1027.proyecto.dao.ContractDAO;
import es.uji.ei1027.proyecto.dao.RegistrationDAO;
import es.uji.ei1027.proyecto.dao.RequestDAO;
import es.uji.ei1027.proyecto.modelo.Contract;
import es.uji.ei1027.proyecto.modelo.Request;
import es.uji.ei1027.proyecto.modelo.UserDetails;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/PAP")
public class PATIController {

    @Autowired
    private ActivityDAO activityDAO;

    @Autowired
    private RegistrationDAO registrationDAO;

    @Autowired
    private RequestDAO requestDAO;

    @Autowired
    private ContractDAO contractDAO;

    @Autowired
    private ContractDAO cDAO;

    private boolean isPAP(HttpSession session) {
        UserDetails user = (UserDetails) session.getAttribute("user");
        return user != null && "PAP".equals(user.getRol());
    }

    @GetMapping("/index")
    public String index(HttpSession session) {
        if (!isPAP(session)) return "redirect:/login";
        return "PAP/index";
    }

    @GetMapping("/misActividades")
    public String misActividades(Model model, HttpSession session) {
        if (!isPAP(session)) return "redirect:/login";
        UserDetails user = (UserDetails) session.getAttribute("user");
        model.addAttribute("inscripciones", registrationDAO.getRegistrationsWithActivityName(user.getDni()));
        return "PAP/misActividades";
    }

    @GetMapping("/solicitudesPendientes")
    public String listSolicitudesPendientes(Model model, HttpSession session) {
        if (!isPAP(session)) return "redirect:/login";
        UserDetails user = (UserDetails) session.getAttribute("user");
        model.addAttribute("solicitudes", requestDAO.getRequestsWithUserName("Propuesta enviada"));
        return "PAP/solicitudesPendientes";
    }

    @PostMapping("/aceptarSolicitud")
    public String acceptRequest(@RequestParam int idRequest, HttpSession session) {
        if (!isPAP(session)) return "redirect:/login";
        UserDetails user = (UserDetails) session.getAttribute("user");
        Request r = requestDAO.getRequest(idRequest);
        if (r == null) return "redirect:/PAP/solicitudesPendientes?error=invalid";
        Contract contract = new Contract();
        contract.setDateStart(new Date());
        contract.setIdRequest(r.getIdRequest());
        contract.setDNICand(user.getDni());
        contractDAO.addContract(contract);
        requestDAO.updateRequestStatus(r.getIdRequest(), "Aceptada", 0);
        return "redirect:/PAP/solicitudesPendientes";
    }

    @GetMapping("/misContratos")
    public String misContratos(Model model, HttpSession session) {
        if (!isPAP(session)) return "redirect:/login";
        UserDetails user = (UserDetails) session.getAttribute("user");
        model.addAttribute("contratos", cDAO.getContractsByPATIWithName(user.getDni()));
        return "PAP/misContratos";
    }
}
