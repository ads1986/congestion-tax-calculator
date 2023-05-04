package volvo.com.tax.calculator.controller;

public class TollFeeRequest {

    private String vehicle;
    private String[] dates;

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String[] getDates() {
        return dates;
    }

    public void setDates(String[] dates) {
        this.dates = dates;
    }
}
