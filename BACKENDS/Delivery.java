import java.sql.Date;
import java.sql.Time;

public class Delivery {
     private int deliveryId;
    private int orderId;
    private String deliveryType;
    private String deliveryStatus;
    private int driverId;
    private String payment;
    private Time estDeliveryTime;
    private Time actDeliveryTime;
    private double deliveryFee;
    private Date deliveryDate;

    //constructor
    public Delivery(int deliveryId, int orderId, String deliveryType, String deliveryStatus,
                    int driverId, String payment, Time estDeliveryTime, Time actDeliveryTime,
                    double deliveryFee, Date deliveryDate) {
        this.deliveryId = deliveryId;
        this.orderId = orderId;
        this.deliveryType = deliveryType;
        this.deliveryStatus = deliveryStatus;
        this.driverId = driverId;
        this.payment = payment;
        this.estDeliveryTime = estDeliveryTime;
        this.actDeliveryTime = actDeliveryTime;
        this.deliveryFee = deliveryFee;
        this.deliveryDate = deliveryDate;
    }

    //getters
    public int getDeliveryId() {
        return deliveryId;
    }
    public int getOrderId() {
        return orderId;
    }
    public String getDeliveryType() {
        return deliveryType;
    }
    public String getDeliveryStatus() {
        return deliveryStatus;
    }
    public int getDriverId() {
        return driverId;
    }
    public String getPayment() {
        return payment;
    }
    public Time getEstDeliveryTime() {
        return estDeliveryTime;
    }
    public Time getActDeliveryTime() {
        return actDeliveryTime;
    }
    public double getDeliveryFee() {
        return deliveryFee;
    }
    public Date getDeliveryDate() {
        return deliveryDate;
    }

    //setters
    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }
    public void setActDeliveryTime(Time actDeliveryTime) {
        this.actDeliveryTime = actDeliveryTime;
    }
    public void setPayment(String payment) {
        this.payment = payment;
    }
}
