package musigmamart.contact;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AboutController {
	
	@GetMapping("/about")
    ModelAndView index() {
         return new ModelAndView("about");
    }

}
