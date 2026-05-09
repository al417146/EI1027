package es.uji.ei1027.proyecto.controlador;

import es.uji.ei1027.proyecto.Validator.RequestValidator;
import es.uji.ei1027.proyecto.dao.RequestDAO;
import es.uji.ei1027.proyecto.dao.RequirementsDAO;
import es.uji.ei1027.proyecto.modelo.Request;
import es.uji.ei1027.proyecto.modelo.Requirements;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/request")
public class RequestController {

    @Autowired
    private RequestDAO requestDAO;

    @Autowired
    private RequirementsDAO requirementsDAO;

    @Autowired
    private RequestValidator requestValidator;

    // LISTAR TODAS LAS SOLICITUDES (solo staff)
    @GetMapping("/list")
    public String listAll(Model model) {
        List<Request> lista = requestDAO.getRequests();
        model.addAttribute("requests", lista);
        return "Request/list";
    }

    // VER UNA SOLICITUD
    @GetMapping("/view/{id}")
    public String viewRequest(Model model, @PathVariable int id) {
        Request r = requestDAO.getRequest(id);
        model.addAttribute("request", r);
        return "Request/view";
    }

    // FORMULARIO PARA CREAR UNA SOLICITUD (genérica)
    @GetMapping("/add")
    public String addRequestForm(Model model) {
        model.addAttribute("request", new Request());
        model.addAttribute("requirements", requirementsDAO.getRequirements());
        return "Request/add";
    }

    // PROCESAR CREACIÓN DE SOLICITUD
    @PostMapping("/add")
    public String processAddRequest(@ModelAttribute("request") Request request,
                                    BindingResult bindingResult,
                                    Principal principal) {

        requestValidator.validate(request, bindingResult);

        if (bindingResult.hasErrors()) {
            return "Request/add";
        }

        request.setDNIUser(principal.getName());
        request.setDate(new Date());
        request.setStatus("Pendiente");
        request.setDNICand(null);
        request.setIdContract(0);
        request.setIdNeg(null);

        requestDAO.addRequest(request);

        return "redirect:/OVIUser/estadoSolicitud";
    }

    // BORRAR UNA SOLICITUD
    @GetMapping("/delete/{id}")
    public String deleteRequest(@PathVariable int id) {
        requestDAO.deleteRequest(id);
        return "redirect:/request/list";
    }
}
