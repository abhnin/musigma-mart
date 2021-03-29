package musigmamart.cart;

import org.springframework.stereotype.Controller;

import javax.servlet.*;
import java.io.IOException;

@Controller
public class CartTotalMappingFilter implements Filter {

    private final Cart cart;

    public CartTotalMappingFilter(Cart cart) {
        this.cart = cart;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setAttribute("cctv", this.cart.getCartProductCount());
        chain.doFilter(request, response);
    }
}
