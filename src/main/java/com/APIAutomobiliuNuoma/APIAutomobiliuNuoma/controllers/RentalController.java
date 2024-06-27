package com.APIAutomobiliuNuoma.APIAutomobiliuNuoma.controllers;

import com.APIAutomobiliuNuoma.APIAutomobiliuNuoma.*;
import org.springframework.web.bind.annotation.*;
import services.RentalService;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rentals")
public class RentalController {
    RentalService rentalService = new RentalService();

    @PostMapping
    public String registerRental(@RequestBody Rental r) throws SQLException {
        return rentalService.registerRental(r);
    }
    @GetMapping
    public List<Rental> getRentals() throws SQLException {
        return rentalService.getRentals();
    }
    @GetMapping("/{id}")
    public Rental getRentalById(@PathVariable int id) throws SQLException {
        return rentalService.getRentalById(id);
    }
    @PutMapping("/{id}")
    public void renewReturnDate(@RequestBody Rental r, @PathVariable int id) throws SQLException {
        rentalService.renewReturnDate(r, id);
    }
    @DeleteMapping("/{id}")
    public String removeRental(@PathVariable int id) throws SQLException {
        return rentalService.removeRental(id);
    }
    @GetMapping("/period")
    public List<Rental> rentalsPeriod(LocalDate fromDate, LocalDate toDate) throws SQLException {
        return rentalService.rentalsPeriod(fromDate, toDate);
    }


}
