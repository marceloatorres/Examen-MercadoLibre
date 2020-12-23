package api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontController {
	@RequestMapping("/index")
	public String index(Model modelo) {
	    return "index";
	}
	
	@RequestMapping("/stats")
	public String stats(Model modelo) {
	    return "stats";
	}
}
