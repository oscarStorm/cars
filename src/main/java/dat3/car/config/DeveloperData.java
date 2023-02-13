package dat3.car.config;

import dat3.car.entity.Car;
import dat3.car.entity.Member;
import dat3.car.repository.CarRepository;
import dat3.car.repository.MemberRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDateTime;

//opdateret CI/CD

@Configuration
public class DeveloperData  implements ApplicationRunner{

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Car car = new Car("jaguar", "sport", 500, 60);
        Car car1 = new Car("toyota", "lort", 100, 20);
        Car car2 = new Car("Mitshubishi", "enigma", 400, 200);

        carRepository.save(car);
        carRepository.save(car1);
        carRepository.save(car2);

        Member m1 = new Member("Dennis", "123", "dennis@gmail.com", "Dennissen", "Gormsen", "Frejsvej", "Ishøj", "2670" );
        Member m2 = new Member("Jon", "1321", "jon@gmail.com", "jon", "helgesen", "nørrebrogade", "københavn", "2200" );
        Member m3 = new Member("kasper", "1241", "kasper@gmail.com", "kasper", "tomsen", "Rolighedsvej", "frederiksberg", "1958" );

        memberRepository.save(m1);
        memberRepository.save(m2);
        memberRepository.save(m3);

    }
    
    private CarRepository carRepository;
    private MemberRepository memberRepository;

    public DeveloperData(CarRepository carRepository, MemberRepository memberRepository){
        this.carRepository = carRepository;
        this.memberRepository = memberRepository;
    }
}
