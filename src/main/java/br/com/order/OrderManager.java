package br.com.order;

import java.util.Collection;
import java.util.HashMap;

public class OrderManager {

    private final HashMap<Integer, Order> orders = new HashMap<>();
    private int orderCounter = 1;

    public Order createOrder(String customerName) {
        Order order = new Order(orderCounter, customerName);
        orders.put(orderCounter, order);
        orderCounter++;
        return order;
    }

    public Order findOrder(int number) { return orders.get(number); }

    public Collection<Order> getOrders() { return orders.values(); }

    public double calculateDailyRevenue() {
        double total = 0;

        for (Order order : orders.values()) {
            total += order.calculateTotal();
        }

        return total;
    }
}
