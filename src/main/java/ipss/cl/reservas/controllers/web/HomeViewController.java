package ipss.cl.reservas.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeViewController {
    @RequestMapping("/")
    public String home() {
        return "home";
    }

}
