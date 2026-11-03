package br.com.financial;

import br.com.order.Order;
import br.com.utils.Constants;
import java.util.HashSet;
import java.util.Set;

public class CashRegister {

    private final Set<Transaction> transactions = new HashSet<>();

    public void registerTransaction(Order order, double tipAmount) {
        Transaction transaction = new Transaction(order.getNumber(), order.calculateTotal(), tipAmount);
        transactions.add(transaction);
    }

    public void updateTransaction(Order order) {
        if (order == null) {
            throw new IllegalArgumentException(Constants.INVALID_NULL_VALUE);
        }

        Transaction current = findTransaction(order.getNumber());

        if (current == null) { return; }

        if (order.getItems().isEmpty() || order.calculateTotal() <= 0) {
            removeTransaction(order.getNumber());
            return;
        }

        transactions.remove(current);
        transactions.add(new Transaction(order.getNumber(), order.calculateTotal(), current.getTipAmount()));
    }

    public boolean removeTransaction(int orderNumber) {
        return transactions.removeIf(transaction -> transaction.getOrderNumber() == orderNumber);
    }

    private Transaction findTransaction(int orderNumber) {
        for (Transaction transaction : transactions) {
            if (transaction.getOrderNumber() == orderNumber) {
                return transaction;
            }
        }
        return null;
    }

    public double getTipAmount(int orderNumber) {
        Transaction transaction = findTransaction(orderNumber);
        return transaction != null ? transaction.getTipAmount() : 0;
    }

    public double calculateTotalSales() {
        double total = 0;
        for (Transaction transaction : transactions) {
            total += transaction.getOrderAmount();
        }
        return total;
    }

    public double calculateTotalTips() {
        double total = 0;
        for (Transaction transaction : transactions) {
            total += transaction.getTipAmount();
        }
        return total;
    }

    public double calculateTotalCash() {
        return calculateTotalSales() + calculateTotalTips();
    }
}
