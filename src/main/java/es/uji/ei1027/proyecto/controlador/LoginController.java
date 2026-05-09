package es.uji.ei1027.proyecto.controlador;

import es.uji.ei1027.proyecto.dao.UserDao;
import es.uji.ei1027.proyecto.modelo.UserDetails;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    private UserDao userDao;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new UserDetails());
        return "login";
    }

    @PostMapping("/login")
    public String checkLogin(@ModelAttribute("user") UserDetails user,
                             BindingResult bindingResult,
                             HttpSession session) {

        SessionUserValidator validator = new SessionUserValidator();
        validator.validate(user, bindingResult);

        if (bindingResult.hasErrors())
            return "login";

        UserDetails authenticated =
                userDao.loadUserByUsername(user.getMail(), user.getPassword());

        if (authenticated == null) {
            bindingResult.rejectValue("password", "badpw", "Correo o contraseña incorrectos");
            return "login";
        }

        session.setAttribute("user", authenticated);

        String nextUrl = (String) session.getAttribute("nextUrl");
        if (nextUrl != null) {
            session.removeAttribute("nextUrl");
            return "redirect:" + nextUrl;
        }

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
