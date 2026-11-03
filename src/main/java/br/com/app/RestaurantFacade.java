package br.com.app;

import br.com.menu.MenuFactory;
import br.com.menu.MenuItem;
import br.com.order.Order;
import br.com.order.OrderManager;
import br.com.financial.CashRegister;
import br.com.utils.Constants;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RestaurantFacade {

    private final OrderManager orderManager = new OrderManager();
    private final CashRegister cashRegister = new CashRegister();

    public Map<String, Double> getAvailableExtras(int option) {
        return MenuFactory.getAvailableExtras(option);
    }

    public boolean addMenuItemToOrder(
            Order order,
            int option,
            List<String> selectedExtras,
            boolean largeSize
    ) {
        if (order == null) {
            throw new IllegalArgumentException(Constants.INVALID_NULL_VALUE);
        }

        if (option < 1 || option > 9) {
            return false;
        }

        MenuItem item = MenuFactory.createCustomizedItem(
                option,
                selectedExtras,
                largeSize
        );

        order.addItem(item);
        return true;
    }

    public Order createOrder(String customerName) {
        return orderManager.createOrder(customerName);
    }

    public Collection<Order> getOrders() {
        return orderManager.getOrders();
    }

    public Order findOrder(int number) {
        return orderManager.findOrder(number);
    }

    public Set<String> getDistinctItemDescriptions() {
        return orderManager.getDistinctItemDescriptions();
    }

    public boolean removeItemFromOrder(Order order, int index) {
        if (order == null || order.isCanceled()) {
            return false;
        }

        if (orderManager.findOrder(order.getNumber()) != order) {
            return false;
        }

        boolean removed = order.removeItem(index);

        if (!removed) {
            return false;
        }

        if (order.getItems().isEmpty()) {
            cancelOrder(order.getNumber());
        }

        return true;
    }

    public boolean cancelOrder(int number) {
        boolean canceled = orderManager.cancelOrder(number);

        if (canceled) {
            cashRegister.removeTransaction(number);
        }

        return canceled;
    }

    public Set<String> getDistinctBaseNames() {
        return orderManager.getDistinctBaseNames();
    }
    public void registerTransaction(Order order, double tipAmount) {
        cashRegister.registerTransaction(order, tipAmount);
    }

    public double getTipAmount(int orderNumber) {
        return cashRegister.getTipAmount(orderNumber);
    }

    public void updateTransaction(Order order) {
        if (order == null) {
            throw new IllegalArgumentException(Constants.INVALID_NULL_VALUE);
        }

        if (order.isCanceled()) {
            return;
        }

        if (order.getItems().isEmpty() || order.calculateTotal() <= 0) {
            cancelOrder(order.getNumber());
            return;
        }

        cashRegister.updateTransaction(order);
    }

    public double calculateTotalSales() {
        return cashRegister.calculateTotalSales();
    }

    public double calculateTotalTips() {
        return cashRegister.calculateTotalTips();
    }

    public double calculateTotalCash() {
        return cashRegister.calculateTotalCash();
    }
}