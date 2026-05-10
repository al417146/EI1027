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

    /*
    ######################################
        PARTE PRINCIPAL USUARIO OVI
    ######################################
     */

    // Panel principal
    @RequestMapping(value = "/OVIndex", method = RequestMethod.GET)
    public String index() {
        return "OVIUser/OVIndex";
    }

    // Listamos los PATI asociados a este OVIUser
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listPATIS(Model model, HttpSession session) {
        UserDetails user = (UserDetails) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        List<PATI> lista = patiDAO.getPATIsByOVIUser(user.getDni());
        model.addAttribute("patis", lista);
        return lista.isEmpty() ? "OVIUser/listError" : "OVIUser/list";
    }

    // Mostramos la información del contrato de un profesional
    @RequestMapping(value = "/contrato/{DNICand}", method = RequestMethod.GET)
    public String mandarContrato(Model model, @PathVariable String DNICand) {
        Contract contract = cDAO.getContractByPATI(DNICand);
        model.addAttribute("contrato", contract);
        return "OVIUser/contrato";
    }

    // Mostramos los profesionales disponibles para solicitar
    @RequestMapping(value = "/available", method = RequestMethod.GET)
    public String listaPATISdisponibles(Model model) {
        List<PATI> disponibles = patiDAO.getAvailablePATIs();
        model.addAttribute("disponibles", disponibles);
        return "OVIUser/available";
    }

    // Crea una solicitud para un profesional elegido
    @RequestMapping(value = "/solicitarRequest", method = RequestMethod.POST)
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
    @RequestMapping(value = "/contrato/finalizar/{idContract}", method = RequestMethod.GET)
    public String showFinalizarContratoForm(@PathVariable String idContract,
                                            Model model, HttpSession session) {
        UserDetails user = (UserDetails) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        Contract contract = cDAO.getContractById(idContract);
        Request request = rDAO.getRequestById(contract.getIdRequest());
        if (request == null || !request.getDNIUser().equals(user.getDni()))
            return "error/403";

        model.addAttribute("contract", contract);
        return "OVIUser/finalizarContrato";
    }

    // Procesamos la fecha de fin
    @RequestMapping(value = "/contrato/finalizar", method = RequestMethod.POST)
    public String finalizarContrato(@RequestParam String idContract,
                                    @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date dateEnd,
                                    HttpSession session) {
        UserDetails user = (UserDetails) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        Contract contract = cDAO.getContractById(idContract);
        Request request = rDAO.getRequestById(contract.getIdRequest());
        if (request == null || !request.getDNIUser().equals(user.getDni()))
            return "error/403";

        cDAO.updateContractEndDate(idContract, dateEnd);
        return "redirect:/OVIUser/list";
    }

    // Ver estado de las solicitudes
    @RequestMapping(value = "/estadoSolicitud", method = RequestMethod.GET)
    public String verEstadoSolicitudes(Model model, HttpSession session) {
        UserDetails user = (UserDetails) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        List<Request> solicitudes = rDAO.getRequestsByUser(user.getDni());
        model.addAttribute("solicitudes", solicitudes);
        return "OVIUser/estadoSolicitud";
    }

    // Mostrar formulario de edición del perfil propio
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String showProfileForm(Model model, HttpSession session) {
        UserDetails user = (UserDetails) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        OVIUser oviUser = oviUserDAO.getOVIUser(user.getDni());
        model.addAttribute("oviuser", oviUser);
        return "OVIUser/profile";
    }

    // Procesar actualización del perfil
    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public String updateProfile(@ModelAttribute("oviuser") OVIUser oviUser,
                                BindingResult bindingResult,
                                HttpSession session) {
        UserDetails user = (UserDetails) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        OVIUserValidator validator = new OVIUserValidator();
        validator.validate(oviUser, bindingResult);
        if (bindingResult.hasErrors())
            return "OVIUser/profile";

        oviUser.setDNI(user.getDni());
        oviUserDAO.updateOVIUser(oviUser);
        return "redirect:/OVIUser/OVIndex";
    }

    // Elimina la cuenta
    @RequestMapping(value = "/deleteAccount", method = RequestMethod.POST)
    public String deleteOwnAccount(HttpSession session) {
        UserDetails user = (UserDetails) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        oviUserDAO.deleteOVIUser(user.getDni());
        session.invalidate();
        return "redirect:/login";
    }

    // Mostrar formulario para crear una solicitud genérica
    @RequestMapping(value = "/requestAssistance", method = RequestMethod.GET)
    public String showRequestAssistance(Model model) {
        List<Requirements> requirements = requirementsDAO.getRequirements();
        model.addAttribute("requirements", requirements);
        model.addAttribute("request", new Request());
        return "OVIUser/requestAssistance";
    }

    // Procesar solicitud genérica
    @RequestMapping(value = "/requestAssistance", method = RequestMethod.POST)
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

    /*
    ########################################################
        PARTE PARA EL MANEJO DE OVIUSERS PARA EL STAFF
    ########################################################
     */

    // AÑADIR (GET)
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addUser(Model model) {
        model.addAttribute("oviuser", new OVIUser());
        return "Staff/OVIadd";
    }

    // AÑADIR (POST)
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("oviuser") OVIUser user,
                                   BindingResult bindingResult) {
        OVIUserValidator validator = new OVIUserValidator();
        validator.validate(user, bindingResult);
        if (bindingResult.hasErrors())
            return "Staff/OVIadd";

        user.setStatus("Pendiente");
        oviUserDAO.addOVIUser(user);
        return "redirect:/staff/index";
    }
}