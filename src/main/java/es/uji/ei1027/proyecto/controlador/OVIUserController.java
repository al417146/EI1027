package es.uji.ei1027.proyecto.controlador;

import es.uji.ei1027.proyecto.Validator.OVIUserValidator;
import es.uji.ei1027.proyecto.Validator.RequestValidator;
import es.uji.ei1027.proyecto.dao.*;
import es.uji.ei1027.proyecto.modelo.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/OVIUser")
public class OVIUserController {

    @Autowired
    private OVIUserDAO oviUserDAO;

    @Autowired
    private patiDAO patiDAO;

    @Autowired
    private ContractDAO cDAO;

    @Autowired
    private RequestDAO rDAO;

    @Autowired
    private RequirementsDAO requirementsDAO;

    @Autowired
    private RequestValidator requestValidator;

    // Panel principal
    @GetMapping("/OVIIndex")
    public String index() {
        return "OVIUser/OVIIndex";
    }

    // Listamos los PATI asociados a este OVIUser
    @GetMapping("/list")
    public String listPATIS(Model model, HttpSession session) {
        UserDetails user = (UserDetails) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        List<PATI> lista = patiDAO.getPATIsByOVIUser(user.getDni());
        model.addAttribute("patis", lista);
        return lista.isEmpty() ? "OVIUser/listError" : "OVIUser/list";
    }

    // Mostramos la información del contrato de un profesional
    @GetMapping("/contrato/{DNICand}")
    public String mandarContrato(Model model, @PathVariable String DNICand) {
        Contract contract = cDAO.getContractByPATI(DNICand);
        model.addAttribute("contrato", contract);
        return "Contrato/info";  // La vista debe estar en templates/Contrato/info.html
    }

    // Mostramos los profesionales disponibles para solicitar
    @GetMapping("/available")
    public String listaPATISdisponibles(Model model) {
        List<PATI> disponibles = patiDAO.getAvailablePATIs();
        model.addAttribute("disponibles", disponibles);
        return "OVIUser/available";
    }

    // Crea una solicitud para un profesional elegido
    @PostMapping("/solicitarRequest")
    public String solicitarRequest(@RequestParam String dniPAP, HttpSession session) {
        UserDetails user = (UserDetails) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        Request r = new Request();
        r.setDNICand(dniPAP);
        r.setDate(new Date());
        r.setStatus("Pendiente");
        r.setDNIUser(user.getDni());
        r.setIdRequirement(new Random().nextInt(999999));
        rDAO.addRequest(r);
        return "redirect:/OVIUser/estadoSolicitud";
    }

    // Mostramos un formulario para añadir la fecha de fin a un contrato
    @GetMapping("/contrato/finalizar/{idContract}")
    public String showFinalizarContratoForm(@PathVariable int idContract, Model model, HttpSession session) {
        UserDetails user = (UserDetails) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        Contract contract = cDAO.getContractById(idContract);
        Request request = rDAO.getRequestById(contract.getIdRequest());
        if (request == null || !request.getDNIUser().equals(user.getDni())) {
            return "error/403";
        }
        model.addAttribute("contract", contract);
        model.addAttribute("fechaFin", null);
        return "OVIUser/finalizarContrato";
    }

    // Procesamos la fecha de fin
    @PostMapping("/contrato/finalizar")
    public String finalizarContrato(@RequestParam int idContract,
                                    @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date dateEnd,
                                    HttpSession session) {
        UserDetails user = (UserDetails) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        Contract contract = cDAO.getContractById(idContract);
        Request request = rDAO.getRequestById(contract.getIdRequest());
        if (request == null || !request.getDNIUser().equals(user.getDni())) {
            return "error/403";
        }
        cDAO.updateContractEndDate(idContract, dateEnd);
        return "redirect:/OVIUser/list";
    }

    // Ver estado de las solicitudes
    @GetMapping("/estadoSolicitud")
    public String verEstadoSolicitudes(Model model, HttpSession session) {
        UserDetails user = (UserDetails) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        List<Request> solicitudes = rDAO.getRequestsByUser(user.getDni());
        model.addAttribute("solicitudes", solicitudes);
        return "OVIUser/estadoSolicitud";
    }

    // Mostrar formulario de edición del perfil propio
    @GetMapping("/profile")
    public String showProfileForm(Model model, HttpSession session) {
        UserDetails user = (UserDetails) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        OVIUser oviuser = oviUserDAO.getOVIUser(user.getDni());
        model.addAttribute("oviuser", oviuser);
        return "OVIUser/profile";
    }

    // Elimina la cuenta
    @PostMapping("/deleteAccount")
    public String deleteOwnAccount(HttpSession session) {
        UserDetails user = (UserDetails) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        oviUserDAO.deleteOVIUser(user.getDni());
        session.invalidate();
        return "redirect:/login?deleted";
    }

