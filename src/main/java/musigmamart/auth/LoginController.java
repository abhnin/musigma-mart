package musigmamart.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    ModelAndView login(@RequestParam(required = false) String error) {
    	
    	Map<String, Object> model = new HashMap<>();
        model.put("errorMessage", "Incorrect Login ID or Password"); //Show error message
        
        if(error != null) {
        	return new ModelAndView("login", model);
        }else {
        	return new ModelAndView("login");
        }
        
    	
    }

}
