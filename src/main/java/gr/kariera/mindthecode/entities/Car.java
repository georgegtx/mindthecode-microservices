package gr.kariera.mindthecode.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import gr.kariera.mindthecode.shared.CarDto;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private int mileage;

    @NotBlank(message = "Car Maker is required")
    private String maker;

    @Pattern(regexp = "sn-\\d{0,17}\\Z", message = "Serial number must be in the format sn-[7 digits]")
    private String serialNumber;

    @OneToOne()
    @JoinColumn(name = "engine_id")
    @JsonManagedReference
    private Engine engine;

    public Car(){ }

    public Car(int mileage, String maker, String serialNumber) {
        this.mileage = mileage;
        this.maker = maker;
        this.serialNumber = serialNumber;
    }

    public Car(int mileage, String maker, String serialNumber, Engine engine) {
        this.mileage = mileage;
        this.maker = maker;
        this.serialNumber = serialNumber;
        this.engine = engine;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Long getId() {
        return id;
    }

    public int getMileage() {
        return mileage;
    }


    public String getMaker() {
        return maker;
    }


    public String getSerialNumber() {
        return serialNumber;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public static Optional<CarDto> toDto(Optional<Car> car) {
        return Optional.ofNullable(car.isEmpty() ? null : new CarDto(car.get().getId(), car.get().getMileage(), car.get().getMaker(), car.get().getSerialNumber()));
    }
    public static CarDto toDto(Car car) {
        return new CarDto(car.getId(), car.getMileage(), car.getMaker(), car.getSerialNumber());
    }

    public static List<CarDto> toDto(List<Car> cars) {
        return cars.stream().map(c -> Car.toDto(c)).collect(Collectors.toList());
    }

    public static Car fromDto(CarDto carDto) {
        Car car = new Car(carDto.getMileage(), carDto.getMaker(), carDto.getSerialNumber());
        car.setId(carDto.getId());
        return car;
    }
}
