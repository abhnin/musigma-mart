package musigmamart.products.data;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import musigmamart.products.Product;
import musigmamart.products.ProductService;

import org.springframework.stereotype.Component;

@Component
public class JPAProductsService implements ProductService {
	private final ProductRepository productRepository;

	JPAProductsService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

	@Override
	public Iterable<Product> getProducts() {
		 return StreamSupport.stream(productRepository.findAll().spliterator(), false)
		            .map(this::mapEntity)
		            .collect(Collectors.toList());
	}
	
	 Product mapEntity(ProductEntity entity) {
	        return new Product(entity.sku, entity.title, entity.price);
	    }

	@Override
	public Product getProductBySku(String sku) {
		ProductEntity entity = this.productRepository.findBySku(sku);
        if (entity == null) {
            return null;
        }

        return mapEntity(entity);
	}


}
