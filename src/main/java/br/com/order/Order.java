package br.com.order;

import br.com.menu.MenuItem;
import br.com.utils.Constants;
import java.util.ArrayList;

public class Order {

    private int number;
    private String customerName;
    private ArrayList<MenuItem> items;
    private boolean canceled;

    public Order(int number, String customerName) {

        if (number <= 0) {
            throw new IllegalArgumentException(Constants.ORDER_NOT_FOUND);
        }

        if (customerName == null || customerName.trim().isEmpty()) {
            throw new IllegalArgumentException(Constants.INVALID_CUSTOMER_NAME);
        }

        this.number = number;
        this.customerName = customerName;
        this.items = new ArrayList<>();
        this.canceled = false;
    }

    public void addItem(MenuItem item) {
        if (canceled) {
            throw new IllegalArgumentException(Constants.ORDER_CANCELED);
        }

        if (item == null) {
            throw new IllegalArgumentException(Constants.INVALID_OPTION);
        }

        items.add(item);
    }

    public ArrayList<MenuItem> getItems() {
        return new ArrayList<>(items);
    }

    public int getNumber() {
        return number;
    }

    public String getCustomerName() {
        return customerName;
    }

    public boolean removeItem(int index) {
        if (canceled || index < 0 || index >= items.size()) {
            return false;
        }

        items.remove(index);
        return true;
    }

    public double calculateTotal() {
        double total = 0;

        for (MenuItem item : items) {
            total += item.getPrice();
        }

        return total;
    }

    public void cancel() {
        canceled = true;
    }

    public boolean isCanceled() {
        return canceled;
    }
}
