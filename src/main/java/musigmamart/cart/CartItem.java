package musigmamart.cart;

import java.math.BigDecimal;

import lombok.Data;
import musigmamart.products.Product;

@Data
public class CartItem {
	
	final private Product product;
	final private int quantity;
	
	BigDecimal getTotalPriceForProduct() {
		return this.product.getPrice().multiply(BigDecimal.valueOf(quantity));
	}
	

}
