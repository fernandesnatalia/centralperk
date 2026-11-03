package br.com.menu;

import br.com.utils.Constants;

public class Food implements MenuItem {

    private final String name;
    private final double price;

    public Food(String name, double price) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(Constants.INVALID_ITEM_NAME);
        }

        if (price < 0) {
            throw new IllegalArgumentException(Constants.INVALID_ITEM_PRICE);
        }

        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return name;
    }

    @Override
    public String getBaseName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }
}