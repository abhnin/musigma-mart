package musigmamart.products;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import musigmamart.account.AccountService;
import musigmamart.cart.Cart;
import musigmamart.wishlist.WishList;

@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest {
	private WebClient webClient;

	@Autowired
	MockMvc mockMvc;

	@MockBean
	ProductService productService;

	@MockBean
	Cart cart;
	
	@MockBean
	WishList wishlist;

	@MockBean
	AccountService accountService;

	@BeforeEach
	void setUp() {
		this.webClient = MockMvcWebClientBuilder.mockMvcSetup(mockMvc).build();
	}

	@Test
	@DisplayName("home page MuSigma check")
	void returnsLandingPage() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("MuSigma Mart")));
	}

	@Test
	@DisplayName("check list of products have laptop")
	void returnsListOfItemsFromDb() throws Exception {
		final String expectedTitle = "Laptop";
		mockItems(expectedTitle, BigDecimal.valueOf(3));

		HtmlPage page = webClient.getPage("http://localhost:8080/");

		assertThat(page.querySelectorAll(".item-title"))
				.anyMatch(domElement -> expectedTitle.equals(domElement.asText()));
	}

	@Test
	@DisplayName("check total products count")
	void displaysNumberOfItems() throws FailingHttpStatusCodeException, IOException {
		when(cart.getCartProductCount()).thenReturn(3);

		HtmlPage page = webClient.getPage("http://localhost:8080/");

		DomNode totalElement = page.querySelector(".basket-total");
		assertThat(totalElement).isNotNull();
		assertThat(totalElement.asText()).isEqualTo("3");
	}

	@Test
	@DisplayName("check users email in the header")
	@WithMockUser(username = "abhishek@gmail.com")
	void displaysCurrentUserName() throws FailingHttpStatusCodeException, IOException {
		when(cart.getCartProductCount()).thenReturn(3);

		HtmlPage page = webClient.getPage("http://localhost:8080/");

		DomNode totalElement = page.querySelector("#current-user");
		assertThat(totalElement.asText()).isEqualTo("abhishek@gmail.com");
	}

	@Test
	@DisplayName("check for login button when user not logged in")
	void displaysSignupLink() throws FailingHttpStatusCodeException, IOException {
		when(cart.getCartProductCount()).thenReturn(3);

		HtmlPage page = webClient.getPage("http://localhost:8080/");

		HtmlAnchor totalElement = page.querySelector("#current-user");
		assertThat(totalElement.asText()).isEqualTo("Login");
		assertThat(totalElement.getHrefAttribute()).isEqualTo("/login");
	}


	private void mockItems(String title, BigDecimal price) {
		when(productService.getProducts()).thenReturn(Collections.singletonList(new Product("test", title, price)));
	}
	
}
