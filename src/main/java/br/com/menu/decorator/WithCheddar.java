package br.com.menu.decorator;

import br.com.menu.MenuItem;

public class WithCheddar implements MenuItem {

    private final MenuItem item;

    public WithCheddar(MenuItem item) { this.item = item; }

    public MenuItem getItem() { return item; }

    @Override
    public String getDescription() {
        return item.getDescription() + " + cheddar";
    }

    @Override
    public double getPrice() { return item.getPrice() + 4.00; }
}