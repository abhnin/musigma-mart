package musigmamart.wishlist;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/wishlist")
public class WishListController {
	private final WishList wishlist;

	public WishListController(WishList wishlist) {
		this.wishlist = wishlist;
	}
	
	@PostMapping
    String addToWishList(@RequestParam String sku) {
        this.wishlist.addToWishList(sku);
        return "redirect:/";
    }

    @GetMapping
    ModelAndView showWishList() {
        return new ModelAndView("wishlist", Map.of("wishlistcct", wishlist.getWishListProductCount(), "wishlistProducts", wishlist.getProducts()));
    }

    @PostMapping("/delete")
    String deleteFromCart(@RequestParam String sku) {
        this.wishlist.deleteFromWishList(sku);
        return "redirect:/wishlist";
    }
	
	

}
