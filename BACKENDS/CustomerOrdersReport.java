public class CustomerOrdersReport {
    private int customerId;
    private String customerName;
    private int deliveries;
    private int pickups;
    private double totalTransactionAmount;

    public CustomerOrdersReport(int customerId, String customerName,
                                int deliveries, int pickups,
                                double totalTransactionAmount) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.deliveries = deliveries;
        this.pickups = pickups;
        this.totalTransactionAmount = totalTransactionAmount;
    }

    // Getters
    public int getCustomerId() { return customerId; }
    public String getCustomerName() { return customerName; }
    public int getDeliveries() { return deliveries; }
    public int getPickups() { return pickups; }
    public double getTotalTransactionAmount() { return totalTransactionAmount; }

    // Optional: toString for debugging
    @Override
    public String toString() {
        return customerName + " (ID: " + customerId + ") - Deliveries: " + deliveries +
               ", Pickups: " + pickups + ", Total: " + totalTransactionAmount;
    }
}
