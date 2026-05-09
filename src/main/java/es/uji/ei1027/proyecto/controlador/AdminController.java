package es.uji.ei1027.proyecto.controlador;

import es.uji.ei1027.proyecto.dao.*;
import es.uji.ei1027.proyecto.modelo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private RequestDAO requestDAO;
    private patiDAO patiDAO;
    private OVIUserDAO oviUserDAO;
    private RequirementsDAO requirementsDAO;

    @Autowired
    public void setDAOs(RequestDAO requestDAO, patiDAO patiDAO,
                        OVIUserDAO oviUserDAO, RequirementsDAO requirementsDAO) {
        this.requestDAO = requestDAO;
        this.patiDAO = patiDAO;
        this.oviUserDAO = oviUserDAO;
        this.requirementsDAO = requirementsDAO;
    }

    // 1. LISTAR PETICIONES PENDIENTES
    @GetMapping("/list")
    public String listRequests(Model model) {
        List<Request> requests = requestDAO.getPendingRequests();
        model.addAttribute("requests", requests);
        return "backoffice/list";
    }

    // 2. GENERAR PROPUESTA (MATCHING)
    @GetMapping("/match/{idRequest}")
    public String showMatching(Model model, @PathVariable int idRequest) {

        Request request = requestDAO.getRequest(idRequest);
        if (request == null){
            model.addAttribute("error", "Solicitud no encontrada");
            return "error";
        }

        OVIUser user = oviUserDAO.getOVIUser(request.getDNIUser());
        Requirements req = requirementsDAO.getRequirement(request.getIdRequirement());

        //Candidatos que coinciden con los requisitos
        List<PATI> candidates = patiDAO.findMatch(
                user.getAddress(),
                user.getGender(),
                req.getTopic()
        );

        //Asignamos las especialidades a cada candidato
        //para mostrarlo en la vista

        for (PATI p : candidates){
            p.setSpecialties(patiDAO.getSpecialtiesForPati(p.getDNI()));
        }


        model.addAttribute("request", request);
        model.addAttribute("user", user);
        model.addAttribute("candidates", candidates);

        return "backoffice/proposals";
    }

    // 3. APROBAR Y ASIGNAR PROFESIONAL
    @PostMapping("/approve")
    public String approveRequest(@RequestParam("idRequest") int idRequest,
                                 @RequestParam("DNICand") String DNICand) {

        Request request = requestDAO.getRequest(idRequest);

        if (request != null && "Pendiente".equals(request.getStatus())) {
            request.setStatus("Aceptado");
            request.setDNICand(DNICand);
            requestDAO.updateRequest(request);
        }
        return "redirect:/admin/list";
    }
}
