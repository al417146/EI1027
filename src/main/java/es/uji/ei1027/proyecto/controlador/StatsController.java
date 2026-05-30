package es.uji.ei1027.proyecto.controlador;

import es.uji.ei1027.proyecto.dao.StatsDAO;
import es.uji.ei1027.proyecto.modelo.UserDetails;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/staff")
public class StatsController {

    @Autowired
    private StatsDAO statsDAO;

    private boolean isStaff(HttpSession session) {
        UserDetails user = (UserDetails) session.getAttribute("user");
        return user != null && "STAFF".equals(user.getRol());
    }

    @GetMapping("/stats")
    public String showStats(Model model, HttpSession session) {
        if (!isStaff(session)) {
            return "redirect:/login";
        }

        model.addAttribute("activeServices", statsDAO.countActiveServices());
        model.addAttribute("pendingRequests", statsDAO.countPendingRequests());
        model.addAttribute("usersWithActiveContracts", statsDAO.countUsersWithActiveContracts());
        model.addAttribute("totalOVIUsers", statsDAO.countTotalOVIUsers());
        model.addAttribute("totalPATIs", statsDAO.countActivePATIs());
        model.addAttribute("attendanceStats", statsDAO.getTrainingAttendanceStats());
        model.addAttribute("requestStatusSummary", statsDAO.getRequestStatusSummary());
        model.addAttribute("topProfessionals", statsDAO.getTopProfessionalsByContracts(3));

        return "staff/stats";
    }
}