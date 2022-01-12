package gr.kariera.mindthecode.web.controllers;

import gr.kariera.mindthecode.shared.CarDto;
import gr.kariera.mindthecode.web.clients.CarClient;
import gr.kariera.mindthecode.web.controllers.models.CarSearchModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class CarMvcController {

    @Autowired
    private CarClient repository;


    @PostMapping("/cars")
    public Object searchCarsSubmit(
            @ModelAttribute CarSearchModel searchModel) {
        return "redirect:/cars?search=" + searchModel.getQuery();
    }

    @GetMapping("/cars")
    public Object showCars(
            Model model,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String search
    ) {
        if (page < 1) {
            return new RedirectView("/cars?page=1&size="+ size);
        };

        Page<CarDto> cars = findPaginated(
                !search.equals("") ?
                        repository.getCars(null,null, search) :
                        repository.getCars(null, null, null),
                PageRequest.of(page - 1, size)
        );

        int totalPages = cars.getTotalPages();

        if (page > totalPages && totalPages != 0) {
            return new RedirectView("/cars?size="+ size + "&page=" + totalPages);
        };

        List<Integer> pageNumbers = IntStream.rangeClosed(Math.max(1, page-2), Math.min(page + 2, totalPages))
                .boxed()
                .collect(Collectors.toList());
        model.addAttribute("pageNumbers", pageNumbers);

        model.addAttribute("page", page);
        model.addAttribute("cars", cars);
        model.addAttribute("searchModel", new CarSearchModel(search));
        return "cars";
    }

    @GetMapping("/cars/addcar")
    public String addCar(Model model) {
        model.addAttribute("car", new CarDto());
        return "add-car";
    }

    @GetMapping("/cars/deleteOld")
    public String deleteOld(Model model) {
        repository.deleteOldCars();
        return "redirect:/cars";
    }



    @PostMapping("/cars/addcar")
    public String addCar(@Valid CarDto car, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-car";
        }

        repository.updateCar(car, 0L);
        model.addAttribute("car", car);
        return "redirect:/cars";
    }

    @GetMapping("/cars/update/{id}")
    public String updateCar(@PathVariable("id") long id, Model model) {
        CarDto car = repository.getCar(id);

        model.addAttribute("car", car);
        return "update-car";
    }

    @PostMapping("/cars/update/{id}")
    public String updateCar(@PathVariable("id") long id, @Valid CarDto car,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            car.setId(id);
            return "update-car";
        }

        repository.updateCar(car, car.getId());
        return "redirect:/cars";
    }

    @GetMapping("/cars/delete/{id}")
    public String deleteCar(@PathVariable("id") long id, Model model) {
        CarDto car = repository.getCar(id);
        repository.deleteCar(car.getId());
        return "redirect:/cars";
    }

    private Page<CarDto> findPaginated(List<CarDto> cars, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<CarDto> result;

        if (cars.size() < startItem) {
            result = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, cars.size());
            result = cars.subList(startItem, toIndex);
        }

        Page<CarDto> carPage = new PageImpl<CarDto>(result, PageRequest.of(currentPage, pageSize), cars.size());

        return carPage;
    }
}
