package es.uji.ei1027.proyecto.controlador;

import es.uji.ei1027.proyecto.dao.SpecialityDAO;
import es.uji.ei1027.proyecto.modelo.Speciality;
import es.uji.ei1027.proyecto.modelo.UserDetails;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/Speciality")
public class SpecialityController {

    private SpecialityDAO specialityDAO;

    @Autowired
    public void setSpecialityDAO(SpecialityDAO specialityDAO) {
        this.specialityDAO = specialityDAO;
    }

    private boolean isStaff(HttpSession session) {
        UserDetails user = (UserDetails) session.getAttribute("user");
        return user != null && "STAFF".equals(user.getRol());
    }

    @GetMapping("/list")
    public String list(Model model, HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        model.addAttribute("specialities", specialityDAO.getSpecialities());
        return "Speciality/list";
    }

    @GetMapping("/add")
    public String add(Model model, HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        model.addAttribute("speciality", new Speciality());
        return "Speciality/add";
    }

    @PostMapping("/add")
    public String processAdd(@ModelAttribute Speciality speciality, HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        specialityDAO.addSpeciality(speciality);
        return "redirect:/Speciality/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        specialityDAO.deleteSpeciality(id);
        return "redirect:/Speciality/list";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable int id, HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        model.addAttribute("speciality", specialityDAO.getSpeciality(id));
        return "Speciality/update";
    }

    @PostMapping("/edit")
    public String processEdit(@ModelAttribute Speciality speciality, HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        specialityDAO.updateSpeciality(speciality);
        return "redirect:/Speciality/list";
    }
}