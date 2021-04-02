package musigmamart.wishlist;

import java.util.Collection;

import musigmamart.cart.CartItem;

public interface WishList {
	void addToWishList(String sku);
	void deleteFromWishList(String sku);
	void clearWishList();
	int getWishListProductCount();
	Collection<CartItem> getProducts();
}
