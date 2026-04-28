package es.uji.ei1027.proyecto.controlador;

import es.uji.ei1027.proyecto.controlador.Validator.OVIUserValidator;
import es.uji.ei1027.proyecto.dao.ContractDAO;
import es.uji.ei1027.proyecto.dao.OVIUserDAO;
import es.uji.ei1027.proyecto.dao.patiDAO;
import es.uji.ei1027.proyecto.modelo.Contract;
import es.uji.ei1027.proyecto.modelo.OVIUser;
import es.uji.ei1027.proyecto.modelo.PATI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/OVIUser")
public class OVIUserController {

    private OVIUserDAO oviUserDAO;

    @Autowired
    public void setOviUserDAO(OVIUserDAO oviUserDAO) {
        this.oviUserDAO = oviUserDAO;
    }

    @Autowired
    private patiDAO DAO;

    @Autowired
    private ContractDAO cDAO;

    //Listamos los PATI asociados a este OVIUser
    @GetMapping("/list")
    public String listPATIS(Model model, Principal p){

        String dniOVI = p.getName();
        List<PATI> lista = DAO.getPATIsByOVIUser(dniOVI);

        // Siempre pasamos la lista (puede estar vacía)
        model.addAttribute("patis", lista);

        if (!lista.isEmpty())
            return "OVIUser/list";

        else
            return "OVIUser/listError";
    }

    @RequestMapping(value="/Contrato/{DNICand}", method = RequestMethod.GET)
    public String mandarContrato(Model model, @PathVariable String DNICand){

        Contract contract = cDAO.getContratoByPATI(DNICand);

        model.addAttribute("contrato", contract);
        return "Contrato/info";
    }

    // AÑADIR (GET)
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addUser(Model model) {

        model.addAttribute("oviuser", new OVIUser());

        return "OVIUser/add";

    }

    // AÑADIR (POST)
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("oviuser") OVIUser user,
                                   BindingResult bindingResult) {
        OVIUserValidator validator = new OVIUserValidator();
        validator.validate(user, bindingResult);
        if (bindingResult.hasErrors())
            return "OVIUser/add";
        oviUserDAO.addOVIUser(user);
        return "redirect:/OVIUser/index";
    }

    // BORRAR
    @RequestMapping("/delete/{DNI}")
    public String deleteUser(@PathVariable String DNI) {
        oviUserDAO.deleteOVIUser(DNI);
        return "redirect:/OVIUser/index";
    }

    // EDITAR (GET)
    @RequestMapping(value = "/update/{DNI}", method = RequestMethod.GET)
    public String editUser(Model model, @PathVariable String DNI) {

        model.addAttribute("OVIuser", oviUserDAO.getOVIUser(DNI));

        return "OVIUser/index";
    }

    // EDITAR (POST)
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("oviuser") OVIUser user,
                                      BindingResult bindingResult) {

        OVIUserValidator validator = new OVIUserValidator();
        validator.validate(user, bindingResult);

        if (bindingResult.hasErrors())

            return "OVIUser/update";


        oviUserDAO.updateOVIUser(user);
        return "redirect:/OVIUser/index";
    }


}