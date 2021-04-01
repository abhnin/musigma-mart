package musigmamart.products.data;

import org.springframework.data.repository.CrudRepository;


public interface ProductRepository extends CrudRepository<ProductEntity, String>  {
	ProductEntity findBySku(String sku);
	Iterable<ProductEntity> findAllBySkuContaining(String sku);
}
