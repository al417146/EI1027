package es.uji.ei1027.proyecto.controlador;

import es.uji.ei1027.proyecto.dao.SpecialityDAO;
import es.uji.ei1027.proyecto.modelo.Speciality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/speciality")
public class SpecialityController {

    private SpecialityDAO specialityDAO;

    @Autowired
    public void setSpecialityDAO(SpecialityDAO specialityDAO) {
        this.specialityDAO = specialityDAO;
    }

    @RequestMapping("/list")
    public String list(Model model) {
        model.addAttribute("specialities", specialityDAO.getSpecialities());
        return "speciality/list";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("speciality", new Speciality());
        return "speciality/add";
    }

    @PostMapping("/add")
    public String processAdd(@ModelAttribute Speciality speciality) {
        specialityDAO.addSpeciality(speciality);
        return "redirect:list";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        specialityDAO.deleteSpeciality(id);
        return "redirect:../list";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable String id) {
        model.addAttribute("speciality", specialityDAO.getSpeciality(id));
        return "speciality/edit";
    }

    @PostMapping("/edit")
    public String processEdit(@ModelAttribute Speciality speciality) {
        specialityDAO.updateSpeciality(speciality);
        return "redirect:list";
    }
}
