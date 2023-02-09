package dat3.car.api;


import dat3.car.dto.CarRequest;
import dat3.car.dto.CarResponse;
import dat3.car.dto.MemberRequest;
import dat3.car.dto.MemberResponse;
import dat3.car.service.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/cars")
public class CarController {

    CarService carService;

    public CarController(CarService carService){
        this.carService =  carService;
    }

    //ADMIN ONLY
    @GetMapping
    List<CarResponse> getCars(){
        return carService.getCars(false);
    }

    //ADMIN ONLY
    @GetMapping(path="/{id}")
    CarResponse getMemberById(@PathVariable int id) throws Exception {
        return carService.findCarById(id);
    }

    //ANONYMOUS
    //@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping
    CarResponse addCar(@RequestBody CarRequest body){
        return carService.addCar(body);
    }

    //MEMBER
    @PutMapping("/{id}")
    public ResponseEntity<Boolean> editCar(@PathVariable int id, @RequestBody CarRequest body) {

        return carService.editCar(body, id);
    }

    //ADMIN ONLY
    @PatchMapping("/discount/{id}/{value}")
    public CarResponse setDiscount(@PathVariable int id, @PathVariable int value) {
        return carService.setDiscount(id, value);
    }

    // ADMIN ONLY
    @DeleteMapping("/{id}")
    void deleteCarById(@PathVariable int id) {
        carService.deleteCarById(id);
    }



}
