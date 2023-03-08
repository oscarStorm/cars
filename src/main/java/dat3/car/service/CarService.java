package dat3.car.service;


import dat3.car.dto.CarRequest;
import dat3.car.dto.CarResponse;
import dat3.car.dto.MemberRequest;
import dat3.car.dto.MemberResponse;
import dat3.car.entity.Car;
import dat3.car.entity.Member;
import dat3.car.repository.CarRepository;
import dat3.car.repository.ReservationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    CarRepository carRepository;
    ReservationRepository reservationRepository;

    public CarService(CarRepository carRepository, ReservationRepository reservationRepository){
        this.carRepository = carRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<CarResponse> getCars(boolean includeAll) {

        List<Car> cars = carRepository.findAll();
        List<CarResponse>carResponses = cars.stream().map(c->new CarResponse(c,includeAll)).toList();

        return carResponses;

    }

    public Car findCar(int id){

        carRepository.findById(String.valueOf(id)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Car with this ID doesnt exist"));
        Optional<Car> c = carRepository.findById(String.valueOf(id));
        Car car = c.orElse(null);

        return car;
    }


    public CarResponse findCarById(int id) {

        Car car = findCar(id);
        CarResponse carRepsonse = new CarResponse(car,true);

        return carRepsonse;
    }


    public CarResponse addCar(CarRequest carRequest) {

        Car newCar = CarRequest.getCarEntity(carRequest);
        newCar = carRepository.save(newCar);

        return new CarResponse(newCar,true);
    }


    public void editCar(CarRequest body, int id) {

        Car carToEdit =  findCar(id);
        Optional.ofNullable(body.getBrand()).ifPresent(carToEdit::setBrand);
        Optional.ofNullable(body.getModel()).ifPresent(carToEdit::setModel);
        Optional.ofNullable(body.getPricePrDay()).ifPresent(carToEdit::setPricePrDay);
        carRepository.save(carToEdit);
    }


    public CarResponse setDiscount(int id, int value) {

        Car car = findCar(id);
        car.setBestDiscount(value);
        carRepository.save(car);
        return new CarResponse(car,true);
    }

    public void deleteCarById(int id) {

        Car car = findCar(id);
        carRepository.delete(car);
    }

}
