package es.uji.ei1027.proyecto.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @GetMapping("confirmLogout")
    public String confirmLogout() {
        return "logoutConfirm";
    }
}
