package br.com.menu;

import br.com.utils.Constants;

public class Burger implements MenuItem {

    private String name;
    private double price;
    private boolean cheese = true;
    private boolean lettuce = true;
    private boolean tomato = true;
    private boolean onion = true;

    public Burger(String name, double price) {
        setName(name);
        setPrice(price);
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(Constants.INVALID_BURGER_NAME);
        }

        this.name = name;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException(Constants.INVALID_BURGER_PRICE);
        }

        this.price = price;
    }

    @Override
    public String getDescription() {
        String description = name;

        if (!cheese) description += " sem queijo";
        if (!lettuce) description += " sem alface";
        if (!tomato) description += " sem tomate";
        if (!onion) description += " sem cebola";

        return description;
    }

    @Override
    public String getBaseName() {
        return name;
    }

    public String getName() {
        return name;
    }

    public String getRemovedIngredients() {
        StringBuilder ingredients = new StringBuilder();

        if (!cheese) ingredients.append("queijo, ");
        if (!lettuce) ingredients.append("alface, ");
        if (!tomato) ingredients.append("tomate, ");
        if (!onion) ingredients.append("cebola, ");

        if (ingredients.isEmpty()) {
            return "";
        }

        ingredients.setLength(ingredients.length() - 2);
        return ingredients.toString();
    }

    public double getPrice() { return price; }

    public void setCheese(boolean cheese) { this.cheese = cheese; }

    public void setLettuce(boolean lettuce) { this.lettuce = lettuce; }

    public void setTomato(boolean tomato) { this.tomato = tomato; }

    public void setOnion(boolean onion) { this.onion = onion; }
}