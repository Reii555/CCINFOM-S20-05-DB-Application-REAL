public class CustomerPreferenceReport {
    private int customerId;
    private String firstName;
    private String lastName;
    private int deliveryCount;
    private int pickupCount;
    private String preferredMethod;

    public CustomerPreferenceReport(int customerId, String firstName, String lastName, int deliveryCount, int pickupCount, String preferredMethod) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.deliveryCount = deliveryCount;
        this.pickupCount = pickupCount;
        this.preferredMethod = preferredMethod;
    }

    // getters
    public int getCustomerId() {
        return customerId;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public int getDeliveryCount() {
        return deliveryCount;
    }
    public int getPickupCount() {
        return pickupCount;
    }
    public String getPreferredMethod() {
        return preferredMethod;
    }
}
