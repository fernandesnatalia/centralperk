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
        System.out.print(
                Constants.DIVIDER +
                Messages.MAIN_MENU +
                Constants.SELECT_OPTION
        );
    }

    public int readOption() {
        while (true) {
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println(Constants.INVALID_OPTION);
                continue;
            }

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println(Constants.INVALID_OPTION + Constants.SELECT_OPTION);
            }
        }
    }

    public String readCustomerName() {
        while (true) {
            System.out.print(Constants.ENTER_CUSTOMER_NAME);
            String customerName = scanner.nextLine();

            if (customerName != null && !customerName.trim().isEmpty()) {
                return customerName;
            }

            System.out.println(Constants.INVALID_CUSTOMER_NAME);
        }
    }

    public int readBurgerOption() {
        while (true) {
            System.out.print(Constants.DIVIDER + Messages.FOOD_MENU + Constants.SELECT_OPTION);
            String input = scanner.nextLine().trim();

            try {
                int option = Integer.parseInt(input);
                if (option >= 1 && option <= 3) {
                    return option;
                }
                System.out.println(Constants.INVALID_OPTION);
            } catch (NumberFormatException e) {
                System.out.println(Constants.INVALID_OPTION);
            }
        }
    }

    public boolean askForCustomization() {
        return askYesOrNo(Messages.ASK_CUSTOMIZATION);
    }

    public boolean askToAddAnotherItem() {
        while (true) {
            System.out.print(Messages.ASK_ADD_ANOTHER);
            String input = scanner.nextLine().trim();

            if (input.equals("1")) { return true; }
            if (input.equals("0")) { return false; }

            System.out.println(Constants.INVALID_OPTION);
        }
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

    public int readItemIndex(Order order) {
        while (true) {
            System.out.print(Messages.ENTER_ITEM_NUMBER);
            String input = scanner.nextLine().trim();

            try {
                int index = Integer.parseInt(input);

                if (index >= 1 && index <= order.getItems().size()) {
                    return index - 1;
                }

                System.out.println(Constants.INVALID_OPTION);
            } catch (NumberFormatException e) {
                System.out.println(Constants.INVALID_OPTION);
            }
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

    public void displayOrder(Order order) {
        System.out.print(Constants.DIVIDER + Constants.ORDER_REGISTERED);
        displayOrderDetails(order);
    }

    public void displayOrders(Collection<Order> orders) {

        System.out.print(Constants.DIVIDER + Constants.DAILY_ORDERS);

        if (orders.isEmpty()) {
            System.out.println(Constants.NO_ORDERS);
            return;
        }

        for (Order order : orders) {
            displayOrderDetails(order);
            System.out.println("————————————");
        }
    }

    private void displayOrderDetails(Order order) {
        System.out.printf(
                "%nNúmero: %d%nCliente: %s%n",
                order.getNumber(),
                order.getCustomerName()
        );

        for (MenuItem item : order.getItems()) {
            displayOrderItem(item);
        }

        System.out.printf("Total: R$ %.2f%n", order.calculateTotal());
    }

    public int readOrderNumber() {
        while (true) {
            System.out.print(Constants.ENTER_ORDER_NUMBER);
            String input = scanner.nextLine().trim();

            try {
                int number = Integer.parseInt(input);

                if (number > 0) {
                    return number;
                }

                System.out.println(Constants.INVALID_OPTION);
            } catch (NumberFormatException e) {
                System.out.println(Constants.INVALID_OPTION);
            }
        }
    }

    public void displayDailyClosing(
            double revenue,
            Collection<String> combinations
    ) {
        System.out.printf(Constants.DIVIDER + Messages.DAILY_CLOSING, revenue);
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

        System.out.println(Constants.DIVIDER);
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public int readModificationOption() {
        while (true) {
            System.out.println(
                    Constants.DIVIDER +
                    Messages.MODIFICATION_MENU +
                    Constants.SELECT_OPTION
            );

            String input = scanner.nextLine().trim();

            try {
                int option = Integer.parseInt(input);

                if (option >= 0 && option <= 3) {
                    return option;
                }
            } catch (NumberFormatException e) {
                // TODO
            }

            System.out.println(Constants.INVALID_OPTION);
        }
    }
}
