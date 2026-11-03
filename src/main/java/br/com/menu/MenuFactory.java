package br.com.menu;

import br.com.menu.decorator.WithExtraIngredient;
import br.com.menu.decorator.WithLargeSize;
import br.com.utils.Constants;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class MenuFactory {

    private MenuFactory() {
    }

    public static Map<String, Double> getAvailableExtras(int option) {

        Map<String, Double> extras = new LinkedHashMap<>();

        switch (option) {
            case 1:
                extras.put("Creme", 2.00);
                extras.put("Banana", 2.00);
                extras.put("Chantilly", 2.00);
                extras.put("Carne moída", 4.0);
                break;
            case 2:
                extras.put("Peru", 4.0);
                extras.put("Molho gravy",2.00);
                extras.put("Cranberry",2.00);
                break;
            case 3:
                extras.put("Chocolate", 3.0);
                extras.put("Caramelo",2.00);
                extras.put("Coco",2.00);
                break;
            case 4:
                extras.put("Cream cheese", 4.0);
                extras.put("Cremosidade extra", 5.0);
                break;
            case 5:
                extras.put("Pepperoni", 5.0);
                break;
            case 6:
                extras.put("Nozes", 5.0);
                break;
            case 7:
            case 8:
                extras.put("Açúcar", 0.50);
                extras.put("Chantilly", 2.00);
                extras.put("Canela", 2.00);
                break;
            case 9:
                extras.put("Gelo", 0.50);
                extras.put("Sal na borda", 0.50);
                break;
            default:
                throw new IllegalArgumentException(Constants.INVALID_OPTION);
        }

        return extras;
    }

    public static boolean supportsLargeSize(int option) {
        return option == 5;
    }

    public static MenuItem createCustomizedItem(
            int option,
            List<String> selectedExtras,
            boolean largeSize
    ) {
        MenuItem item = switch (option) {
            case 1 -> new Food("Trifle da Rachel", 22.00);
            case 2 -> new Food("Sanduíche MoistMaker do Ross", 28.00);
            case 3 -> new Food("Doces da Monica", 12.00);
            case 4 -> new Food("Cheesecake do Chão", 18.00);
            case 5 -> new Food("Pizza do Joey", 32.00);
            case 6 -> new Food("Biscoitos da Avó da Phoebe", 10.00);
            case 7 -> new Drink("Café Comum", 6.00);
            case 8 -> new Drink("Café Gigante do Central Perk", 14.00);
            case 9 -> new Drink("Margaritas do Ross", 18.00);
            default -> throw new IllegalArgumentException(Constants.INVALID_OPTION);
        };

        Map<String, Double> availableExtras = getAvailableExtras(option);

        if (selectedExtras != null) {
            for (String extra : selectedExtras) {

                Double price = availableExtras.get(extra);

                if (price == null) {
                    throw new IllegalArgumentException(Constants.INVALID_OPTION);
                }

                item = new WithExtraIngredient(item, extra, price);
            }
        }

        if (largeSize) {
            if (!supportsLargeSize(option)) {
                throw new IllegalArgumentException(Constants.INVALID_OPTION);
            }

            double sizePrice = option == 5 ? 5.0 : 8.0;
            item = new WithLargeSize(item, sizePrice);
        }

        return item;
    }
}
