package br.com.menu;

import br.com.menu.decorator.WithCheddar;
import br.com.menu.decorator.WithExtraBacon;
import br.com.utils.Constants;

public final class BurgerFactory {

    private BurgerFactory() { }

    public static Burger xBurger() {
        return new Burger("X-Burger", 16.00);
    }

    public static Burger xSalad() {
        return new Burger("X-Salada", 18.00);
    }

    public static Burger xBacon() {
        return new Burger("X-Bacon", 22.00);
    }

    public static MenuItem createCustomizedBurger(
            int option,
            boolean removeCheese,
            boolean removeLettuce,
            boolean removeTomato,
            boolean removeOnion,
            boolean cheddar,
            boolean extraBacon
    ) {
        Burger burger;

        switch (option) {
            case 1:
                burger = xBurger();
                break;
            case 2:
                burger = xSalad();
                break;
            case 3:
                burger = xBacon();
                break;
            default:
                throw new IllegalArgumentException(Constants.INVALID_OPTION);
        }

        if (removeCheese) burger.setCheese(false);
        if (removeLettuce) burger.setLettuce(false);
        if (removeTomato) burger.setTomato(false);
        if (removeOnion) burger.setOnion(false);

        MenuItem item = burger;

        if (cheddar) {
            item = new WithCheddar(item);
        }

        if (extraBacon) {
            item = new WithExtraBacon(item);
        }

        return item;
    }
}
