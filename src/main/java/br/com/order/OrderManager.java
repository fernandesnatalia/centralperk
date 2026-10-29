package br.com.order;

import br.com.menu.MenuItem;
import java.util.*;

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

    public Collection<Order> getOrders() { return new ArrayList<>(orders.values()); }

    public Set<String> getDistinctItemDescriptions() {
        HashSet<String> combinations = new HashSet<>();

        for (Order order : orders.values()) {
            for (MenuItem item : order.getItems()) {
                combinations.add(item.getDescription());
            }
        }

        return combinations;
    }

    public boolean cancelOrder(int number) {
        return orders.remove(number) != null;
    }

    public double calculateDailyRevenue() {
        double total = 0;

        for (Order order : orders.values()) {
            total += order.calculateTotal();
        }

        return total;
    }
}
