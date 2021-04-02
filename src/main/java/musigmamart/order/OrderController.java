package musigmamart.order;

import musigmamart.cart.Cart;
import musigmamart.cart.CartItem;
import musigmamart.products.ProductService;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/order")
class OrderController {

    private final Cart cart;
    private final ApplicationEventPublisher eventPublisher;
    private final OrderService orderService;

    public OrderController(Cart cart, ApplicationEventPublisher eventPublisher, OrderService orderService) {
        this.cart = cart;
        this.eventPublisher = eventPublisher;
        this.orderService = orderService;
    }

    @GetMapping
    ModelAndView order() {
        return new ModelAndView("order", Map.of("cartTotal", 0));
    }

    @PostMapping
    String completeOrder(@RequestParam String addressLine1, @RequestParam String addressLine2, @RequestParam String postcode) {
        
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	
    	if(authentication.getName().equals("anonymousUser")) {
    		return "redirect:/login";
    	}
    	
    	
    	final String address = Stream.of(addressLine1, addressLine2, postcode).collect(Collectors.joining(", "));
        String orderList = cart.getProducts().stream().map(e -> e.toString()).reduce("", String::concat);
        
        String orderItems = "";
        
        for (CartItem cartItem : cart.getProducts()) {
            orderItems = orderItems +  "\n" + cartItem.getProduct().getTitle() + " - " + cartItem.getQuantity();
        }
        
        this.orderService.placeOrder(authentication.getName(), "Order Confirmed", orderItems, address);
        this.eventPublisher.publishEvent(new OrderReceivedEvent(address, cart.getProducts()));
        this.cart.clearCart();
        return "redirect:/order";
    }

}
