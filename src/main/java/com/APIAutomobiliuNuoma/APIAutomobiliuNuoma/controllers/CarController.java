package com.APIAutomobiliuNuoma.APIAutomobiliuNuoma.controllers;

import com.APIAutomobiliuNuoma.APIAutomobiliuNuoma.Car;
import org.springframework.web.bind.annotation.*;
import services.CarService;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {
    public CarService carS = new CarService();
    public static List<Car> carList = new ArrayList<>();

    @PostMapping
    public String addCar(@RequestBody Car car) throws SQLException {
        return carS.addCar(car);
    }
    @GetMapping
    public List<Car> getCars() throws SQLException {
        return carS.getCars();
    }
    @GetMapping("/{id}")
    public Car getCarById(@PathVariable int id) throws SQLException {
        return carS.getCarById(id);
    }
    @PutMapping("/{id}")
    public String renewCarInfo(@RequestBody Car car) throws SQLException {
        return carS.renewCarInfo(car);
    }
    @DeleteMapping("/{id}")
    public String removeCar(@PathVariable int id) throws SQLException {
        return carS.removeCar(id);
    }
    @GetMapping("/available")
    public List<Car> getAvailableCars() throws SQLException {

        return carS.getAvailableCars();
    }
    @GetMapping("/make/{make}")
    public List<Car> getCarsByMaker(@PathVariable String make) throws SQLException {
        return carS.getCarsByMaker(make);
    }
    @GetMapping("/{id}/availability")
    public boolean checkWhenCarIsAvailable(@PathVariable int id, LocalDate date) throws SQLException {
        return carS.checkWhenCarIsAvailable(id, date);
    }
    @GetMapping("/year/{year}")
    public List<Car> getCarsByYear(@PathVariable int year) throws SQLException {
        return carS.getCarsByYear(year);
    }
    @GetMapping("/rented-more-than/{count}")
    public List<Car> getCarsByTimesRented(@PathVariable int count) throws SQLException {
        return carS.getCarsByTimesRented(count);
    }










}
