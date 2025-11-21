public class Driver {
    private int driverId;
    private String lastName;
    private String firstName;
    private long contact;
    private String licence;
    private String status;
    private String shift;

    public Driver(int driverId, String lastName, String firstName, long contact, 
                  String licence, String status, String shift) {
        this.driverId = driverId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.contact = contact;
        this.licence = licence;
        this.status = status;
        this.shift = shift;
    }

    public int getDriverId() { 
        return driverId; 
    }
    
    public String getLastName() { 
        return lastName; 
    }

    public String getFirstName() { 
        return firstName; 
    }

    public long getContact() { 
        return contact; 
    }

    public String getLicence() { 
        return licence; 
    }

    public String getStatus() { 
        return status; 
    }

    public String getShift() { 
        return shift; 
    }

    public void setStatus(String status) { 
        this.status = status; 
    }

    public void setShift(String shift) { 
        this.shift = shift; 
    }
}