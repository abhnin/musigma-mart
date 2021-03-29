package musigmamart.cart;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/cart")
public class CartController {
	private final Cart cart;

    CartController(Cart cart) {
        this.cart = cart;
    }

    @PostMapping
    String addToCart(@RequestParam String sku) {
        this.cart.addToCart(sku);
        return "redirect:/";
    }

    @GetMapping
    ModelAndView showCart() {
        return new ModelAndView("cart", Map.of("cct", cart.getCartProductCount(), "cartProducts", cart.getProducts()));
    }

    @PostMapping("/delete")
    String deleteFromCart(@RequestParam String sku) {
        this.cart.deleteFromCart(sku);
        return "redirect:/cart";
    }
}
