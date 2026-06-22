package es.uji.ei1027.proyecto.controlador;

import es.uji.ei1027.proyecto.Validator.SessionUserValidator;
import es.uji.ei1027.proyecto.dao.OVIUserDAO;
import es.uji.ei1027.proyecto.dao.UserDao;
import es.uji.ei1027.proyecto.dao.patiDAO;
import es.uji.ei1027.proyecto.modelo.OVIUser;
import es.uji.ei1027.proyecto.modelo.PATI;
import es.uji.ei1027.proyecto.modelo.UserDetails;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private OVIUserDAO oviUserDAO;

    @Autowired
    private patiDAO patiDAO;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new UserDetails());
        return "login";
    }

    @PostMapping("/login")
    public String checkLogin(@ModelAttribute("user") UserDetails user,
                             BindingResult bindingResult,
                             HttpSession session) {

        SessionUserValidator validator = new SessionUserValidator();
        validator.validate(user, bindingResult);

        if (bindingResult.hasErrors())
            return "login";

        UserDetails authenticated =
                userDao.loadUserByUsername(user.getDni(), user.getPassword());

        if (authenticated == null) {
            bindingResult.rejectValue("password", "badpw", "DNI o contraseña incorrectos");
            return "login";
        }
        // Verificar que l'OVIUser ha estat acceptat pel staff
        if ("OVIUSER".equals(authenticated.getRol())) {
            OVIUser oviUser = oviUserDAO.getOVIUser(authenticated.getDni());
            if (oviUser != null && "Pendiente".equals(oviUser.getStatus())) {
                session.setAttribute("user", authenticated);
                return "redirect:/espera";
            } else if (oviUser != null && "Denegado".equals(oviUser.getStatus())) {
                bindingResult.rejectValue("password", "denied",
                        "Tu solicitud ha sido denegada. Contacta con la OVI.");
                return "login";
            }
        }

        if ("PAP".equals(authenticated.getRol())) {
            PATI pati = patiDAO.getPATI(authenticated.getDni());
            if (pati != null && "Pendiente".equals(pati.getStatus())) {
                session.setAttribute("user", authenticated);
                return "redirect:/espera";
            } else if (pati != null && "Denegado".equals(pati.getStatus())) {
                bindingResult.rejectValue("password", "denied",
                        "Tu solicitud ha sido denegada. Contacta con la OVI.");
                return "login";
            }
        }

        session.setAttribute("user", authenticated);

        // Redirige según el rol
        String rol = authenticated.getRol();
        if ("STAFF".equals(rol))
            return "redirect:/staff/index";
        else if ("PAP".equals(rol))
            return "redirect:/PAP/index";
        else
            return "redirect:/OVIUser/OVIIndex";
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/logoutConfirm")
    public String logoutConfirm(Model model, HttpSession session) {
        UserDetails user = (UserDetails) session.getAttribute("user");
        String cancelUrl;

        if (user == null) {
            cancelUrl = "/";
        } else if ("STAFF".equals(user.getRol())) {
            cancelUrl = "/staff/index";
        } else if ("PAP".equals(user.getRol())) {
            cancelUrl = "/PAP/index";
        } else {
            cancelUrl = "/OVIUser/OVIIndex";
        }

        model.addAttribute("cancelUrl", cancelUrl);
        return "logoutConfirm";
    }

    @GetMapping("/encryptAll")
    public String encryptAll() {
        userDao.encryptExistingPasswords();
        return "redirect:/login";
    }

    @GetMapping("/espera")
    public String espera(HttpSession session, Model model) {
        UserDetails user = (UserDetails) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        String status = "Pendiente";
        if ("OVIUSER".equals(user.getRol())) {
            OVIUser oviUser = oviUserDAO.getOVIUser(user.getDni());
            if (oviUser != null) status = oviUser.getStatus();
        } else if ("PAP".equals(user.getRol())) {
            PATI pati = patiDAO.getPATI(user.getDni());
            if (pati != null) status = pati.getStatus();
        }

        if ("Aceptado".equals(status)) {
            if ("OVIUSER".equals(user.getRol())) return "redirect:/OVIUser/OVIIndex";
            if ("PAP".equals(user.getRol())) return "redirect:/PAP/index";
        }
        if ("Denegado".equals(status)) {
            session.invalidate();
            return "redirect:/login?denegado";
        }

        model.addAttribute("rol", user.getRol());
        return "espera";
    }
}