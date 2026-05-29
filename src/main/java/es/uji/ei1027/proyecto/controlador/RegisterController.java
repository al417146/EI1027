package es.uji.ei1027.proyecto.controlador;

import es.uji.ei1027.proyecto.Validator.OVIUserValidator;
import es.uji.ei1027.proyecto.dao.OVIUserDAO;
import es.uji.ei1027.proyecto.dao.SpecialityDAO;
import es.uji.ei1027.proyecto.dao.UserDao;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class RegisterController {

    @Autowired
    private OVIUserDAO oviUserDAO;

    @Autowired
    private patiDAO patiDAO;

    @Autowired
    private UserDao userDao;

    @Autowired
    private SpecialityDAO specialityDAO;

    @GetMapping("/register")
    public String chooseType() {
        return "register";
    }

    @GetMapping("/register/oviuser")
    public String showOVIUserForm(Model model) {
        model.addAttribute("oviuser", new OVIUser());
        return "OVIUser/add";
    }

    @PostMapping("/register/oviuser")
    public String processOVIUser(@ModelAttribute("oviuser") OVIUser user,
                                 BindingResult bindingResult,
                                 @RequestParam("password") String password) {   // ← recibe la contraseña aparte
        OVIUserValidator validator = new OVIUserValidator();
        validator.validate(user, bindingResult);

        if (password == null || password.trim().isEmpty()) {
            bindingResult.rejectValue("password", "required", "La contraseña es obligatoria");
        }

        if (bindingResult.hasErrors())
            return "OVIUser/add";

        user.setStatus("Pendiente");
        oviUserDAO.addOVIUser(user);
        userDao.addUser(user.getDNI(), password, "OVIUSER");   // ← usa la contraseña recibida

        return "redirect:/login";
    }

    @GetMapping("/register/pati")
    public String showPATIForm(Model model) {
        model.addAttribute("pati", new PATI());
        model.addAttribute("specialities", specialityDAO.getSpecialities());
        return "PatiRegister";
    }

    /*@PostMapping("/register/pati")
    public String processPATI(@ModelAttribute("pati") PATI pati,
//<<<<<<< HEAD
                              BindingResult bindingResult) {
        pati.setStatus("Pendiente");
        patiDAO.addPATI(pati);
        userDao.addUser(pati.getDNI(), pati.getPassword(), "PAP");
//=======
                              BindingResult bindingResult,
                              @RequestParam("password") String password) {   // ← recibe la contraseña aparte
        if (password == null || password.trim().isEmpty()) {
            bindingResult.rejectValue("password", "required", "La contraseña es obligatoria");
        }

        if (bindingResult.hasErrors()) {
            return "PatiRegister";
        }

        pati.setStatus("Pendiente");
        patiDAO.addPATI(pati);
        userDao.addUser(pati.getDNI(), password, "PAP");   // ← usa la contraseña recibida

//>>>>>>> origin/main
        return "redirect:/login";
    }*/
    @PostMapping("/register/pati")
    public String processPATI(@ModelAttribute("pati") PATI pati,
                              BindingResult bindingResult,
                              @RequestParam(value = "selectedSpecialities", required = false) List<Integer> selectedSpecialities) {
        if (bindingResult.hasErrors()) return "PatiRegister";
        pati.setStatus("Pendiente");
        patiDAO.addPATI(pati);
        userDao.addUser(pati.getDNI(), pati.getPassword(), "PAP");
        if (selectedSpecialities != null) {
            for (Integer idSpec : selectedSpecialities) {
                patiDAO.addSpecialityToPATI(pati.getDNI(), idSpec);
            }
        }
        return "redirect:/login";
    }
}