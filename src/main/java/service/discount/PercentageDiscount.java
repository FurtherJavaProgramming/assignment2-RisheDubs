package service.discount;

public class PercentageDiscount implements DiscountStrategy {
    private double percentage;

    public PercentageDiscount(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public double applyDiscount(double totalPrice) {
        return totalPrice - (totalPrice * (percentage / 100));
    }
}
