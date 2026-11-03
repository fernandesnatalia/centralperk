package br.com.order;

import br.com.menu.MenuItem;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class OrderManager {

    private final Map<Integer, Order> orders = new HashMap<>();
    private final List<Order> orderHistory = new ArrayList<>();

    private int orderCounter = 1;

    public Order createOrder(String customerName) {
        Order order = new Order(orderCounter, customerName);
        orders.put(orderCounter, order);
        orderHistory.add(order);
        orderCounter++;
        return order;
    }

    public Order findOrder(int number) {
        for (Order order : orderHistory) {
            if (order.getNumber() == number) {
                return order;
            }
        }

        return null;
    }

    public Collection<Order> getOrders() {
        return new ArrayList<>(orderHistory);
    }

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
        Order order = orders.remove(number);

        if (order == null) {
            return false;
        }

        order.cancel();
        return true;
    }

    public Set<String> getDistinctBaseNames() {
        Set<String> baseNames = new HashSet<>();

        for (Order order : orders.values()) {
            for (MenuItem item : order.getItems()) {
                baseNames.add(item.getBaseName());
            }
        }

        return baseNames;
    }
}
