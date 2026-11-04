package br.com.financial;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    @Test
    void shouldCreateTransactionSuccessfully() {
        Transaction transaction = new Transaction(1, 50, 10);

        assertEquals(1, transaction.getOrderNumber());
        assertEquals(50, transaction.getOrderAmount());
        assertEquals(10, transaction.getTipAmount());
    }

    @Test
    void shouldRejectInvalidOrderNumber() {
        assertThrows(IllegalArgumentException.class,
                () -> new Transaction(0, 50, 10)
        );
    }

    @Test
    void shouldRejectNegativeOrderAmount() {
        assertThrows(IllegalArgumentException.class,
                () -> new Transaction(1, -50, 10)
        );
    }

    @Test
    void shouldRejectNegativeTipAmount() {
        assertThrows(IllegalArgumentException.class,
                () -> new Transaction(1, 50, -10)
        );
    }

    @Test
    void shouldAcceptZeroValues() {
        Transaction transaction = new Transaction(1, 0, 0);

        assertEquals(0, transaction.getOrderAmount());
        assertEquals(0, transaction.getTipAmount());
    }

    @Test
    void shouldConsiderTransactionsWithSameOrderNumberEqual() {
        Transaction first = new Transaction(1, 50, 10);
        Transaction second = new Transaction(1, 100, 20);

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void shouldNotConsiderDifferentOrdersEqual() {
        Transaction first = new Transaction(1, 50, 10);
        Transaction second = new Transaction(2, 50, 10);

        assertNotEquals(first, second);
    }
}
