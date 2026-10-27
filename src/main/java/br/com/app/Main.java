package br.com.app;

import br.com.order.Order;
import br.com.utils.Constants;

public class Main {

    public static void main(String[] args) {

        ConsoleMenu menu = new ConsoleMenu();
        RestaurantFacade restaurant = new RestaurantFacade();

        boolean isRunning = true;

        while (isRunning) {
            menu.displayMenu();
            int option = menu.readOption();

            switch (option) {
                case 1:
                    String customerName = menu.readCustomerName();
                    Order order = restaurant.createOrder(customerName);

                    boolean isAddingItems = true;

                    while (isAddingItems) {
                        int burgerOption = menu.readBurgerOption();
                        boolean isAdded = restaurant.addBurgerToOrder(order, burgerOption);

                        if (!isAdded) {
                            menu.displayMessage(Constants.INVALID_OPTION);
                            continue;
                        }

                        isAddingItems = menu.askToAddAnotherItem();
                    }

                    menu.displayOrder(order);
                    break;
                case 2:
                    menu.displayOrders(restaurant.getOrders());
                    break;
                case 3:
                    int number = menu.readOrderNumber();
                    Order foundOrder = restaurant.findOrder(number);

                    if (foundOrder == null) {
                        menu.displayMessage(Constants.ORDER_NOT_FOUND);
                    } else {
                        menu.displayOrder(foundOrder);
                    }
                    break;
                case 4:
                    menu.displayDailyClosing(restaurant.calculateDailyRevenue());
                    isRunning = false;
                    break;
                case 0:
                    menu.displayMessage(Constants.RESTAURANT_CLOSED);
                    isRunning = false;
                    break;
                default:
                    menu.displayMessage(Constants.INVALID_OPTION);
            }
        }
    }
}