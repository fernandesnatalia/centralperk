package br.com.app;

import br.com.menu.Food;
import br.com.order.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class RestaurantFacadeTest {

    private RestaurantFacade restaurant;

    @BeforeEach
    void setUp() {
        restaurant = new RestaurantFacade();
    }

    @Test
    void shouldCreateOrder() {
        Order order = restaurant.createOrder("Joey");

        assertNotNull(order);
        assertEquals("Joey", order.getCustomerName());
        assertTrue(restaurant.getOrders().contains(order));
    }

    @Test
    void shouldFindExistingOrder() {
        Order order = restaurant.createOrder("Rachel");
        assertEquals(order, restaurant.findOrder(order.getNumber()));
    }

    @Test
    void shouldReturnNullWhenOrderDoesNotExist() {
        Order result = restaurant.findOrder(99);
        assertNull(result);
    }

    @Test
    void shouldAddMenuItemToOrder() {
        Order order = restaurant.createOrder("Monica");

        boolean result = restaurant.addMenuItemToOrder(order, 5, List.of(), false);

        assertTrue(result);
        assertEquals(1, order.getItems().size());
        assertEquals(32.00, order.calculateTotal(), 0.01);
    }

    @Test
    void shouldRejectNullOrderWhenAddingItem() {
        assertThrows(IllegalArgumentException.class,
                () -> restaurant.addMenuItemToOrder(null, 5, List.of(), false)
        );
    }

    @Test
    void shouldRejectInvalidMenuOption() {
        Order order = restaurant.createOrder("Ross");

        assertFalse(restaurant.addMenuItemToOrder(order, 10, List.of(), false));
        assertTrue(order.getItems().isEmpty());
    }

    @Test
    void shouldReturnAvailableExtras() {
        assertTrue(restaurant.getAvailableExtras(5).containsKey("Pepperoni"));
    }

    @Test
    void shouldRemoveItemFromOrder() {
        Order order = restaurant.createOrder("Phoebe");

        restaurant.addMenuItemToOrder(order, 5, List.of(), false);

        assertTrue(restaurant.removeItemFromOrder(order, 0));
        assertTrue(order.getItems().isEmpty());
        assertTrue(order.isCanceled());
    }

    @Test
    void shouldRejectRemovingItemFromInvalidOrder() {
        Order order = restaurant.createOrder("Rachel");
        assertFalse(restaurant.removeItemFromOrder(order, 0));
    }

    @Test
    void shouldRejectRemovingItemFromUnregisteredOrder() {
        Order unregisteredOrder = new Order(99, "Monica");
        unregisteredOrder.addItem(new Food("Pizza", 32));

        assertFalse(restaurant.removeItemFromOrder(unregisteredOrder, 0));
        assertEquals(1, unregisteredOrder.getItems().size());
    }

    @Test
    void shouldRejectUnregisteredOrderWithSameNumber() {
        Order existingOrder = restaurant.createOrder("Rachel");

        Order unregisteredOrder = new Order(existingOrder.getNumber(), "Monica");
        unregisteredOrder.addItem(new Food("Pizza", 32));

        assertFalse(restaurant.removeItemFromOrder(unregisteredOrder, 0));
        assertTrue(existingOrder.getItems().isEmpty());
    }

    @Test
    void shouldRejectRemovingItemFromCanceledOrder() {
        Order order = restaurant.createOrder("Joey");

        restaurant.addMenuItemToOrder(order, 5, List.of(), false);
        restaurant.cancelOrder(order.getNumber());

        assertFalse(restaurant.removeItemFromOrder(order, 0));
    }

    @Test
    void shouldCancelOrderAndRemoveFinancialTransaction() {
        Order order = restaurant.createOrder("Chandler");

        restaurant.addMenuItemToOrder(order, 5, List.of(), false);
        restaurant.registerTransaction(order, 15);

        assertTrue(restaurant.cancelOrder(order.getNumber()));
        assertTrue(order.isCanceled());

        assertEquals(0.00, restaurant.calculateTotalSales(), 0.001);
        assertEquals(0.00, restaurant.calculateTotalTips(), 0.001);
        assertEquals(0.00, restaurant.calculateTotalCash(), 0.001);
    }

    @Test
    void shouldReturnFalseWhenCancelingNonexistentOrder() {
        assertFalse(restaurant.cancelOrder(99));
    }


    @Test
    void shouldRegisterTransaction() {
        Order order = restaurant.createOrder("Joey");

        restaurant.addMenuItemToOrder(order, 5, List.of(), false);
        restaurant.registerTransaction(order, 5);

        assertEquals(32.00, restaurant.calculateTotalSales(), 0.001);
        assertEquals(5.00, restaurant.calculateTotalTips(), 0.001);
        assertEquals(37.00, restaurant.calculateTotalCash(), 0.001);
        assertEquals(5.00, restaurant.getTipAmount(order.getNumber()), 0.001);
    }

    @Test
    void shouldUpdateTransactionWhenOrderChanges() {
        Order order = restaurant.createOrder("Chandler");

        restaurant.addMenuItemToOrder(order, 5, List.of(), false);
        restaurant.registerTransaction(order, 5);

        order.addItem(new Food("Mac and Cheese do Chandler", 18));

        restaurant.updateTransaction(order);

        assertEquals(50.00, restaurant.calculateTotalSales(), 0.001);
        assertEquals(5.00, restaurant.calculateTotalTips(), 0.001);
        assertEquals(55.00, restaurant.calculateTotalCash(), 0.001);
    }

    @Test
    void shouldCancelOrderWhenUpdatedWithoutItems() {
        Order order = restaurant.createOrder("Monica");

        restaurant.addMenuItemToOrder(order, 5, List.of(), false);
        restaurant.registerTransaction(order, 5);

        order.removeItem(0);

        restaurant.updateTransaction(order);

        assertTrue(order.isCanceled());
        assertEquals(0.00, restaurant.calculateTotalSales(), 0.001);
        assertEquals(0.00, restaurant.calculateTotalTips(), 0.001);
        assertEquals(0.00, restaurant.calculateTotalCash(), 0.001);
    }

    @Test
    void shouldRejectNullOrderOnUpdate() {
        assertThrows(IllegalArgumentException.class, () -> restaurant.updateTransaction(null));
    }

    @Test
    void shouldNotRestoreTransactionWhenCanceledOrderIsUpdated() {
        Order order = restaurant.createOrder("Phoebe");

        restaurant.addMenuItemToOrder(order, 5, List.of(), false);
        restaurant.registerTransaction(order, 5);
        restaurant.cancelOrder(order.getNumber());
        restaurant.updateTransaction(order);

        assertEquals(0.00, restaurant.calculateTotalSales(), 0.001);
        assertEquals(0.00, restaurant.calculateTotalTips(), 0.001);
        assertEquals(0.00, restaurant.calculateTotalCash(), 0.001);
    }

    @Test
    void shouldReturnDistinctBaseNames() {
        Order order = restaurant.createOrder("Joey");

        restaurant.addMenuItemToOrder(order, 5, List.of(), false);
        restaurant.addMenuItemToOrder(order, 5, List.of("Pepperoni"), false);

        assertEquals(1, restaurant.getDistinctBaseNames().size());
    }

    @Test
    void shouldReturnDistinctItemDescriptions() {
        Order order = restaurant.createOrder("Monica");

        restaurant.addMenuItemToOrder(order, 5, List.of(), false);
        restaurant.addMenuItemToOrder(order, 5, List.of("Pepperoni"), false);

        assertFalse(restaurant.getDistinctItemDescriptions().isEmpty());
    }
}