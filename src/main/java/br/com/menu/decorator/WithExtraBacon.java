package br.com.menu.decorator;

import br.com.menu.MenuItem;

public class WithExtraBacon implements MenuItem {

    private final MenuItem item;

    public WithExtraBacon(MenuItem item) { this.item = item; }

    public MenuItem getItem() { return item; }

    @Override
    public String getDescription() {
        return item.getDescription() + " + bacon extra";
    }

    @Override
    public double getPrice() { return item.getPrice() + 6.00; }
}
