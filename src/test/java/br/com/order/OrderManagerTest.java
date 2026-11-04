package br.com.order;

import br.com.menu.MenuFactory;
import br.com.menu.MenuItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class OrderManagerTest {

    private OrderManager orderManager;

    @BeforeEach
    void setUp() {
        orderManager = new OrderManager();
    }

    @Test
    void shouldReturnEmptyCombinationsWhenThereAreNoOrders() {
        Set<String> combinations = orderManager.getDistinctItemDescriptions();
        assertTrue(combinations.isEmpty());
    }

    @Test
    void shouldCountSameItemOnlyOnce() {
        Order order = orderManager.createOrder("Rachel");

        MenuItem item1 = MenuFactory.createCustomizedItem(5, List.of(), false);
        MenuItem item2 = MenuFactory.createCustomizedItem(5, List.of(), false);

        order.addItem(item1);
        order.addItem(item2);

        Set<String> combinations = orderManager.getDistinctItemDescriptions();

        assertEquals(1, combinations.size());
        assertTrue(combinations.contains("Pizza do Joey"));
    }

    @Test
    void shouldCountDifferentExtrasAsDifferentCombinations() {
        Order order = orderManager.createOrder("Joey");

        MenuItem pizzaWithoutExtras = MenuFactory.createCustomizedItem(5, List.of(), false);
        MenuItem pizzaWithPepperoni = MenuFactory.createCustomizedItem(5, List.of("Pepperoni"), false);

        order.addItem(pizzaWithoutExtras);
        order.addItem(pizzaWithPepperoni);

        Set<String> combinations = orderManager.getDistinctItemDescriptions();

        assertEquals(2, combinations.size());

        assertTrue(combinations.contains("Pizza do Joey"));
        assertTrue(combinations.contains("Pizza do Joey + Pepperoni"));
    }

    @Test
    void shouldCountMultipleDifferentExtrasAsDifferentCombinations() {
        Order order = orderManager.createOrder("Monica");

        MenuItem item1 = MenuFactory.createCustomizedItem(1, List.of("Creme"), false);
        MenuItem item2 = MenuFactory.createCustomizedItem(1, List.of("Banana"), false);
        MenuItem item3 = MenuFactory.createCustomizedItem(1, List.of("Creme", "Banana"), false);

        order.addItem(item1);
        order.addItem(item2);
        order.addItem(item3);

        Set<String> combinations = orderManager.getDistinctItemDescriptions();

        assertEquals(3, combinations.size());
        assertEquals(3, combinations.size());

        assertTrue(combinations.contains("Trifle da Rachel + Creme"));
        assertTrue(combinations.contains("Trifle da Rachel + Banana"));
        assertTrue(combinations.contains("Trifle da Rachel + Creme + Banana"));
    }

    @Test
    void shouldCountBaseItemsWithoutConsideringExtras() {
        Order order = orderManager.createOrder("Phoebe");
        order.addItem(MenuFactory.createCustomizedItem(5, List.of(), false));
        order.addItem(MenuFactory.createCustomizedItem(5, List.of("Pepperoni"), false));
        order.addItem(MenuFactory.createCustomizedItem(1, List.of("Creme"), false));

        Set<String> baseNames = orderManager.getDistinctBaseNames();

        assertEquals(2, baseNames.size());

        assertTrue(baseNames.contains("Pizza do Joey"));
        assertTrue(baseNames.contains("Trifle da Rachel"));
    }

    @Test
    void shouldNotCountCanceledOrders() {
        Order order = orderManager.createOrder("Ross");
        order.addItem(MenuFactory.createCustomizedItem(5, List.of(), false));

        assertTrue(orderManager.cancelOrder(order.getNumber()));

        assertTrue(orderManager.getDistinctItemDescriptions().isEmpty());
        assertTrue(orderManager.getDistinctBaseNames().isEmpty());
    }

    @Test
    void shouldKeepOrderInHistoryAfterCancellation() {
        Order order = orderManager.createOrder("Chandler");

        orderManager.cancelOrder(order.getNumber());

        assertNotNull(orderManager.findOrder(order.getNumber()));
        assertTrue(orderManager.findOrder(order.getNumber()).isCanceled());
    }
}
