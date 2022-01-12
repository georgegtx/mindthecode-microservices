package gr.kariera.mindthecode.controllers.api;

import gr.kariera.mindthecode.services.CarService;
import gr.kariera.mindthecode.shared.CarController;
import gr.kariera.mindthecode.shared.CarDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CarControllerImpl implements CarController {

    @Autowired
    private CarService service;

    @GetMapping(value = "/api/cars")
    public List<CarDto> getCars(
            @RequestParam(name = "byMaker", required = false) String byMaker,
            @RequestParam(name = "makerStartsWith", required = false) String makerStartsWith,
            @RequestParam(name = "query", required = false) String query
    ) {
        if (byMaker != null && byMaker != "") {
            return service.findAllByMaker(byMaker);
        }
        if (makerStartsWith != null && makerStartsWith != "") {
            return service.findByMakerStartingWith(makerStartsWith);
        }
        if (query != null && query != "") {
            return service.freeTextSearch(query);
        }
        return service.findAll();
    }

    @GetMapping(value = "/api/cars/{id}")
    public CarDto getCar(@PathVariable("id") Long id) {
        return service.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot find car with id " + id));
    }

    @PutMapping(value = "/api/cars/{id}")
    public CarDto updateCar(@RequestBody CarDto newCar, @PathVariable("id") Long id) {

        return service.findById(id)
                .map(match -> {
                    match.setMaker(newCar.getMaker());
                    match.setMileage(newCar.getMileage());
                    match.setSerialNumber(newCar.getSerialNumber());
                    return service.save(match);
                })
                .orElseGet(() -> {
                    newCar.setId(id);
                    return service.save(newCar);
                });
    }

    @DeleteMapping(value = "/api/cars")
    public void deleteAllCars() {
        service.deleteAll();
    }

    @DeleteMapping(value = "/api/cars/{id}")
    public void deleteCar(@PathVariable("id") Long id) {
        service.deleteById(id);
    }

    @DeleteMapping(value = "/api/cars/old")
    public void deleteOldCars() {
        service.deleteOldCars();
    }
}
