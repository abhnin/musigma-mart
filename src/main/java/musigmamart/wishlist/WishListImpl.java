package musigmamart.wishlist;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import musigmamart.cart.CartItem;
import musigmamart.products.Product;
import musigmamart.products.ProductService;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
public class WishListImpl implements WishList {

	private final Map<String, CartItem> items = new ConcurrentHashMap<>();
    private final ProductService productService;
    
    public WishListImpl(ProductService productService) {
        this.productService = productService;
    }
	
	@Override
	public void addToWishList(String sku) {
		Product product = this.productService.getProductBySku(sku);
        this.items.compute(sku, (existingSku, existingItem) -> {
            if (existingItem == null) {
                return new CartItem(product, 1);
            }

            return new CartItem(existingItem.getProduct(), existingItem.getQuantity()+ 1);
        });
		
	}

	@Override
	public void deleteFromWishList(String sku) {
		this.items.computeIfPresent(sku, (s, existingItem) -> {
            if (existingItem.getQuantity() == 1) {
                return null;
            }

            return new CartItem(existingItem.getProduct(), existingItem.getQuantity() - 1);
        });
		
	}

	@Override
	public void clearWishList() {
		this.items.clear();
		
	}

	@Override
	public int getWishListProductCount() {
		return this.items.values().stream().map(CartItem::getQuantity).reduce(0, Integer::sum);
	}

	@Override
	public Collection<CartItem> getProducts() {
		return this.items.values();
	}

}
