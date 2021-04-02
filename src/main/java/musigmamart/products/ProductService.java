package musigmamart.products;

public interface ProductService {
	Iterable<Product> getProducts();
	Iterable<Product> getProductsContaining(String type);
	Iterable<Product> getProductsContaining(String type, String sort);
	Product getProductBySku(String sku);
}
