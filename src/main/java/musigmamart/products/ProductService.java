package musigmamart.products;

public interface ProductService {
	Iterable<Product> getProducts();
	Product getProductBySku(String sku);
}
