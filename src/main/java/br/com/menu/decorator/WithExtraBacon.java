package br.com.menu.decorator;

import br.com.menu.MenuItem;
import br.com.utils.Constants;

public class WithExtraBacon implements MenuItem {

    private final MenuItem item;

    public WithExtraBacon(MenuItem item) {
        if (item == null) {
            throw new IllegalArgumentException(Constants.INVALID_NULL_VALUE);
        }
        this.item = item;
    }

    public MenuItem getItem() { return item; }

    @Override
    public String getDescription() {
        return item.getDescription() + " + bacon extra";
    }

    @Override
    public double getPrice() { return item.getPrice() + 6.00; }

    @Override
    public String getBaseName() { return item.getBaseName(); }
}
