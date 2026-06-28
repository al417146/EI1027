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

import java.util.List;

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
        model.addAttribute("especialidades", List.of(
                "Comunicación aumentativa",
                "Divulgación social",
                "Fisioterapia",
                "Formación laboral",
                "Habilidades sociales",
                "Movilidad y autonomía",
                "Psicología",
                "Tecnología asistiva",
                "Trabajo social"
        ));
        return "staff/profesionales/add";
    }

    @PostMapping("/add")
    public String processAdd(@ModelAttribute Professional professional,
                             @RequestParam(value = "selectedEspecialidades", required = false) List<String> especialidades,
                             HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        if (especialidades != null) {
            professional.setUniqueSpeciality(String.join(", ", especialidades));
        }
        professionalDAO.addProfessional(professional);
        return "redirect:/staff/formadores/list";
    }

    @GetMapping("/edit/{dni}")
    public String edit(@PathVariable String dni, Model model, HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        model.addAttribute("professional", professionalDAO.getProfessional(dni));
        model.addAttribute("especialidades", List.of(
                "Comunicación aumentativa",
                "Divulgación social",
                "Fisioterapia",
                "Formación laboral",
                "Habilidades sociales",
                "Movilidad y autonomía",
                "Psicología",
                "Tecnología asistiva",
                "Trabajo social"
        ));
        return "staff/profesionales/edit";
    }

    @PostMapping("/edit")
    public String processEdit(@ModelAttribute Professional professional,
                              @RequestParam(value = "selectedEspecialidades", required = false) List<String> especialidades,
                              HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        if (especialidades != null) {
            professional.setUniqueSpeciality(String.join(", ", especialidades));
        } else {
            professional.setUniqueSpeciality("");
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
