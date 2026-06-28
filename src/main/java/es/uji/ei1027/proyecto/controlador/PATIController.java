package es.uji.ei1027.proyecto.controlador;

import es.uji.ei1027.proyecto.dao.*;
import es.uji.ei1027.proyecto.modelo.Contract;
import es.uji.ei1027.proyecto.modelo.PATI;
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

    @Autowired
    private patiDAO patiDAO;



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
        model.addAttribute("solicitudes", requestDAO.getSolicitudesForPAP(user.getDni()));
        return "PAP/solicitudesPendientes";
    }

    @PostMapping("/aceptarSolicitud")
    public String aceptarSolicitud(@RequestParam int idRequest, HttpSession session) {
        if (!isPAP(session)) return "redirect:/login";
        UserDetails user = (UserDetails) session.getAttribute("user");
        Request r = requestDAO.getRequest(idRequest);
        if (r == null) return "redirect:/PAP/solicitudesPendientes";

        Contract contract = new Contract();
        contract.setDateStart(new Date());
        contract.setIdRequest(idRequest);
        contract.setDNICand(user.getDni());
        contract.setStatus("Pendiente de firma OVI");
        cDAO.addContract(contract);

        requestDAO.updateRequestStatus(idRequest, "Contrato pendiente", 0);
        return "redirect:/PAP/solicitudesPendientes";
    }

    @GetMapping("/misContratos")
    public String misContratos(Model model, HttpSession session) {
        if (!isPAP(session)) return "redirect:/login";
        UserDetails user = (UserDetails) session.getAttribute("user");
        model.addAttribute("contratos", cDAO.getContractsByPATIWithName(user.getDni()));
        return "PAP/misContratos";
    }

    @PostMapping("/firmarContrato")
    public String firmarContrato(@RequestParam int idContract, HttpSession session) {
        if (!isPAP(session)) return "redirect:/login";
        UserDetails user = (UserDetails) session.getAttribute("user");
        Contract contract = cDAO.getContractById(idContract);

        PATI pati = patiDAO.getPATI(user.getDni());
        String nomPAP = pati != null ? pati.getName() : user.getDni();
        String pdf = contract.getPdf() != null ? contract.getPdf() : "";
        pdf += "========================================\n" +
                "Firmado también por el PAP/PATI: " + nomPAP + "\n" +
                "Contrato ACTIVO desde: " + new java.util.Date() + "\n" +
                "========================================\n";
        cDAO.firmarPAP(idContract, pdf);
        return "redirect:/PAP/misContratos";
    }
}
