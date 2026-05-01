package es.uji.ei1027.proyecto.controlador;


import es.uji.ei1027.proyecto.dao.ContractDAO;
import es.uji.ei1027.proyecto.dao.RequestDAO;
import es.uji.ei1027.proyecto.modelo.Contract;
import es.uji.ei1027.proyecto.modelo.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static java.lang.Math.random;

@Controller
@RequestMapping("/PAP-PATI")
public class patiController {

    @Autowired
    RequestDAO requestDAO;

    @Autowired
    ContractDAO contractDAO;

    // Listar las solicitudes pendientes dirigidas a este PAP/PATI
    @GetMapping("/solicitudesPendientes")
    public String listSolicitudesPendientes(Model model, Principal principal) {
        String dniPati = principal.getName();
        List<Request> solicitudes = requestDAO.getPendingRequestsForPati(dniPati);
        model.addAttribute("solicitudes", solicitudes);
        return "PAP-PATI/solicitudesPendientes";
    }

    //Acepta una solicitud y genera el contrato
    @PostMapping("/aceptarSolicitud")
    public String acceptRequest(@RequestParam int idRequest){
        Request r = requestDAO.getRequestById(idRequest);

        if (r == null || "Pendiente".equals(r.getStatus()))
            return "redirect:/pati/solicitudesPendientes?error=invalid";

        Contract contract = new Contract();
        int idContract =  new Random().nextInt();
        contract.setIdContract(idContract);
        contract.setDateStart(new Date());
        contract.setDateEnd(null); //Por predeterminado, los contratos serán indefinidos
        contract.setIdRequest(r.getIdRequest());
        contract.setDNICand(r.getDNICand());

        r.setIdContract(idContract);

        contractDAO.addContract(contract);
        requestDAO.updateRequestStatus(r.getIdRequest(), "Aceptado", idContract);

        return "redirect:/pati/solicitudesPendientes?success=accepted";
    }
}

