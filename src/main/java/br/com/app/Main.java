package br.com.app;

import br.com.order.Order;
import br.com.utils.Constants;
import br.com.utils.Messages;
import java.util.List;

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

                    if (!addItemsToOrder(order, restaurant, menu)) {
                        restaurant.cancelOrder(order.getNumber());
                        break;
                    }

                    double tip = menu.readTipAmount();
                    restaurant.registerTransaction(order, tip);
                    menu.displayOrder(order, tip);
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
                        menu.displayOrder(foundOrder, restaurant.getTipAmount(number));
                    }
                    break;
                case 4:
                    int orderNumber = menu.readOrderNumber();
                    Order orderToModify = restaurant.findOrder(orderNumber);

                    if (orderToModify == null) {
                        menu.displayMessage(Constants.ORDER_NOT_FOUND);
                        break;
                    }

                    if (orderToModify.isCanceled()) {
                        menu.displayMessage(Constants.ORDER_CANCELED);
                        break;
                    }

                    int modificationOption = menu.readModificationOption();

                    switch (modificationOption) {
                        case 1:
                            if (!addItemsToOrder(orderToModify, restaurant, menu)) {
                                break;
                            }

                            restaurant.updateTransaction(orderToModify);
                            menu.displayOrder(orderToModify, restaurant.getTipAmount(orderNumber));
                            break;
                        case 2:
                            if (orderToModify.getItems().isEmpty()) {
                                menu.displayMessage(Constants.EMPTY_ORDER);
                                break;
                            }

                            menu.displayOrderItems(orderToModify);
                            int itemIndex = menu.readItemIndex(orderToModify);

                            if (!restaurant.removeItemFromOrder(orderToModify, itemIndex)) {
                                menu.displayMessage(Constants.INVALID_OPTION);
                                break;
                            }

                            if (orderToModify.isCanceled()) {
                                menu.displayMessage(Constants.ORDER_CANCELED);
                                break;
                            }

                            restaurant.updateTransaction(orderToModify);
                            menu.displayOrder(orderToModify, restaurant.getTipAmount(orderNumber));
                            break;
                        case 3:
                            if (restaurant.cancelOrder(orderNumber)) {
                                menu.displayMessage(Constants.ORDER_CANCELED);
                            } else {
                                menu.displayMessage(Constants.ORDER_NOT_FOUND);
                            }
                            break;
                        case 0:
                            break;
                    }
                    break;
                case 5:
                    menu.displayDailyClosing(
                            restaurant.calculateTotalSales(),
                            restaurant.calculateTotalTips(),
                            restaurant.calculateTotalCash(),
                            restaurant.getDistinctItemDescriptions(),
                            restaurant.getDistinctBaseNames()
                    );
                    isRunning = false;
                    break;
                case 0:
                    menu.displayMessage(Constants.DIVIDER + Constants.RESTAURANT_CLOSED);
                    isRunning = false;
                    break;
                default:
                    menu.displayMessage(Constants.INVALID_OPTION);
            }
        }
    }

    private static boolean addItemsToOrder(
            Order order,
            RestaurantFacade restaurant,
            ConsoleMenu menu
    ) {
        boolean isAddingItems;
        boolean addedAnyItem = false;

        do {
            int option = menu.readMenuOption();

            if (option == 0) {
                break;
            }

            List<String> selectedExtras = new java.util.ArrayList<>();

            if (menu.askForCustomization()) {
                selectedExtras = menu.readExtras(
                        restaurant.getAvailableExtras(option)
                );
            }

            boolean largeSize = false;

            if (option == 5) {
                largeSize = menu.askYesOrNo(Messages.ASK_LARGE_SIZE);
            }

            boolean isAdded = restaurant.addMenuItemToOrder(
                    order,
                    option,
                    selectedExtras,
                    largeSize
            );

            if (!isAdded) {
                menu.displayMessage(Constants.INVALID_OPTION);
                isAddingItems = true;
            } else {
                addedAnyItem = true;
                isAddingItems = menu.askYesOrNo(Messages.ASK_ADD_ANOTHER);
            }
        } while (isAddingItems);

        return addedAnyItem;
    }
}