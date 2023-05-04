package volvo.com.tax.calculator.controller;

public class TollFeeResponse {

    private Integer fee;

    public TollFeeResponse(Integer fee) {
        this.fee = fee;
    }

    public Integer getFee() {
        return fee;
    }

}
