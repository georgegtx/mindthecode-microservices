package gr.kariera.mindthecode.shared;

import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface CarController {

    @GetMapping(value = "/api/cars")
    List<CarDto> getCars(
            @RequestParam(name = "byMaker", required = false) String byMaker,
            @RequestParam(name = "makerStartsWith", required = false) String makerStartsWith,
            @RequestParam(name = "query", required = false) String query
    );

    @GetMapping(value = "/api/cars/{id}")
    CarDto getCar(@PathVariable("id") Long id);

    @PutMapping(value = "/api/cars/{id}")
    CarDto updateCar(@RequestBody CarDto newCar, @PathVariable("id") Long id);

    @DeleteMapping(value = "/api/cars")
    void deleteAllCars();

    @DeleteMapping(value = "/api/cars/{id}")
    void deleteCar(@PathVariable("id") Long id);

    @DeleteMapping(value = "/api/cars/old")
    void deleteOldCars();
}
