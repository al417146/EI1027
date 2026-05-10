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
        return "register";
    }

    // 2. Registro OVIUser (GET)
    @GetMapping("/register/oviuser")
    public String showOVIUserForm(Model model) {
        model.addAttribute("oviuser", new OVIUser());
        return "OVIUser/add";
    }

    // 3. Registro OVIUser (POST)
    @PostMapping("/register/oviuser")
    public String processOVIUser(@ModelAttribute("oviuser") OVIUser user,
                                 BindingResult bindingResult) {
        new OVIUserValidator().validate(user, bindingResult);
        if (bindingResult.hasErrors())
            return "OVIUser/add";

        user.setStatus("Pendiente");
        oviUserDAO.addOVIUser(user);
        return "redirect:/login";
    }

    // 4. Registro PATI (GET)
    @GetMapping("/register/pati")
    public String showPATIForm(Model model) {
        model.addAttribute("pati", new PATI());
        return "PatiRegister";
    }

    // 5. Registro PATI (POST)
    @PostMapping("/register/pati")
    public String processPATI(@ModelAttribute("pati") PATI pati,
                              BindingResult bindingResult) {
        pati.setStatus("Pendiente");
        patiDAO.addPATI(pati);
        return "redirect:/login";
    }
}