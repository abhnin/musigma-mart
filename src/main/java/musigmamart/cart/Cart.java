package musigmamart.cart;

import java.util.Collection;

public interface Cart {
	void addToCart(String sku);
	void deleteFromCart(String sku);
	void clearCart();
	int getCartProductCount();
	Collection<CartItem> getProducts();
	
	
}
