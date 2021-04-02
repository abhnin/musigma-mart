package musigmamart.products.data;

import java.util.Comparator;
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

	@Override
	public Iterable<Product> getProductsContaining(String type) {
		 return StreamSupport.stream(productRepository.findAllBySkuContaining(type).spliterator(), false)
				 	//.sorted((o1, o2)->o1.price.compareTo(o2.price))
		            .map(this::mapEntity)
		            .collect(Collectors.toList());
	}
	
	@Override
	public Iterable<Product> getProductsContaining(String type, String sort) {
		
		if (sort.equals("H2L")) {
			return StreamSupport.stream(productRepository.findAllBySkuContaining(type).spliterator(), false)
				 	.sorted((o1, o2)->o2.price.compareTo(o1.price))
		            .map(this::mapEntity)
		            .collect(Collectors.toList());
		}else if(sort.equals("L2H")) {
			return StreamSupport.stream(productRepository.findAllBySkuContaining(type).spliterator(), false)
				 	.sorted((o1, o2)->o1.price.compareTo(o2.price))
		            .map(this::mapEntity)
		            .collect(Collectors.toList());
		}else {
			return StreamSupport.stream(productRepository.findAllBySkuContaining(type).spliterator(), false)
				 	//.sorted((o1, o2)->o1.price.compareTo(o2.price))
		            .map(this::mapEntity)
		            .collect(Collectors.toList());
		}
		
	}


}
