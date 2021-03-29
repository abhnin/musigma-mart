package musigmamart.order;

import musigmamart.cart.CartItem;
import lombok.Data;

import java.util.Collection;

@Data
public class OrderReceivedEvent {

    private final String deliveryAddress;
    private final Collection<CartItem> items;

}
