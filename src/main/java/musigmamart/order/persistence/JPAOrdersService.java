package musigmamart.order.persistence;

import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Component;

import musigmamart.order.Order;
import musigmamart.order.OrderService;
import musigmamart.products.Product;
import musigmamart.products.data.ProductEntity;

@Component
public class JPAOrdersService implements OrderService {
	
	private final OrderRepository orderRepository;
	
	JPAOrdersService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

	@Override
	public void placeOrder(String email, String status, String orderDetails, String address) {
		// TODO Auto-generated method stub
		orderRepository.save( new OrderEntity(email, status, orderDetails, address) );
	}

	@Override
	public Iterable<Order> getOrders(String email) {
		return StreamSupport.stream(orderRepository.findAll().spliterator(), false)
				.sorted(Comparator.comparing(OrderEntity::getId).reversed())
				.filter(order -> order.getCustid().equals(email))
	            .map(this::mapEntity)
	            .collect(Collectors.toList());
	}
	
	Order mapEntity(OrderEntity entity) {
        return new Order(entity.getId(), entity.getCustid(), entity.getStatus(), entity.getOrderdetails());
    }

}
