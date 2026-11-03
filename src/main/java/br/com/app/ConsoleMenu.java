package br.com.app;

import br.com.menu.Burger;
import br.com.menu.MenuItem;
import br.com.menu.decorator.WithCheddar;
import br.com.menu.decorator.WithExtraBacon;
import br.com.order.Order;
import br.com.utils.Constants;
import br.com.utils.Messages;
import java.util.Collection;
import java.util.Scanner;

public class ConsoleMenu {

    private final Scanner scanner = new Scanner(System.in);

    public void displayMenu() {
        System.out.print(Constants.DIVIDER + Constants.MAIN_MENU + Messages.MAIN_MENU);
    }

    public int readOption() {
        while (true) {
            int option = readInteger(Messages.SELECT_OPTION);

            if (option >= 0 && option <= 5) {
                return option;
            }

            System.out.println(Constants.INVALID_OPTION);
        }
    }

    private int readInteger(String message) {
        while (true) {
            System.out.print(message);

            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println(Constants.INVALID_OPTION);
            }
        }
    }

    public int readBurgerOption() {
        while (true) {
            int option = readInteger(Constants.DIVIDER + Constants.FOOD_MENU + Messages.FOOD_MENU + Messages.SELECT_OPTION);

            if (option >= 0 && option <= 3) {
                return option;
            }

            System.out.println(Constants.INVALID_OPTION);
        }
    }

    public int readItemIndex(Order order) {
        while (true) {
            int index = readInteger(Messages.ENTER_ITEM_NUMBER);

            if (index >= 1 && index <= order.getItems().size()) {
                return index - 1;
            }

            System.out.println(Constants.INVALID_OPTION);
        }
    }

    public int readOrderNumber() {
        while (true) {
            int number = readInteger(Messages.ENTER_ORDER_NUMBER);

            if (number > 0) {
                return number;
            }

            System.out.println(Constants.INVALID_OPTION);
        }
    }

    public int readModificationOption() {
        while (true) {
            int option = readInteger(Constants.DIVIDER + Messages.MODIFICATION_MENU + Messages.SELECT_OPTION);

            if (option >= 0 && option <= 3) {
                return option;
            }

            System.out.println(Constants.INVALID_OPTION);
        }
    }

    public String readCustomerName() {
        while (true) {
            System.out.print(Messages.ENTER_CUSTOMER_NAME);
            String customerName = scanner.nextLine();

            if (customerName != null && !customerName.trim().isEmpty()) {
                return customerName;
            }

            System.out.println(Constants.INVALID_CUSTOMER_NAME);
        }
    }

    public boolean askForCustomization() {
        return askYesOrNo(Messages.ASK_CUSTOMIZATION);
    }

    public boolean askYesOrNo(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            if (input.equals("1")) { return true; }
            if (input.equals("0")) { return false; }

            System.out.println(Constants.INVALID_OPTION);
        }
    }

    private Burger getBaseBurger(MenuItem item) {
        while (true) {
            if (item instanceof WithCheddar) {
                item = ((WithCheddar) item).getItem();
            } else if (item instanceof WithExtraBacon) {
                item = ((WithExtraBacon) item).getItem();
            } else if (item instanceof Burger) {
                return (Burger) item;
            } else {
                return null;
            }
        }
    }

    private void displayOrderItem(MenuItem item) {
        Burger burger = getBaseBurger(item);

        if (burger == null) {
            System.out.printf(
                    "%s - R$ %.2f%n",
                    item.getDescription(),
                    item.getPrice()
            );
            return;
        }

        System.out.printf(
                "%s - R$ %.2f%n",
                burger.getName(),
                burger.getPrice()
        );

        displayAdditions(item);

        String removedIngredients = burger.getRemovedIngredients();

        if (!removedIngredients.isEmpty()) {
            System.out.printf("    - %s%n", removedIngredients);
        }
    }

    private void displayAdditions(MenuItem item) {
        if (item instanceof WithCheddar) {
            WithCheddar cheddar = (WithCheddar) item;
            displayAdditions(cheddar.getItem());
            System.out.printf("    + cheddar - R$ %.2f%n", 4.00);

        } else if (item instanceof WithExtraBacon) {
            WithExtraBacon bacon = (WithExtraBacon) item;
            displayAdditions(bacon.getItem());
            System.out.printf("    + bacon extra - R$ %.2f%n", 6.00);
        }
    }

    public void displayOrderItems(Order order) {
        var items = order.getItems();

        if (items.isEmpty()) {
            System.out.println(Constants.EMPTY_ORDER);
            return;
        }

        System.out.println(Constants.ORDER_ITEMS);

        for (int i = 0; i < items.size(); i++) {
            MenuItem item = items.get(i);

            System.out.printf(
                    "%d. %s - R$ %.2f%n",
                    i + 1,
                    item.getDescription(),
                    item.getPrice()
            );
        }
    }

    public void displayOrder(Order order, double tipAmount) {
        System.out.print(Constants.DIVIDER + Constants.ORDER_REGISTERED);
        displayOrderDetails(order, tipAmount);
    }

    public void displayOrders(Collection<Order> orders) {

        System.out.print(Constants.DIVIDER + Constants.DAILY_ORDERS);

        if (orders.isEmpty()) {
            System.out.println(Constants.NO_ORDERS);
            return;
        }

        int i = 0;

        for (Order order : orders) {
            if (i > 0) {
                System.out.println("----------");
            }
            displayOrderDetails(order, null);
            i++;
        }
    }

    private void displayOrderDetails(Order order, Double tipAmount) {
        System.out.printf(
                "%nNúmero: %d%nCliente: %s%n",
                order.getNumber(),
                order.getCustomerName()
        );

        if (order.isCanceled()) {
            System.out.println(Constants.CANCELED_STATUS);
            return;
        }

        for (MenuItem item : order.getItems()) {
            displayOrderItem(item);
        }

        double subtotal = order.calculateTotal();

        if (tipAmount == null) {
            System.out.printf("Total: R$ %.2f%n", subtotal);
            return;
        }

        System.out.printf(
                Messages.SUBTOTAL + Messages.TOTAL_TIPS + Messages.TOTAL,
                subtotal, tipAmount, subtotal + tipAmount
        );
    }

    public void displayDailyClosing(
            double sales,
            double tips,
            double totalCash,
            Collection<String> combinations,
            Collection<String> baseNames
    ) {
        System.out.println(Constants.DIVIDER + Constants.DAILY_CLOSING);

        System.out.printf(
                Messages.TOTAL_SALES + Messages.TOTAL_TIPS + Messages.TOTAL_CASH,
                sales, tips, totalCash
        );

        System.out.printf(
                Messages.DIFFERENT_COMBINATIONS,
                combinations.size()
        );

        int itemNumber = 1;

        for (String combination : combinations) {
            System.out.printf(
                    "%d. %s%n",
                    itemNumber++,
                    combination
            );
        }

        System.out.printf(
                Messages.DIFFERENT_BASE_BURGERS,
                baseNames.size()
        );

        itemNumber = 1;

        for (String baseName : baseNames) {
            System.out.printf(
                    "%d. %s%n",
                    itemNumber++,
                    baseName
            );
        }

        System.out.println(Constants.DIVIDER);
    }


    public void displayMessage(String message) {
        System.out.println(message);
    }

    public double readTipAmount() {
        while (true) {
            System.out.print(Messages.ENTER_TIP);

            String input = scanner.nextLine().trim().replace(",", ".");

            try {
                double tip = Double.parseDouble(input);

                if (Double.isFinite(tip) && tip >= 0) {
                    return tip;
                }

                System.out.println(Constants.INVALID_VALUE);

            } catch (NumberFormatException e) {
                System.out.println(Constants.INVALID_VALUE);
            }
        }
    }
}
