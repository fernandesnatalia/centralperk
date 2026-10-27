package br.com.order;

import br.com.menu.Burger;
import br.com.utils.Constants;
import java.util.ArrayList;

public class Order {

    private int number;
    private String customerName;
    private ArrayList<Burger> items;

    public Order(int number, String customerName) {
        if (customerName == null || customerName.trim().isEmpty()) {
            throw new IllegalArgumentException(Constants.INVALID_CUSTOMER_NAME);
        }

        this.number = number;
        this.customerName = customerName;
        this.items = new ArrayList<>();
    }

    public void addItem(Burger burger) { items.add(burger); }

    public ArrayList<Burger> getItems() { return new ArrayList<>(items); }

    public int getNumber() { return number; }

    public String getCustomerName() { return customerName; }

    public double calculateTotal() {
        double total = 0;

        for (Burger item : items) {
            total += item.getPrice();
        }

        return total;
    }
}
