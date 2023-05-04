package volvo.com.tax.calculator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import volvo.com.tax.calculator.model.Car;
import volvo.com.tax.calculator.model.Motorbike;
import volvo.com.tax.calculator.model.Vehicle;
import volvo.com.tax.calculator.usecase.CongestionTaxCalculator;

@RestController
public class CongestionTaxController {

    @Autowired
    private CongestionTaxCalculator calculator;

    @PostMapping("/toll/fee")
    public TollFeeResponse getTollFee(@RequestBody TollFeeRequest request){
        Vehicle vehicle = getVehicle(request.getVehicle());
        return new TollFeeResponse(calculator.getTax(vehicle, request.getDates()));
    }

    private Vehicle getVehicle(String vehicle){
        return switch (vehicle) {
            case "Car" -> new Car();
            case "Motorbike" -> new Motorbike();
            default -> throw new IllegalStateException("Unexpected value: " + vehicle);
        };
    }

}
