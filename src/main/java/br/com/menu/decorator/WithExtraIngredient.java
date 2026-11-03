package br.com.menu.decorator;

import br.com.menu.MenuItem;

public class WithExtraIngredient implements MenuItem {

    private final MenuItem item;
    private final String extraName;
    private final double extraPrice;

    public WithExtraIngredient(
            MenuItem item,
            String extraName,
            double extraPrice
    ) {
        this.item = item;
        this.extraName = extraName;
        this.extraPrice = extraPrice;
    }

    public String getExtraName() { return extraName; }

    public double getExtraPrice() { return extraPrice; }

    public MenuItem getItem() { return item; }

    @Override
    public String getDescription() {
        return item.getDescription() + " + " + extraName;
    }

    @Override
    public double getPrice() {
        return item.getPrice() + extraPrice;
    }

    @Override
    public String getBaseName() { return item.getBaseName(); }
}