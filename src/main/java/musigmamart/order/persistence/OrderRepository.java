package musigmamart.order.persistence;

import org.springframework.data.repository.CrudRepository;


public interface OrderRepository extends CrudRepository<OrderEntity, String> {
	OrderEntity save(OrderEntity orderEntity);
	//findAll();
}
