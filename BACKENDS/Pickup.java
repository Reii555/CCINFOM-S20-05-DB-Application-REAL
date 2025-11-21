import java.sql.Date;

public class Pickup {
    private int orderId;
    private String orderType;
    private String status;
    private String pickupLocation;
    private Date pickupDate;
    private String pickupService;
    private String paymentMethod;
    private int customerId;

    // constructor !!
    public Pickup(int orderId, String orderType, String status, String pickupLocation,
                  Date pickupDate, String pickupService, String paymentMethod, int customerId) {
        this.orderId = orderId;
        this.orderType = orderType;
        this.status = status;
        this.pickupLocation = pickupLocation;
        this.pickupDate = pickupDate;
        this.pickupService = pickupService;
        this.paymentMethod = paymentMethod;
        this.customerId = customerId;
    }

    // getters !!
    public int getOrderId() {
        return orderId;
    }

    public String getOrderType() {
        return orderType;
    }

    public String getStatus() {
        return status;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public Date getPickupDate() {
        return pickupDate;
    }

    public String getPickupService() {
        return pickupService;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public int getCustomerId() {
        return customerId;
    }

    // setters if needed !!
    /*
     public void setStatus(String status) { 
        this.status = status 
    }
    */

    /*
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    */


    
}
