package br.com.financial;

import br.com.menu.Food;
import br.com.order.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CashRegisterTest {

    private CashRegister cashRegister;

    @BeforeEach
    void setUp() {
        cashRegister = new CashRegister();
    }

    private Order createOrder() {
        Order order = new Order(1, "Joey");
        order.addItem(new Food("Pizza", 32));
        return order;
    }

    @Test
    void shouldRegisterTransaction() {
        Order order = createOrder();

        cashRegister.registerTransaction(order, 5);

        assertEquals(32, cashRegister.calculateTotalSales());
        assertEquals(5, cashRegister.calculateTotalTips());
        assertEquals(37, cashRegister.calculateTotalCash());
    }

    @Test
    void shouldRegisterTransactionWithoutTip() {
        Order order = createOrder();

        cashRegister.registerTransaction(order, 0);

        assertEquals(32.00, cashRegister.calculateTotalSales());
        assertEquals(0.00, cashRegister.calculateTotalTips());
        assertEquals(32.00, cashRegister.calculateTotalCash());
    }

    @Test
    void shouldReturnZeroWhenThereAreNoTransactions() {
        assertEquals(0, cashRegister.calculateTotalSales());
        assertEquals(0, cashRegister.calculateTotalTips());
        assertEquals(0, cashRegister.calculateTotalCash());
    }

    @Test
    void shouldNotDuplicateTransactionForSameOrder() {
        Order order = createOrder();

        cashRegister.registerTransaction(order, 5);
        cashRegister.registerTransaction(order, 5);

        assertEquals(32.00, cashRegister.calculateTotalSales());
        assertEquals(5.00, cashRegister.calculateTotalTips());
    }

    @Test
    void shouldReturnTipAmountForExistingTransaction() {
        cashRegister.registerTransaction(createOrder(), 8);
        assertEquals(8, cashRegister.getTipAmount(1));
    }

    @Test
    void shouldReturnZeroForNonexistentTransaction() {
        assertEquals(0, cashRegister.getTipAmount(99));
    }

    @Test
    void shouldRemoveTransaction() {
        cashRegister.registerTransaction(createOrder(), 5);

        assertTrue(cashRegister.removeTransaction(1));
        assertEquals(0, cashRegister.calculateTotalSales());
        assertEquals(0, cashRegister.calculateTotalTips());
    }

    @Test
    void shouldReturnFalseWhenRemovingNonexistentTransaction() {
        assertFalse(cashRegister.removeTransaction(99));
    }

    @Test
    void shouldUpdateTransactionAmountAndPreserveTip() {
        Order order = createOrder();

        cashRegister.registerTransaction(order, 5);
        order.addItem(new Food("Lasanha da Mônica", 18));
        cashRegister.updateTransaction(order);

        assertEquals(50, cashRegister.calculateTotalSales());
        assertEquals(5, cashRegister.calculateTotalTips());
        assertEquals(55, cashRegister.calculateTotalCash());
    }

    @Test
    void shouldRemoveTransactionWhenOrderBecomesEmpty() {
        Order order = createOrder();

        cashRegister.registerTransaction(order, 5);
        order.removeItem(0);
        cashRegister.updateTransaction(order);

        assertEquals(0, cashRegister.calculateTotalSales());
        assertEquals(0, cashRegister.calculateTotalTips());
        assertFalse(cashRegister.removeTransaction(1));
    }

    @Test
    void shouldIgnoreUpdateWhenTransactionDoesNotExist() {
        Order order = createOrder();
        cashRegister.updateTransaction(order);

        assertEquals(0, cashRegister.calculateTotalSales());
        assertEquals(0, cashRegister.calculateTotalTips());
        assertEquals(0, cashRegister.calculateTotalCash());
    }

    @Test
    void shouldRejectNullOrderOnUpdate() {
        assertThrows(IllegalArgumentException.class, () -> cashRegister.updateTransaction(null));
    }
}