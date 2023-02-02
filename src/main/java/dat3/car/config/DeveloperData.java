package dat3.car.config;

import dat3.car.entity.Car;
import dat3.car.repository.CarRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeveloperData  implements ApplicationRunner{
    @Override
    public void run(ApplicationArguments args) throws Exception {

        Car car = new Car("jaguar", "sport", 500,60);
        Car car1 = new Car("toyota", "lort", 100, 20);
        Car car2 = new Car("Mitshubishi", "enigma", 400, 200);
        carRepository.save(car);
        carRepository.save(car1);
        carRepository.save(car2);

    }
    private CarRepository carRepository;

    public DeveloperData(CarRepository carRepository){
        this.carRepository = carRepository;
    }








}
