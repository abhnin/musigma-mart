package musigmamart.order;

import musigmamart.address.Address;

public interface OrderService {
	Iterable<Order> getOrders(String email);
	void placeOrder(String email, String status, String orderDetails, String address);
	
}
