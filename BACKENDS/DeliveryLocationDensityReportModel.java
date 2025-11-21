public class DeliveryLocationDensityReportModel {
    private String location;
    private int totalCompleteDeliveries;
    private String month;
    private int year;
    private int rank;

    public DeliveryLocationDensityReportModel(){} //added diz since blank ung ganitey sa MainMenu.java

    
    public DeliveryLocationDensityReportModel(String location, int totalCompleteDeliveries, String month, int year, int rank) {
        this.location = location;
        this.totalCompleteDeliveries = totalCompleteDeliveries;
        this.month = month;
        this.year = year;
        this.rank = rank;
}

    // getterz
    public String getLocation() {
        return location;
    }
    public int getTotalCompleteDeliveries() {
        return totalCompleteDeliveries;
    }
    public String getMonth() {
        return month;
    }
    public int getYear() {
        return year;
    }
    public int getRank() {
        return rank;
    }

}
