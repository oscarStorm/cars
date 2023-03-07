package dat3.car.config;

import dat3.car.security.repository.UserWithRolesRepository;
import dat3.car.dto.ReservationRequest;
import dat3.car.entity.Reservation;
import dat3.car.entity.Car;
import dat3.car.entity.Member;
import dat3.car.security.repository.*;
import dat3.car.repository.CarRepository;
import dat3.car.repository.MemberRepository;
import dat3.car.repository.ReservationRepository;
import dat3.car.security.entity.Role;
import dat3.car.security.entity.UserWithRoles;
import dat3.car.service.ReservationService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDate;
import java.time.LocalDateTime;

//opdateret CI/CD
@EnableJpaRepositories(basePackages = {"dat3.car.security.repository","dat3.car.repository"})
@Configuration
public class DeveloperData  implements ApplicationRunner{

    final String passwordUsedByAll = "12";

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

        System.out.println("----------Reservationdate----------");
        Reservation r1 = Reservation.builder().member(m1).car(car1).date(LocalDate.now()).build();
        Reservation r2 = Reservation.builder().member(m2).car(car1).date(LocalDate.now()).build();

        //reservationDateRepository.save(r1);
        ReservationRequest reservationRequest1 = new ReservationRequest(r1);
        ReservationRequest reservationRequest2 = new ReservationRequest(r2);

        reservationService.makeReservation(reservationRequest1);
        reservationService.makeReservation(reservationRequest2);


        setupUserWithRoleUsers();


    }
    
    private CarRepository carRepository;
    private MemberRepository memberRepository;
    ReservationRepository reservationRepository;
    ReservationService reservationService;
    UserWithRolesRepository userWithRolesRepository;


    public DeveloperData(CarRepository carRepository, MemberRepository memberRepository, ReservationRepository reservationRepository, ReservationService reservationService, UserWithRolesRepository userWithRolesRepository){
        this.carRepository = carRepository;
        this.memberRepository = memberRepository;
        this.reservationRepository = reservationRepository;
        this.reservationService = reservationService;
        this.userWithRolesRepository = userWithRolesRepository;

    }
    private void setupUserWithRoleUsers() {

        System.out.println("******************************************************************************");
        System.out.println("******* NEVER  COMMIT/PUSH CODE WITH DEFAULT CREDENTIALS FOR REAL ************");
        System.out.println("******* REMOVE THIS BEFORE DEPLOYMENT, AND SETUP DEFAULT USERS DIRECTLY  *****");
        System.out.println("**** ** ON YOUR REMOTE DATABASE                 ******************************");
        System.out.println("******************************************************************************");
        UserWithRoles user1 = new UserWithRoles("user1", passwordUsedByAll, "user1@a.dk");
        UserWithRoles user2 = new UserWithRoles("user2", passwordUsedByAll, "user2@a.dk");
        UserWithRoles user3 = new UserWithRoles("user3", passwordUsedByAll, "user3@a.dk");
        UserWithRoles user4 = new UserWithRoles("user4", passwordUsedByAll, "user4@a.dk");
        user1.addRole(dat3.car.security.entity.Role.USER);
        user1.addRole(Role.ADMIN);
        user2.addRole(Role.USER);
        user3.addRole(Role.ADMIN);
        //No Role assigned to user4
        userWithRolesRepository.save(user1);
        userWithRolesRepository.save(user2);
        userWithRolesRepository.save(user3);
        userWithRolesRepository.save(user4);
    }






}
