package br.com.app;

import br.com.menu.MenuItem;
import br.com.menu.decorator.WithExtraIngredient;
import br.com.menu.decorator.WithLargeSize;
import br.com.order.Order;
import br.com.utils.Constants;
import br.com.utils.Messages;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
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

    public int readMenuOption() {
        System.out.print(Constants.DIVIDER + Constants.FOOD_MENU + Messages.FOOD_MENU);

        while (true) {
            int option = readInteger(Messages.SELECT_OPTION);

            if (option >= 0 && option <= 9) {
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

    public List<String> readExtras(Map<String, Double> availableExtras) {
        List<String> selectedExtras = new ArrayList<>();
        List<String> extras = new ArrayList<>(availableExtras.keySet());

        while (true) {
            for (int i = 0; i < extras.size(); i++) {
                String extra = extras.get(i);

                System.out.printf(
                    "%d - %s (+ R$ %.2f)%n",
                    i + 1,
                    extra,
                    availableExtras.get(extra)
                );
            }

            System.out.println(Messages.RETURN_MENU);

            int option = readInteger(Messages.SELECT_EXTRA);

            if (option == 0) {
                return selectedExtras;
            }

            if (option < 1 || option > extras.size()) {
                System.out.println(Constants.INVALID_OPTION);
                continue;
            }

            String selected = extras.get(option - 1);

            if (selectedExtras.contains(selected)) {
                System.out.println(Messages.EXTRA_ALREADY_SELECTED);
                continue;
            }

            selectedExtras.add(selected);
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

    private void displayOrderItem(MenuItem item) {
        MenuItem baseItem = item;
        List<MenuItem> additions = new ArrayList<>();

        while (baseItem instanceof WithExtraIngredient || baseItem instanceof WithLargeSize) {

            additions.add(0, baseItem);

            if (baseItem instanceof WithExtraIngredient extra) {
                baseItem = extra.getItem();
            } else {
                WithLargeSize large = (WithLargeSize) baseItem;
                baseItem = large.getItem();
            }
        }

        System.out.printf(
                "%s - R$ %.2f%n",
                baseItem.getDescription(),
                baseItem.getPrice()
        );

        for (MenuItem addition : additions) {
            if (addition instanceof WithExtraIngredient extra) {
                System.out.printf(
                        "    + %s (+ R$ %.2f)%n",
                        extra.getExtraName(),
                        extra.getExtraPrice()
                );
            } else if (addition instanceof WithLargeSize large) {
                System.out.printf(
                        "    + Tamanho grande (+ R$ %.2f)%n",
                        large.getAdditionalPrice()
                );
            }
        }
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
                Messages.DIFFERENT_BASE_ITEMS,
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
