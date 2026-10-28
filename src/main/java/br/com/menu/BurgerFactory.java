package br.com.menu;

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
}
