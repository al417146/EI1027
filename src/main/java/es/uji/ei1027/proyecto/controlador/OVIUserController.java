package es.uji.ei1027.proyecto.controlador;

import es.uji.ei1027.proyecto.Validator.OVIUserValidator;
import es.uji.ei1027.proyecto.dao.ContractDAO;
import es.uji.ei1027.proyecto.dao.OVIUserDAO;
import es.uji.ei1027.proyecto.dao.RequestDAO;
import es.uji.ei1027.proyecto.dao.patiDAO;
import es.uji.ei1027.proyecto.modelo.Contract;
import es.uji.ei1027.proyecto.modelo.OVIUser;
import es.uji.ei1027.proyecto.modelo.PATI;
import es.uji.ei1027.proyecto.modelo.Request;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/OVIUser")
public class OVIUserController {

    private OVIUserDAO oviUserDAO;

    @Autowired
    private patiDAO patiDAO;

    @Autowired
    private ContractDAO cDAO;

    @Autowired
    private RequestDAO rDAO;

    @Autowired
    public void setOviUserDAO(OVIUserDAO oviUserDAO) {
        this.oviUserDAO = oviUserDAO;
    }

    @Autowired
    public void setcDAO(ContractDAO cDAO) {
        this.cDAO = cDAO;
    }

    @Autowired
    public void setPatiDAO(patiDAO patiDAO) {
        this.patiDAO = patiDAO;
    }

    @Autowired
    public void setrDAO(RequestDAO rDAO) {
        this.rDAO = rDAO;
    }

    /*
    ######################################
        PARTE PRINCIPAL USUARIO OVI
    ######################################
     */
    //Panel principal
    @GetMapping("/index")
    public String index() {
        return "OVIUser/index";
    }

    //Listamos los PATI asociados a este OVIUser
    @GetMapping("/list")
    public String listPATIS(Model model, Principal p) {

        String dniOVI = p.getName();
        List<PATI> lista = patiDAO.getPATIsByOVIUser(dniOVI);

        // Siempre pasamos la lista (puede estar vacía)
        model.addAttribute("patis", lista);

        return lista.isEmpty() ? "OVIUser/listError" : "OVIUser/list";
    }

    //Mostramos la información del contrato de un profesional
    @GetMapping("/Contrato/{DNICand}")
    public String mandarContrato(Model model, @PathVariable String DNICand) {

        Contract contract = cDAO.getContractByPATI(DNICand);
        model.addAttribute("contrato", contract);
        return "Contrato/info";
    }

    //Mostramos los profesionales disponibles para solicitar
    @GetMapping("/available")
    public String listaPATISdisponibles(Model model) {
        List<PATI> disponibles = patiDAO.getAvailablePATIs();
        model.addAttribute("dispoibles", disponibles);
        return "OVIUser/available";
    }

    //Crea una solicitud para un profesional elegido
    @PostMapping("/solicitarRequest")
    public String solicitarRequest(@RequestParam String dniPAP, int idRequirement, Principal principal) {

        Request r = new Request();

        r.setDNICand(dniPAP);
        r.setDate(new Date());
        r.setStatus("Pendiente");
        r.setDNIUser(principal.getName());
        r.setIdRequirement(idRequirement);
        rDAO.addRequest(r);

        return "redirect:/OVIUser/estadoSolicitud";
    }

    // Mostramos un formulario para añadir la fecha de fin a un contrato
    @GetMapping("/contrato/finalizar/{idContract}")
    public String showFinalizarContratoForm(@PathVariable String idContract, Model model, Principal principal) {

        Contract contract = cDAO.getContractById(idContract);
        // Verificamos que el OVIUser autenticado es el dueño (a través de la request asociada)
        Request request = rDAO.getRequestById(contract.getIdRequest());
        if (request == null || !request.getDNIUser().equals(principal.getName())) {
            return "error/403";
        }
        model.addAttribute("contract", contract);
        model.addAttribute("fechaFin", null);
        return "OVIUser/finalizarContrato";
    }

    // Procesamos la fecha de fin
    @PostMapping("/contrato/finalizar")
    public String finalizarContrato(@RequestParam String idContract,
                                    @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date dateEnd,
                                    Principal principal) {
        Contract contract = cDAO.getContractById(idContract);
        Request request = rDAO.getRequestById(contract.getIdRequest());
        if (request == null || !request.getDNIUser().equals(principal.getName())) {
            return "error/403";
        }
        cDAO.updateContractEndDate(idContract, dateEnd);
        return "redirect:/OVIUser/list";
    }

    //Ver estado de las solicitudes
    @GetMapping("/estadoSolicitud")
    public String verEstadoSolicitudes(Model model, Principal principal) {

        List<Request> solicitudes = rDAO.getRequestsByUser(principal.getName());
        model.addAttribute("solicitudes", solicitudes);
        return "OVIUser/estadoSolicitud";
    }


    // Mostrar formulario de edición del perfil propio
    @GetMapping("/profile")
    public String showProfileForm(Model model, Principal principal) {
        String dni = principal.getName();
        OVIUser user = oviUserDAO.getOVIUser(dni);
        model.addAttribute("oviuser", user);
        return "OVIUser/profile";
    }

    // Elimina la cuenta
    @PostMapping("/deleteAccount")
    public String deleteOwnAccount(HttpSession session, Principal principal) {
        String dni = principal.getName();
        oviUserDAO.deleteOVIUser(dni);
        session.invalidate();  // Cierra la sesión
        return "redirect:/iniciarSesion?deleted";
    }

    // Procesar actualización del perfil
    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute("oviuser") OVIUser user,
                                BindingResult bindingResult,
                                Principal principal) {
        // Validar
        OVIUserValidator validator = new OVIUserValidator();
        validator.validate(user, bindingResult);

        if (bindingResult.hasErrors())
            return "OVIUser/profile";


        // Asegurar que el DNI no se modifica (o se fuerza el mismo)
        user.setDNI(principal.getName());
        oviUserDAO.updateOVIUser(user);
        return "redirect:/OVIUser/index?updated";
    }

    // AÑADIR (POST)
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("oviuser") OVIUser user,
                                   BindingResult bindingResult) {

        OVIUserValidator validator = new OVIUserValidator();
        validator.validate(user, bindingResult);
        if (bindingResult.hasErrors())
            return "OVIUser/add";

        user.setStatus("Pendiente");   // Estado inicial pendiente de evaluación por el staff
        oviUserDAO.addOVIUser(user);
        return "redirect:/OVIUser/index";
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
}




