package es.uji.ei1027.proyecto.controlador;

import es.uji.ei1027.proyecto.Validator.OVIUserValidator;
import es.uji.ei1027.proyecto.dao.OVIUserDAO;
import es.uji.ei1027.proyecto.dao.patiDAO;
import es.uji.ei1027.proyecto.modelo.OVIUser;
import es.uji.ei1027.proyecto.modelo.PATI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    @Autowired
    private OVIUserDAO oviUserDAO;

    @Autowired
    private patiDAO patiDAO;

    // 1. Página inicial para elegir tipo de registro
    @GetMapping("/register")
    public String chooseType() {
        return "register";   // → templates/register.html
    }

    // 2. Registro OVIUser (GET)
    @GetMapping("/register/oviuser")
    public String showOVIUserForm(Model model) {
        model.addAttribute("oviuser", new OVIUser());
        return "OVIUserRegister";   // → templates/OVIUserRegister.html
    }

    // 3. Registro OVIUser (POST)
    @PostMapping("/register/oviuser")
    public String processOVIUser(@ModelAttribute("oviuser") OVIUser user,
                                 BindingResult bindingResult) {

        new OVIUserValidator().validate(user, bindingResult);

        if (bindingResult.hasErrors())
            return "OVIUserRegister";

        oviUserDAO.addOVIUser(user);
        return "redirect:/login";
    }

    // 4. Registro PATI (GET)
    @GetMapping("/register/pati")
    public String showPATIForm(Model model) {
        model.addAttribute("pati", new PATI());
        return "PatiRegister";   // → templates/PatiRegister.html
    }

    // 5. Registro PATI (POST)
    @PostMapping("/register/pati")
    public String processPATI(@ModelAttribute("pati") PATI pati,
                              BindingResult bindingResult) {

        patiDAO.addPATI(pati);
        return "redirect:/login";
    }

    // 6. Login
    @GetMapping("/login")
    public String login() {
        return "login";   // → templates/login.html
    }
}


