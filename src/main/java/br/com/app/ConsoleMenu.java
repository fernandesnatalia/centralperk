package br.com.app;

import br.com.menu.Burger;
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

            if (input.isEmpty()) { continue; } // TODO

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
            System.out.print(Constants.DIVIDER + Messages.FOOD_MENU);
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

    public boolean askToAddAnotherItem() {
        while (true) {
            System.out.print(Messages.ADD_ANOTHER_ITEM);
            String input = scanner.nextLine().trim();

            if (input.equals("1")) {
                return true;
            }

            if (input.equals("0")) {
                return false;
            }

            System.out.println(Constants.INVALID_OPTION);
        }
    }

    public void displayOrder(Order order) {
        System.out.print(Constants.DIVIDER + Constants.ORDER_REGISTERED);

        System.out.printf(
                "Número: %d%nCliente: %s%n",
                order.getNumber(),
                order.getCustomerName()
        );

        for (Burger burger : order.getItems()) {
            System.out.printf(
                    "%s - R$ %.2f%n",
                    burger.getName(),
                    burger.getPrice()
            );
        }

        System.out.printf("Total: R$ %.2f%n", order.calculateTotal());
    }

    public void displayOrders(Collection<Order> orders) {
        if (orders.isEmpty()) {
            System.out.println(Constants.NO_ORDERS);
            return;
        }

        System.out.print(Constants.DIVIDER + Constants.DAILY_ORDERS);

        for (Order order : orders) {
            System.out.printf(
                    "Pedido: %d \nCliente: %s \nTotal: R$ %.2f%n",
                    order.getNumber(), order.getCustomerName(), order.calculateTotal()
            );
        }
    }

    public int readOrderNumber() {
        System.out.print(Constants.ENTER_ORDER_NUMBER);
        return scanner.nextInt();
    }

    public void displayDailyClosing(double revenue) {
        System.out.printf(
                Constants.DIVIDER +
                Messages.DAILY_CLOSING,
                revenue
        );
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }
}