    // Procesar actualización del perfil
    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute("oviuser") OVIUser oviuser,
                                BindingResult bindingResult,
                                HttpSession session) {
        UserDetails user = (UserDetails) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        OVIUserValidator validator = new OVIUserValidator();
        validator.validate(oviuser, bindingResult);
        if (bindingResult.hasErrors())
            return "OVIUser/profile";
        oviuser.setDNI(user.getDni());
        oviUserDAO.updateOVIUser(oviuser);
        return "redirect:/OVIUser/OVIIndex?updated";
    }

    // Mostrar formulario para solicitud genérica
    @GetMapping("/requestAssistance")
    public String showRequestAssistance(Model model) {
        model.addAttribute("request", new Request());
        return "OVIUser/requestAssistance";
    }

    // Procesar solicitud genérica
    @PostMapping("/requestAssistance")
    public String processRequestAssistance(@ModelAttribute("request") Request request,
                                           BindingResult bindingResult,
                                           HttpSession session) {
        UserDetails user = (UserDetails) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        requestValidator.validate(request, bindingResult);
        if (bindingResult.hasErrors())
            return "OVIUser/requestAssistance";
        request.setDNIUser(user.getDni());
        request.setDate(new Date());
        request.setStatus("Pendiente");
        request.setDNICand(null);
        request.setIdContract(0);
        request.setIdNeg(null);
        rDAO.addRequest(request);
        return "redirect:/OVIUser/estadoSolicitud";
    }

    // AÑADIR OVIUser (GET) — para el Staff
    @GetMapping("/add")
    public String addUser(Model model) {
        model.addAttribute("oviuser", new OVIUser());
        return "Staff/OVIadd";
    }

    // AÑADIR OVIUser (POST)
    @PostMapping("/add")
    public String processAddSubmit(@ModelAttribute("oviuser") OVIUser user,
                                   BindingResult bindingResult) {
        OVIUserValidator validator = new OVIUserValidator();
        validator.validate(user, bindingResult);
        if (bindingResult.hasErrors())
            return "OVIUser/add";
        user.setStatus("Pendiente");
        oviUserDAO.addOVIUser(user);
        return "redirect:/OVIUser/OVIIndex";  // Corregido
    }

    @GetMapping("/verCandidatos/{idRequest}")
    public String verCandidatos(@PathVariable int idRequest, Model model, HttpSession session) {
        UserDetails user = (UserDetails) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        Request request = rDAO.getRequest(idRequest);
        if (request == null || !request.getDNIUser().equals(user.getDni()))
            return "redirect:/OVIUser/estadoSolicitud";

        List<PATI> candidatos = new java.util.ArrayList<>();
        if (request.getDNICand() != null && !request.getDNICand().isEmpty()) {
            PATI pati = patiDAO.getPATI(request.getDNICand());
            if (pati != null) {
                pati.setSpecialties(patiDAO.getSpecialtiesForPati(pati.getDNI()));
                candidatos.add(pati);
            }
        }

        model.addAttribute("request", request);
        model.addAttribute("candidatos", candidatos);
        return "OVIUser/verCandidatos";
    }

    @PostMapping("/elegirCandidato")
    public String elegirCandidato(@RequestParam int idRequest,
                                  @RequestParam String dniCand,
                                  HttpSession session) {
        UserDetails user = (UserDetails) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        Request request = rDAO.getRequest(idRequest);
        if (request != null && request.getDNIUser().equals(user.getDni())) {
            request.setStatus("Aceptada");
            request.setDNICand(dniCand);
            rDAO.updateRequest(request);

            Contract contract = new Contract();
            contract.setDateStart(new Date());
            contract.setIdRequest(idRequest);
            contract.setDNICand(dniCand);
            cDAO.addContract(contract);
        }
        return "redirect:/OVIUser/estadoSolicitud";
    }
    @GetMapping("/misContratos")
    public String listMisContratos(Model model, HttpSession session,
                                   @RequestParam(value = "tipo", required = false, defaultValue = "activos") String tipo) {
        UserDetails user = (UserDetails) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        List<Contract> todosContratos = cDAO.getContractsByUser(user.getDni());
        Date hoy = new Date();

        List<Contract> filtrados = new ArrayList<>();
        for (Contract c : todosContratos) {
            boolean activo = (c.getDateEnd() == null || c.getDateEnd().after(hoy)) && c.getDateStart().before(hoy);
            boolean pasado = c.getDateEnd() != null && c.getDateEnd().before(hoy);
            boolean futuro = c.getDateStart().after(hoy);

            if ("activos".equals(tipo) && activo) filtrados.add(c);
            else if ("pasados".equals(tipo) && pasado) filtrados.add(c);
            else if ("futuros".equals(tipo) && futuro) filtrados.add(c);
            else if ("todos".equals(tipo)) filtrados.add(c);
            else if ("activos".equals(tipo) && !activo) {} // no añadir
            else if ("pasados".equals(tipo) && !pasado) {}
            else if ("futuros".equals(tipo) && !futuro) {}
            else if (!"activos".equals(tipo) && !"pasados".equals(tipo) && !"futuros".equals(tipo) && !"todos".equals(tipo)) {
                // por defecto, activos
                if (activo) filtrados.add(c);
            }
        }

        model.addAttribute("contratos", filtrados);
        model.addAttribute("tipoActual", tipo);
        return "OVIUser/misContratos";
    }
}