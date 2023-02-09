package dat3.car.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dat3.car.entity.Car;



import java.time.LocalDateTime;

public class CarResponse {

    String brand;
    String model;
    double pricePrDay;
    Integer bestDiscount;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss",shape = JsonFormat.Shape.STRING)
    LocalDateTime created;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss",shape = JsonFormat.Shape.STRING)
    LocalDateTime edited;

    public CarResponse(Car c, boolean includeAll){
        this.brand = c.getBrand();
        this.model = c.getModel();
        this.pricePrDay = c.getPricePrDay();
        this.bestDiscount = c.getBestDiscount();
        if(includeAll){
            this.created = c.getCreated();
            this.edited = c.getLastEdited();
        }
    }





}
