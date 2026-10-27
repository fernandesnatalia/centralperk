package br.com.app;

import br.com.menu.Burger;
import br.com.order.Order;
import br.com.order.OrderManager;
import java.util.Collection;

public class RestaurantFacade {

    private final OrderManager orderManager = new OrderManager();

    public boolean addBurgerToOrder(Order order, int option) {
        Burger burger;

        switch (option) {
            case 1:
                burger = new Burger("X-Burger", 16.00);
                break;
            case 2:
                burger = new Burger("X-Salada", 18.00);
                break;
            case 3:
                burger = new Burger("X-Bacon", 22.00);
                break;
            default:
                return false;
        }

        order.addItem(burger);
        return true;
    }

    public Order createOrder(String customerName) { return orderManager.createOrder(customerName); }

    public Collection<Order> getOrders() { return orderManager.getOrders(); }

    public Order findOrder(int number) { return orderManager.findOrder(number); }

    public double calculateDailyRevenue() { return orderManager.calculateDailyRevenue(); }

}