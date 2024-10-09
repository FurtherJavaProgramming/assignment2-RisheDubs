package service.discount;

public class FixedAmountDiscount implements DiscountStrategy {
    private double discountAmount;

    public FixedAmountDiscount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    @Override
    public double applyDiscount(double totalPrice) {
        return totalPrice - discountAmount;
    }
}
