package br.com.menu;

import br.com.utils.Constants;

public class Burger {

    private String name;
    private double price;

    public Burger(String name, double price) {
        setName(name);
        setPrice(price);
    }

    public String getName() { return name; }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(Constants.INVALID_BURGER_NAME);
        }

        this.name = name;
    }

    public double getPrice() { return price; }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException(Constants.INVALID_BURGER_PRICE);
        }

        this.price = price;
    }
}