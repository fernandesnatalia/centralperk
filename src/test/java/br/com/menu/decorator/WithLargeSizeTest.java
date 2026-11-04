package br.com.menu.decorator;

import br.com.menu.Food;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WithLargeSizeTest {

    private WithLargeSize createLargePizza() {
        Food pizza = new Food("Pizza do Joey", 32);
        return new WithLargeSize(pizza, 5);
    }

    @Test
    void shouldIncreaseItemPrice() {
        WithLargeSize large = createLargePizza();

        assertEquals(37.00, large.getPrice());
    }

    @Test
    void shouldPreserveDescription() {
        WithLargeSize large = createLargePizza();

        assertEquals("Pizza do Joey", large.getDescription());
    }

    @Test
    void shouldPreserveBaseName() {
        WithLargeSize large = createLargePizza();

        assertEquals("Pizza do Joey", large.getBaseName());
    }

    @Test
    void shouldAllowCombiningLargeSizeAndExtras() {
        Food pizza = new Food("Pizza do Joey", 32);
        WithExtraIngredient extra = new WithExtraIngredient(pizza, "Pepperoni", 5);
        WithLargeSize large = new WithLargeSize(extra, 5);

        assertEquals(42.00, large.getPrice());
        assertEquals("Pizza do Joey + Pepperoni", large.getDescription());
    }
}