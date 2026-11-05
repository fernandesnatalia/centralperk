package br.com.menu.decorator;

import br.com.menu.Food;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WithExtraIngredientTest {

    private Food createFood() {
        return new Food("Pizza do Joey", 32);
    }

    @Test
    void shouldAddExtraIngredientToDescription() {
        WithExtraIngredient item = new WithExtraIngredient(createFood(), "Pepperoni", 5);

        assertEquals("Pizza do Joey + Pepperoni", item.getDescription());
    }

    @Test
    void shouldAddExtraPriceToOriginalPrice() {
        WithExtraIngredient item = new WithExtraIngredient(createFood(), "Pepperoni", 5);

        assertEquals(37.00, item.getPrice(), 0.001);
    }

    @Test
    void shouldPreserveBaseName() {
        WithExtraIngredient item = new WithExtraIngredient(createFood(), "Pepperoni", 5);

        assertEquals("Pizza do Joey", item.getBaseName());
    }

    @Test
    void shouldSupportMultipleDecorators() {
        WithExtraIngredient first = new WithExtraIngredient(createFood(), "Pepperoni", 5);
        WithExtraIngredient second = new WithExtraIngredient(first, "Queijo", 3);

        assertEquals("Pizza do Joey + Pepperoni + Queijo", second.getDescription());
        assertEquals(40.00, second.getPrice(),0.001);
    }

    @Test
    void shouldPreserveOriginalPriceWhenExtraPriceIsZero() {
        WithExtraIngredient item = new WithExtraIngredient(createFood(), "Azeitona", 0);

        assertEquals(32.00, item.getPrice(), 0.001);
    }
}