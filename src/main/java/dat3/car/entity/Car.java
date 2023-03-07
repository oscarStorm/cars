package dat3.car.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@NoArgsConstructor
@Setter
@Getter

@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "car_brand")
    private String brand;
    @Column(name = "car_model")
    private String model;
    @Column(name = "rental_price_day")
    private double pricePrDay;
    @Column(name = "max_discount")
    private Integer bestDiscount;
    @Column
    @CreationTimestamp
    private LocalDateTime created;
    @Column
    @UpdateTimestamp
    private LocalDateTime lastEdited;

    public Car(String brand, String model, double pricePrDay, Integer bestDiscount){
        this.brand = brand;
        this.model = model;
        this.pricePrDay = pricePrDay;
        this.bestDiscount = bestDiscount;
    }
    @OneToMany(mappedBy = "car",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    Set<Reservation> reservations = new HashSet<>();

    public void addReservation(Reservation r){
        if(reservations ==null){
            reservations =new HashSet<>();
        }
        reservations.add(r);
        r.setCar(this);
    }

    public boolean checkDate(LocalDate date){
        Iterator<Reservation> iterator = reservations.iterator();
        while (iterator.hasNext()){
            Reservation reservation = iterator.next();
            if(reservation.date.equals(date)){
                System.out.println("Date already exists");
                return true;
            }
        }
        return false;
    }

}
