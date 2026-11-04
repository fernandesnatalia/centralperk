package br.com.menu.decorator;

import br.com.menu.Food;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WithExtraIngredientTest {

    @Test
    void shouldAddExtraIngredientToDescription() {
        Food pizza = new Food("Pizza do Joey", 32);

        WithExtraIngredient item = new WithExtraIngredient(pizza, "Pepperoni", 5);

        assertEquals("Pizza do Joey + Pepperoni", item.getDescription());
    }

    @Test
    void shouldAddExtraPriceToOriginalPrice() {
        Food pizza = new Food("Pizza do Joey", 32);

        WithExtraIngredient item = new WithExtraIngredient(pizza, "Pepperoni", 5);

        assertEquals(37.00, item.getPrice());
    }

    @Test
    void shouldPreserveBaseName() {
        Food pizza = new Food("Pizza do Joey", 32);

        WithExtraIngredient item = new WithExtraIngredient(pizza, "Pepperoni", 5);

        assertEquals("Pizza do Joey", item.getBaseName());
    }

    @Test
    void shouldSupportMultipleDecorators() {
        Food pizza = new Food("Pizza do Joey", 32);

        WithExtraIngredient first = new WithExtraIngredient(pizza, "Pepperoni", 5);
        WithExtraIngredient second = new WithExtraIngredient(first, "Queijo", 3);

        assertEquals("Pizza do Joey + Pepperoni + Queijo", second.getDescription());
        assertEquals(40.00, second.getPrice());
    }

    @Test
    void shouldPreserveOriginalPriceWhenExtraPriceIsZero() {
        Food pizza = new Food("Pizza do Joey", 32);

        WithExtraIngredient item = new WithExtraIngredient(pizza, "Azeitona", 0);

        assertEquals(32.00, item.getPrice(), 0.001);
    }
}