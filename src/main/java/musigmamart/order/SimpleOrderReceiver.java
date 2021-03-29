package musigmamart.order;

import musigmamart.cart.CartItem;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SimpleOrderReceiver {

    @EventListener
    public void onNewOrder(OrderReceivedEvent event) {
        System.out.println("New order received:");
        System.out.println("Delivery address " + event.getDeliveryAddress());
        for (CartItem cartItem : event.getItems()) {
            System.out.println(cartItem.getProduct().getTitle() + " - " + cartItem.getQuantity());
        }
    }
}
