package br.com.menu.decorator;

import br.com.menu.MenuItem;
import br.com.utils.Constants;

public class WithCheddar implements MenuItem {

    private final MenuItem item;

    public WithCheddar(MenuItem item) {
        if (item == null) {
            throw new IllegalArgumentException(Constants.INVALID_NULL_VALUE);
        }
        this.item = item;
    }

    public MenuItem getItem() { return item; }

    @Override
    public String getDescription() {
        return item.getDescription() + " + cheddar";
    }

    @Override
    public double getPrice() { return item.getPrice() + 4.00; }

    @Override
    public String getBaseName() { return item.getBaseName(); }
}