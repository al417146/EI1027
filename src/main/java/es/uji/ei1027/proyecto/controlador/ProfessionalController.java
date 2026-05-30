package es.uji.ei1027.proyecto.controlador;

import es.uji.ei1027.proyecto.Validator.ProfessionalValidator;
import es.uji.ei1027.proyecto.dao.ProfessionalDAO;
import es.uji.ei1027.proyecto.modelo.Professional;
import es.uji.ei1027.proyecto.modelo.UserDetails;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/staff/formadores")
public class ProfessionalController {

    @Autowired
    private ProfessionalDAO professionalDAO;

    private boolean isStaff(HttpSession session) {
        UserDetails user = (UserDetails) session.getAttribute("user");
        return user != null && "STAFF".equals(user.getRol());
    }

    @GetMapping("/list")
    public String list(Model model, HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        model.addAttribute("formadores", professionalDAO.getProfessionals());
        return "staff/profesionales/list";
    }

    @GetMapping("/add")
    public String add(Model model, HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        model.addAttribute("professional", new Professional());
        return "staff/profesionales/add";
    }

    @PostMapping("/add")
    public String processAdd(@ModelAttribute Professional professional, BindingResult bindingResult, Model model, HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        ProfessionalValidator validator = new ProfessionalValidator();
        validator.validate(professional, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("professional", professional);
            return "staff/profesionales/add";
        }
        professionalDAO.addProfessional(professional);
        return "redirect:/staff/formadores/list";
    }

    @GetMapping("/edit/{dni}")
    public String edit(@PathVariable String dni, Model model, HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        model.addAttribute("professional", professionalDAO.getProfessional(dni));
        return "staff/profesionales/edit";
    }

    @PostMapping("/edit")
    public String processEdit(@ModelAttribute("professional") Professional professional,
                              BindingResult bindingResult,
                              Model model,
                              HttpSession session) {
        if (!isStaff(session)) {
            return "redirect:/login";
        }

        ProfessionalValidator validator = new ProfessionalValidator();
        validator.validate(professional, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("professional", professional);
            return "staff/profesionales/edit";
        }

        professionalDAO.updateProfessional(professional);
        return "redirect:/staff/formadores/list";
    }

    @GetMapping("/delete/{dni}")
    public String delete(@PathVariable String dni, HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        professionalDAO.deleteProfessional(dni);
        return "redirect:/staff/formadores/list";
    }
}
