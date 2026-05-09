package es.uji.ei1027.proyecto.controlador;

import es.uji.ei1027.proyecto.dao.UserDao;
import es.uji.ei1027.proyecto.modelo.UserDetails;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class SessionUserController {
    private UserDao userDao;


    @Autowired
    public void setSociDao(UserDao userDao) {
        this.userDao = userDao;
    }


    @RequestMapping("/list")//Zona privada para comporbar si el atributo user existe en la sesion
    public String listSocis(HttpSession session, Model model) {
        /*if (session.getAttribute("user") == null) {
            model.addAttribute("user", new UserDetails());
            return "login";
        }
        model.addAttribute("users", userDao.listAllUsers());
        return "user/list";*/

        UserDetails user = (UserDetails) session.getAttribute("user");

        if (user == null) {
            session.setAttribute("nextUrl", "/user/list");
            return "redirect:/login";
        }

        model.addAttribute("users", userDao.listAllUsers());
        return "user/list";
    }

}
