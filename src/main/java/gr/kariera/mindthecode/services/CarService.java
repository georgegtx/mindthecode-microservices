package gr.kariera.mindthecode.services;

import gr.kariera.mindthecode.entities.Car;
import gr.kariera.mindthecode.repositories.CarRepository;
import gr.kariera.mindthecode.shared.CarDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    @Autowired
    CarRepository repo;

    public Optional<CarDto> findById(Long id) {
        return Car.toDto(repo.findById(id));
    }

    public CarDto save(CarDto car) {
        return Car.toDto(repo.save(Car.fromDto(car)));
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    public List<CarDto> findAll() {
        return Car.toDto(repo.findAll());
    }

    public void deleteAll() {
        repo.deleteAll();
    }

    public List<CarDto> findAllByMaker(String maker) {
        return Car.toDto(repo.findAllByMaker(maker));
    }

    public List<CarDto> findByMakerStartingWith(String maker) {
        return Car.toDto(repo.findByMakerStartingWith(maker));
    }

    public List<CarDto> freeTextSearch(String query) {
        return Car.toDto(repo.freeTextSearch(query));
    }

    public void deleteOldCars() {
        repo.deleteOldCars();
    }
}
