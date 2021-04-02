package musigmamart.cart;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import musigmamart.products.Product;
import musigmamart.products.ProductService;

public class CartImplTest {
	
	String EXPECTED_TITLE = "Test item";

    Cart cart;
    ProductService productService;

    @BeforeEach
    void setUp()
    {
        productService = mock(ProductService.class);
        cart = new CartImpl(productService);

        when(productService.getProductBySku("test")).thenReturn(new Product("test1", EXPECTED_TITLE, BigDecimal.TEN));
    }

    @Test
    void increasesTotal() {
        cart.addToCart("test");
        cart.addToCart("test");

        assertThat(cart.getCartProductCount()).isEqualTo(2);
    }

    @Test
    void addsNewItem() {
    	cart.addToCart("test");
        assertHasSingleItemWithQty(1);
    }

    @Test
    void increasesQtyForExistingItem() {
    	cart.addToCart("test");
    	cart.addToCart("test");
        assertHasSingleItemWithQty(2);
    }

    @Test
    void removesItem() {
    	cart.addToCart("test");
        cart.deleteFromCart(("test"));
        assertThat(cart.getProducts()).isEmpty();
    }

    @Test
    void decreasesQtyForExistingItem() {
    	cart.addToCart("test");
    	cart.addToCart("test");
        cart.deleteFromCart("test");
        assertHasSingleItemWithQty(1);
    }

    @Test
    void clearsCart() {
    	cart.addToCart("test");
        cart.clearCart();

        assertThat(cart.getProducts()).isEmpty();
        assertThat(cart.getCartProductCount()).isEqualTo(0);
    }

    private void assertHasSingleItemWithQty(int qty) {
        Collection<CartItem> product = cart.getProducts();
        assertThat(product).hasSize(1);
        assertThat(product).allMatch(item -> EXPECTED_TITLE.equals(item.getProduct().getTitle()) && item.getQuantity() == qty);
    }

}
