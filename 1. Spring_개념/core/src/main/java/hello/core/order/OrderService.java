package hello.core.order;

public interface OrderService {
    Order createOrder(Long memeberId, String itemName, int itemPrice);
}
