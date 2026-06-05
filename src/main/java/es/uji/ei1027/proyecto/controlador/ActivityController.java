package es.uji.ei1027.proyecto.controlador;

import es.uji.ei1027.proyecto.dao.ActivityDAO;
import es.uji.ei1027.proyecto.dao.OVIUserDAO;
import es.uji.ei1027.proyecto.dao.ProfessionalDAO;
import es.uji.ei1027.proyecto.dao.RegistrationDAO;
import es.uji.ei1027.proyecto.modelo.Activity;
import es.uji.ei1027.proyecto.modelo.OVIUser;
import es.uji.ei1027.proyecto.modelo.Registration;
import es.uji.ei1027.proyecto.modelo.UserDetails;
import es.uji.ei1027.proyecto.service.CertificadoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityDAO activityDAO;

    @Autowired
    private RegistrationDAO registrationDAO;

    @Autowired
    private ProfessionalDAO professionalDAO;

    @Autowired
    private CertificadoService certificadoService;

    @Autowired
    private OVIUserDAO oviUserDAO;

    private boolean isStaff(HttpSession session) {
        UserDetails user = (UserDetails) session.getAttribute("user");
        return user != null && "STAFF".equals(user.getRol());
    }

    // Llista pública d'activitats (tots els usuaris)
    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("activitats", activityDAO.getActivities());
        return "activity/list";
    }

    // Detall d'una activitat
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable int id, Model model, HttpSession session) {
        Activity activity = activityDAO.getActivity(id);
        model.addAttribute("activity", activity);
        model.addAttribute("numInscrits", activityDAO.countRegistrations(id));
        UserDetails user = (UserDetails) session.getAttribute("user");
        if (user != null && "formacio".equals(activity.getType())) {
            model.addAttribute("jaInscrit", registrationDAO.isRegistered(id, user.getDni()));
        }
        return "activity/detail";
    }

    // Inscripció a una activitat de formació (OVIUser)
    @PostMapping("/inscriure/{id}")
    public String inscriure(@PathVariable int id, HttpSession session) {
        UserDetails user = (UserDetails) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        Activity activity = activityDAO.getActivity(id);
        if (activity == null || !"formacio".equals(activity.getType()))
            return "redirect:/activity/list";
        int inscrits = activityDAO.countRegistrations(id);
        if (activity.getMaxParticipants() != null && inscrits >= activity.getMaxParticipants())
            return "redirect:/activity/detail/" + id + "?ple";
        if (activity.getActDate() != null && activity.getActDate().before(new java.util.Date()))
            return "redirect:/activity/detail/" + id + "?passada";
        if (!registrationDAO.isRegistered(id, user.getDni())) {
            Registration r = new Registration();
            r.setIdActivity(id);
            r.setDniUser(user.getDni());
            r.setAttended(false);
            registrationDAO.addRegistration(r);
        }

        return "redirect:/activity/detail/" + id + "?inscrit";
    }

    // Gestió staff - crear activitat
    @GetMapping("/add")
    public String add(Model model, HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        model.addAttribute("activity", new Activity());
        model.addAttribute("professionals", professionalDAO.getProfessionals());
        return "activity/add";
    }

    @PostMapping("/add")
    public String processAdd(@ModelAttribute Activity activity, HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        activityDAO.addActivity(activity);
        return "redirect:/activity/list";
    }

    // Gestió staff - editar activitat
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model, HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        model.addAttribute("activity", activityDAO.getActivity(id));
        model.addAttribute("professionals", professionalDAO.getProfessionals());
        return "activity/edit";
    }

    @PostMapping("/edit")
    public String processEdit(@ModelAttribute Activity activity, HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        activityDAO.updateActivity(activity);
        return "redirect:/activity/list";
    }

    // Gestió staff - eliminar activitat
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        activityDAO.deleteActivity(id);
        return "redirect:/activity/list";
    }

    // Gestió staff - registrar assistència
    @GetMapping("/assistencia/{id}")
    public String assistencia(@PathVariable int id, Model model, HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        model.addAttribute("activity", activityDAO.getActivity(id));
        model.addAttribute("inscrits", registrationDAO.getRegistrationsByActivity(id));
        return "activity/assistencia";
    }

    @PostMapping("/assistencia/update")
    public String updateAssistencia(@RequestParam int idRegist,
                                    @RequestParam(defaultValue = "false") boolean attended,
                                    @RequestParam int idActivity,
                                    HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        registrationDAO.updateAttendance(idRegist, attended);
        return "redirect:/activity/assistencia/" + idActivity;
    }

    @GetMapping("/emitirCertificados/{id}")
    public String emitirCertificados(@PathVariable int id, Model model, HttpSession session) {
        if (!isStaff(session)) return "redirect:/login";
        Activity activity = activityDAO.getActivity(id);
        List<Registration> inscrits = registrationDAO.getRegistrationsByActivity(id);
        List<String> certificados = new ArrayList<>();
        for (Registration r : inscrits) {
            if (r.isAttended()) {
                OVIUser user = oviUserDAO.getOVIUser(r.getDniUser());
                if (user != null) {
                    String cert = certificadoService.generarCertificado(user, activity);
                    certificadoService.guardarEnHistorialFormador(activity, cert);
                    String resultado = certificadoService.enviarCertificado(user.getMail(), cert);
                    certificados.add(resultado);
                }
            }
        }
        model.addAttribute("activity", activity);
        model.addAttribute("certificados", certificados);
        return "activity/certificados";
    }
}
