package br.com.app;

import br.com.menu.Burger;
import br.com.menu.BurgerFactory;
import br.com.menu.MenuItem;
import br.com.menu.decorator.WithCheddar;
import br.com.menu.decorator.WithExtraBacon;
import br.com.order.Order;
import br.com.order.OrderManager;
import java.util.Collection;

public class RestaurantFacade {

    private final OrderManager orderManager = new OrderManager();

    public boolean addBurgerToOrder(
            Order order,
            int option,
            boolean removeCheese,
            boolean removeLettuce,
            boolean removeTomato,
            boolean removeOnion,
            boolean cheddar,
            boolean extraBacon
    ) {
        Burger burger;

        switch (option) {
            case 1:
                burger = BurgerFactory.xBurger();
                break;
            case 2:
                burger = BurgerFactory.xSalad();
                break;
            case 3:
                burger = BurgerFactory.xBacon();
                break;
            default:
                return false;
        }

        if (removeCheese) burger.setCheese(false);
        if (removeLettuce) burger.setLettuce(false);
        if (removeTomato) burger.setTomato(false);
        if (removeOnion) burger.setOnion(false);

        MenuItem item = burger;

        if (cheddar) { item = new WithCheddar(item); }
        if (extraBacon) { item = new WithExtraBacon(item); }

        order.addItem(item);
        return true;
    }

    public Order createOrder(String customerName) { return orderManager.createOrder(customerName); }

    public Collection<Order> getOrders() { return orderManager.getOrders(); }

    public Order findOrder(int number) { return orderManager.findOrder(number); }

    public double calculateDailyRevenue() { return orderManager.calculateDailyRevenue(); }

}