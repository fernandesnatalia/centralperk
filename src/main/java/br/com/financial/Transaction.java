package br.com.financial;

import br.com.utils.Constants;

public class Transaction {

    private final int orderNumber;
    private final double orderAmount;
    private final double tipAmount;

    public Transaction(int orderNumber, double orderAmount, double tipAmount) {
        if (orderNumber <= 0) {
            throw new IllegalArgumentException(Constants.INVALID_ORDER_NUMBER);
        }

        if (orderAmount < 0 || tipAmount < 0) {
            throw new IllegalArgumentException(Constants.INVALID_VALUE);
        }

        this.orderNumber = orderNumber;
        this.orderAmount = orderAmount;
        this.tipAmount = tipAmount;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public double getTipAmount() {
        return tipAmount;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Transaction)) {
            return false;
        }

        Transaction transaction = (Transaction) object;

        return orderNumber == transaction.orderNumber;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(orderNumber);
    }
}
