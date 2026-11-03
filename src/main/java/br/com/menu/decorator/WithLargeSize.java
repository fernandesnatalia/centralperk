package br.com.menu.decorator;

import br.com.menu.MenuItem;
import br.com.utils.Messages;

public class WithLargeSize implements MenuItem {

    private final MenuItem item;
    private final double additionalPrice;

    public WithLargeSize(MenuItem item, double additionalPrice) {
        this.item = item;
        this.additionalPrice = additionalPrice;
    }

    public MenuItem getItem() { return item; }

    @Override
    public String getDescription() {
        return item.getDescription();
    }

    @Override
    public double getPrice() {
        return item.getPrice() + additionalPrice;
    }

    @Override
    public String getBaseName() { return item.getBaseName(); }

    public double getAdditionalPrice() {
        return additionalPrice;
    }
}
