package musigmamart.cart;

import org.springframework.stereotype.Controller;

import musigmamart.wishlist.WishList;

import javax.servlet.*;
import java.io.IOException;

@Controller
public class CartTotalMappingFilter implements Filter {

    private final Cart cart;
    private final WishList wishlist;

    public CartTotalMappingFilter(Cart cart, WishList wishlist) {
        this.cart = cart;
        this.wishlist = wishlist;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setAttribute("cctv", this.cart.getCartProductCount());
        request.setAttribute("wishcctv", this.wishlist.getWishListProductCount());
        chain.doFilter(request, response);
    }
}
