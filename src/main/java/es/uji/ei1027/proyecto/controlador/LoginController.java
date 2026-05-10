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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private OVIUserDAO oviUserDAO;

    @Autowired
    private patiDAO patiDAO;

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

        session.setAttribute("user", authenticated);

        // Cargar la entidad completa según el rol
        String rol = authenticated.getRol();
        if ("STAFF".equals(rol)) {
            session.setAttribute("currentUserName", authenticated.getDni());
            return "redirect:/staff/index";
        }
        else if ("PAP".equals(rol)) {
            PATI pati = patiDAO.getPATI(authenticated.getDni());
            session.setAttribute("currentUser", pati);
            session.setAttribute("currentUserName", pati.getName());
            return "redirect:/PAP/index";
        }
        else {
            OVIUser oviUser = oviUserDAO.getOVIUser(authenticated.getDni());
            session.setAttribute("currentUser", oviUser);
            session.setAttribute("currentUserName", oviUser.getName());
            return "redirect:/OVIUser/OVIndex";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
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
            cancelUrl = "/OVIUser/OVIndex";
        }

        model.addAttribute("cancelUrl", cancelUrl);
        return "logoutConfirm";
    }
}