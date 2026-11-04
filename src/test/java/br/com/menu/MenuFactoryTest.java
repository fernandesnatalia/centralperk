package br.com.menu;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class MenuFactoryTest {

    @Test
    void shouldCreateAllMenuItems() {
        for (int option = 1; option <= 9; option++) {
            MenuItem item = MenuFactory.createCustomizedItem(option, List.of(), false);
            assertNotNull(item);
            assertTrue(item.getPrice() > 0);
            assertFalse(item.getDescription().isBlank());
        }
    }

    @Test
    void shouldRejectInvalidMenuOption() {
        assertThrows(IllegalArgumentException.class,
                () -> MenuFactory.createCustomizedItem(10, List.of(), false)
        );
    }

    @Test
    void shouldCreateItemWithExtraIngredient() {
        MenuItem item = MenuFactory.createCustomizedItem(5, List.of("Pepperoni"), false);

        assertEquals("Pizza do Joey + Pepperoni", item.getDescription());
        assertEquals(37.00, item.getPrice());
    }

    @Test
    void shouldCreateItemWithMultipleExtras() {
        MenuItem item = MenuFactory.createCustomizedItem(1, List.of("Creme", "Banana"), false);

        assertEquals("Trifle da Rachel + Creme + Banana", item.getDescription());
        assertEquals(26.00, item.getPrice());
    }

    @Test
    void shouldCreateLargeItem() {
        MenuItem item = MenuFactory.createCustomizedItem(5, List.of(), true);

        assertEquals(37.00, item.getPrice());
        assertEquals("Pizza do Joey", item.getDescription());
    }

    @Test
    void shouldRejectLargeSizeForUnsupportedItem() {
        assertThrows(IllegalArgumentException.class,
                () -> MenuFactory.createCustomizedItem(1, List.of(), true)
        );
    }

    @Test
    void shouldRejectUnavailableExtra() {
        assertThrows(IllegalArgumentException.class,
                () -> MenuFactory.createCustomizedItem(5, List.of("Chocolate"), false)
        );
    }

    @Test
    void shouldReturnAvailableExtras() {
        Map<String, Double> extras = MenuFactory.getAvailableExtras(5);

        assertEquals(1, extras.size());
        assertEquals(5.00, extras.get("Pepperoni"));
    }

    @Test
    void shouldRejectInvalidExtrasOption() {
        assertThrows(IllegalArgumentException.class,
                () -> MenuFactory.getAvailableExtras(10)
        );
    }

    @Test
    void shouldIdentifyItemsThatSupportLargeSize() {
        assertTrue(MenuFactory.supportsLargeSize(5));

        assertFalse(MenuFactory.supportsLargeSize(1));
        assertFalse(MenuFactory.supportsLargeSize(2));
        assertFalse(MenuFactory.supportsLargeSize(3));
        assertFalse(MenuFactory.supportsLargeSize(4));
        assertFalse(MenuFactory.supportsLargeSize(6));
        assertFalse(MenuFactory.supportsLargeSize(7));
        assertFalse(MenuFactory.supportsLargeSize(8));
        assertFalse(MenuFactory.supportsLargeSize(9));
    }
}