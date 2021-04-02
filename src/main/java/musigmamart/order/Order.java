package musigmamart.order;

import lombok.Data;

@Data
public class Order {
	private final int idOrders;
    private final String CustomerID;
    private final String Status;
    private final String OrderDetails;
}
