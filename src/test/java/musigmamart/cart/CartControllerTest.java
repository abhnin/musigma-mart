package musigmamart.cart;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import musigmamart.account.AccountService;
import musigmamart.address.Address;
import musigmamart.address.AddressService;
import musigmamart.client.BrowserClient;
import musigmamart.products.Product;
import musigmamart.wishlist.WishList;

@WebMvcTest(controllers = CartController.class)
public class CartControllerTest {
	@Autowired
    MockMvc mockMvc;

    @MockBean
    Cart cart;
    
    @MockBean
    WishList wishlist;


    @MockBean
    AddressService addressService;

    @MockBean
    AccountService accountService;

    @Test
    void addsItemsToCart() throws Exception {
        String expectedSku = "d1";

        mockMvc.perform(MockMvcRequestBuilders.post("/cart").param("sku", expectedSku))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "/"));

        verify(cart).addToCart(expectedSku);
    }

    @Test
    void removesItemsFromCart() throws Exception {
        String expectedSku = "d1";

        mockMvc.perform(MockMvcRequestBuilders.post("/cart/delete").param("sku", expectedSku))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "/cart"));

        verify(cart).deleteFromCart(expectedSku);
    }

    @Test
    void showsCart() {
        CartItem cartProduct1 = new CartItem(new Product("l1", "Product 1", BigDecimal.valueOf(3)), 2);
        CartItem cartProduct2 = new CartItem(new Product("l2", "Product 2", BigDecimal.valueOf(5)), 1);
        when(cart.getProducts()).thenReturn(Arrays.asList(cartProduct1, cartProduct2));

        BrowserClient client = new BrowserClient(mockMvc);
        client.goToCart();

        assertThat(client.getCartProductQtyLabel("Product 1")).isEqualTo("2");
        assertThat(client.getCartProductQtyLabel("Product 2")).isEqualTo("1");
    }

    @Test
    @WithMockUser(username = "abhishek@gmail.com")
    void prePopulatesBasketFields() {
        String expectedAddressLine1 = "";
        String expectedAddressLine2 = "";
        String expectedPostcode = "";

        Address account = new Address(expectedAddressLine1, expectedAddressLine2, expectedPostcode);
        when(addressService.findOrEmpty("abhishek@gmail.com")).thenReturn(account);

        BrowserClient browserClient = new BrowserClient(mockMvc);
        browserClient.goToCart();

        assertThat(browserClient.getAddressLine1()).isEqualTo(expectedAddressLine1);
        assertThat(browserClient.getAddressLine2()).isEqualTo(expectedAddressLine2);
        assertThat(browserClient.getPostcode()).isEqualTo(expectedPostcode);
    }
}
