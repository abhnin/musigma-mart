package musigmamart.products;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductController {
	private final ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    ModelAndView index() {
    	 Map<String, Object> model = new HashMap<>();
         model.put("products", this.productService.getProducts());

         return new ModelAndView("products", model);
    }
}
