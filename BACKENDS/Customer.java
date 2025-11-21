public class Customer {
    private int customerId;
    private String lastName;
    private String firstName;
    private String address;

    public Customer(int customerId, String lastname, String firstName, String address){
        this.customerId = customerId;
        this.lastName = lastname;
        this.firstName = firstName;
        this.address = address;
    }

    // getterz
    public int getCustomerId() {
        return customerId;
    }
    public String getLastName() {
        return lastName;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getAddress() {
        return address;
    }

}
