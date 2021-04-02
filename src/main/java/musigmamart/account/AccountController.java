package musigmamart.account;

import musigmamart.address.Address;
import musigmamart.address.AddressService;
import musigmamart.order.OrderService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/account")
class AccountController {

    private final AddressService addressService;
    private final OrderService orderService;

    AccountController(AddressService addressService, OrderService orderService) {
        this.addressService = addressService;
        this.orderService = orderService;
    }

    @GetMapping
    ModelAndView account(Principal principal) {
        Map<String, Object> model = new HashMap<>();

        Address account = this.addressService.findOrEmpty(principal.getName());
        
        model.put("pastorders", this.orderService.getOrders(principal.getName()));
        model.put("addressLine1", account.getAddressLine1());
        model.put("addressLine2", account.getAddressLine2());
        model.put("postcode", account.getPostcode());

        return new ModelAndView("account", model);
    }

    @PostMapping
    String updateAccount(Principal principal, String addressLine1, String addressLine2, String postcode) {
        if (principal == null) {
            return "redirect:/login";
        }

        this.addressService.update(principal.getName(), addressLine1, addressLine2, postcode);
        return "redirect:/account";
    }
}
