package musigmamart.products;

public interface ProductService {
	Iterable<Product> getProducts();
	Iterable<Product> getProductsContaining(String type);
	Product getProductBySku(String sku);
}
