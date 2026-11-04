package br.com.order;

import br.com.menu.Food;
import br.com.menu.MenuFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order(1, "Joey");
    }

    @Test
    void shouldCreateOrderSuccessfully() {
        assertEquals(1, order.getNumber());
        assertEquals("Joey", order.getCustomerName());
        assertTrue(order.getItems().isEmpty());
        assertFalse(order.isCanceled());
    }

    @Test
    void shouldRejectInvalidOrderNumber() {
        assertThrows(IllegalArgumentException.class, () -> new Order(0, "Joey"));
    }

    @Test
    void shouldRejectNullCustomerName() {
        assertThrows(IllegalArgumentException.class, () -> new Order(1, null));
    }

    @Test
    void shouldRejectEmptyCustomerName() {
        assertThrows(IllegalArgumentException.class, () -> new Order(1, " "));
    }

    @Test
    void shouldAddItemSuccessfully() {
        order.addItem(new Food("Pizza", 32));
        assertEquals(1, order.getItems().size());
    }

    @Test
    void shouldRejectNullItem() {
        assertThrows(IllegalArgumentException.class, () -> order.addItem(null));
    }

    @Test
    void shouldCalculateTotalCorrectly() {
        order.addItem(new Food("Pizza", 32));
        order.addItem(new Food("Cake", 18));

        assertEquals(50.00, order.calculateTotal());
    }

    @Test
    void shouldKeepOrderTotalUnchangedAfterCancellation() {
        order.addItem(new Food("Pizza", 32));
        order.cancel();

        assertEquals(32.00, order.calculateTotal());
    }

    @Test
    void shouldPreservePriceWhenNoAdditionalIngredientIsApplied() {
        Food pizza = new Food("Pizza", 32);

        assertEquals(32.00, pizza.getPrice());
    }

    @Test
    void shouldCalculateTotalWithCustomizedItems() {
        Order order = new Order(1, "Joey");

        order.addItem(
                MenuFactory.createCustomizedItem(
                        5,
                        List.of("Pepperoni"),
                        true
                )
        );

        assertEquals(42.00, order.calculateTotal());
    }

    @Test
    void shouldRemoveItemSuccessfully() {
        order.addItem(new Food("Pizza", 32));

        assertTrue(order.removeItem(0));
        assertTrue(order.getItems().isEmpty());
    }

    @Test
    void shouldRejectInvalidItemIndex() {
        order.addItem(new Food("Pizza", 32));

        assertFalse(order.removeItem(-1));
        assertFalse(order.removeItem(1));
    }

    @Test
    void shouldReturnCopyOfItems() {
        order.addItem(new Food("Pizza", 32));

        var items = order.getItems();
        items.clear();

        assertEquals(1, order.getItems().size());
    }

    @Test
    void shouldCancelOrder() {
        order.cancel();
        assertTrue(order.isCanceled());
    }

    @Test
    void shouldNotAllowAddingItemsToCanceledOrder() {
        order.cancel();

        assertThrows(IllegalArgumentException.class, () -> order.addItem(new Food("Pizza", 32)));
    }

    @Test
    void shouldNotAllowRemovingItemsFromCanceledOrder() {
        order.addItem(new Food("Pizza", 32));
        order.cancel();

        assertFalse(order.removeItem(0));
    }
}
