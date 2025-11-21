package ipss.cl.reservas.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reserva")
public class ReservaViewController {
    
    @GetMapping("/nueva")
    public String nuevaReserva() {
        return "reserva/nueva";
    }
}
