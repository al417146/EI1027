package es.uji.ei1027.proyecto.controlador;

import es.uji.ei1027.proyecto.dao.MensajeDAO;
import es.uji.ei1027.proyecto.dao.RequestDAO;
import es.uji.ei1027.proyecto.modelo.Request;
import es.uji.ei1027.proyecto.modelo.UserDetails;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/chat")
public class MensajeController {

    @Autowired
    private MensajeDAO mensajeDAO;

    @Autowired
    private RequestDAO requestDAO;

    @GetMapping("/{idRequest}")
    public String chat(@PathVariable int idRequest, Model model, HttpSession session) {
        UserDetails user = (UserDetails) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        Request request = requestDAO.getRequest(idRequest);
        if (request == null) return "redirect:/login";
        String dni = user.getDni();
        if (!request.getDNIUser().equals(dni) && !request.getDNICand().equals(dni) && !"STAFF".equals(user.getRol()))
            return "redirect:/login";
        model.addAttribute("mensajes", mensajeDAO.getMensajes(idRequest));
        model.addAttribute("idRequest", idRequest);
        model.addAttribute("userDni", dni);
        return "chat";
    }

    @PostMapping("/enviar")
    public String enviar(@RequestParam int idRequest,
                         @RequestParam String contenido,
                         HttpSession session) {
        UserDetails user = (UserDetails) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        if (contenido != null && !contenido.trim().isEmpty()) {
            mensajeDAO.enviarMensaje(idRequest, user.getDni(), contenido.trim());
        }
        return "redirect:/chat/" + idRequest;
    }
}
