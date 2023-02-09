package dat3.car.service;


import dat3.car.dto.CarRequest;
import dat3.car.dto.CarResponse;
import dat3.car.dto.MemberRequest;
import dat3.car.dto.MemberResponse;
import dat3.car.entity.Car;
import dat3.car.entity.Member;
import dat3.car.repository.CarRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    CarRepository carRepository;

    public List<CarResponse> getCars(boolean includeAll) {

        List<Car> cars = carRepository.findAll();
        List<CarResponse>carResponses = cars.stream().map(c->new CarResponse(c,includeAll)).toList();

        return carResponses;

    }

    public CarResponse findCarById(int id) {
        Car c = carRepository.findById(id).orElseThrow( ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Member with this ID does not exist"));
        CarResponse carResponse = new CarResponse(c, true);
        return carResponse;
    }


    public CarResponse addCar(CarRequest carRequest) {
        //Later you should add error checks --> Missing arguments, email taken etc.
        Car newCar = CarRequest.getCarEntity(carRequest);
        newCar = carRepository.save(newCar);

        return new CarResponse(newCar,false);
    }


    public ResponseEntity<Boolean> editCar(CarRequest body, int id) {
        Car editedCar = carRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member with this ID does not exist"));

        editedCar.setBestDiscount(body.getBestDiscount());
        editedCar.setBrand(body.getBrand());
        editedCar.setModel(body.getModel());
        editedCar.setPricePrDay(body.getPricePrDay());
        carRepository.save(editedCar);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }


    public CarResponse setDiscount(int id, int value) {

        Optional<Car> c = carRepository.findById(id);
        Car car = c.orElse(null);
        car.setBestDiscount(value);
        carRepository.save(car);
        CarResponse carResponse = new CarResponse(car,true);
        return carResponse;
    }

    public void deleteCarById(int id) {

        Optional<Car> c = carRepository.findById(id);
        Car car = c.orElse(null);
        carRepository.delete(car);
    }
}
