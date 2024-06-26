package com.APIAutomobiliuNuoma.APIAutomobiliuNuoma;

import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {
    public static List<Car> carList = new ArrayList<>();

    @PostMapping
    public String addCar(@RequestBody Car car) throws SQLException {
        if(car.manufacturer == null || car.model == null || car.year == 0){
            return "Truksta paramentru.";
        }
        PreparedStatement ps = manageSQL.SQLConnection("INSERT INTO car (manufacturer, model, year, available) VALUES (?,?,?,?)");
        ps.setString(1, car.manufacturer);
        ps.setString(2, car.model);
        ps.setInt(3, car.year);
        ps.setBoolean(4, car.available);
        ps.executeUpdate();
        return "Masina sekmingai prideta.";
    }
    @GetMapping
    public List<Car> getCars() throws SQLException {
        List<Car> cars = new ArrayList<>();
        PreparedStatement ps = manageSQL.SQLConnection("SELECT * FROM car");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Car car = new Car(rs.getInt("car_id"), rs.getString("manufacturer"), rs.getString("model"),
                    rs.getInt("year"), rs.getBoolean("available"));
            cars.add(car);
        }
        return cars;
    }
    @GetMapping("/{id}")
    public static Car getCarById(@PathVariable int id) throws SQLException {
        Car car = null;
        PreparedStatement ps = manageSQL.SQLConnection("SELECT * FROM car WHERE car_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            car = new Car(rs.getInt("car_id"), rs.getString("manufacturer"), rs.getString("model"),
                    rs.getInt("year"), rs.getBoolean("available"));
        }
        if (car == null){
            System.out.println("Nera masinos su tokiu id.");
        }
        return car;
    }
    @PutMapping("/{id}")
    public static String renewCarInfo(@RequestBody Car car) throws SQLException {
        if(car.manufacturer == null || car.model == null || car.year == 0){
            return "Truksta paramentru.";
        }else{
            PreparedStatement ps = manageSQL.SQLConnection("UPDATE car " +
                    "SET manufacturer = ?," +
                    "model = ?," +
                    "year = ?," +
                    "available = ?" +
                    " WHERE car_id = ?");
            ps.setString(1, car.manufacturer);
            ps.setString(2, car.model);
            ps.setInt(3, car.year);
            ps.setBoolean(4, car.available);
            ps.setInt(5, car.id);
            ps.executeUpdate();
            return "Automobilio informacija atnaujinta";
        }
    }
    @DeleteMapping("/{id}")
    public String removeCar(@PathVariable int id) throws SQLException {
        List<Car> carList = getCars();
        for (Car c : carList){
            if(id == c.id){
                PreparedStatement ps = manageSQL.SQLConnection("DELETE FROM car WHERE car_id = ?");
                ps.setInt(1, id);
                ps.executeUpdate();
                return "Automobilis pasalintas.";
            }
        }
        return "Automobilis su tokiu id nerastas.";
    }
    @GetMapping("/available")
    public List<Car> getAvailableCars() throws SQLException {
        List<Car> cars = new ArrayList<>();
        PreparedStatement ps = manageSQL.SQLConnection("SELECT * FROM car WHERE available = 1");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Car car = new Car(rs.getInt("car_id"), rs.getString("manufacturer"), rs.getString("model"),
                    rs.getInt("year"), rs.getBoolean("available"));
            cars.add(car);
        }
        return cars;
    }
    @GetMapping("/make/{make}")
    public List<Car> getCarsByMaker(@PathVariable String make) throws SQLException {
        List<Car> cars = new ArrayList<>();
        PreparedStatement ps = manageSQL.SQLConnection("SELECT * FROM car WHERE manufacturer = ?");
        ps.setString(1, make);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Car car = new Car(rs.getInt("car_id"), rs.getString("manufacturer"), rs.getString("model"),
                    rs.getInt("year"), rs.getBoolean("available"));
            cars.add(car);
        }
        return cars;
    }
    @GetMapping("/{id}/availability")
    public boolean checkWhenCarIsAvailable(@PathVariable int id, LocalDate date) throws SQLException {
        List<Rental> rentals = new ArrayList<>();
        PreparedStatement ps = manageSQL.SQLConnection("SELECT * FROM rental WHERE car_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            LocalDate rentalDate = LocalDate.parse(rs.getString("rental_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate returnDate = LocalDate.parse(rs.getString("return_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            if(date.isAfter(rentalDate) && date.isBefore(returnDate) || date.isEqual(rentalDate) || date.isEqual(returnDate)){
                return false;
            }
        }
        return true;
    }
    @GetMapping("/year/{year}")
    public List<Car> getCarsByYear(@PathVariable int year) throws SQLException {
        List<Car> cars = new ArrayList<>();
        PreparedStatement ps = manageSQL.SQLConnection("SELECT * FROM car WHERE year = ?");
        ps.setInt(1, year);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Car car = new Car(rs.getInt("car_id"), rs.getString("manufacturer"), rs.getString("model"),
                    rs.getInt("year"), rs.getBoolean("available"));
            cars.add(car);
        }
        return cars;
    }
    @GetMapping("/rented-more-than/{count}")
    public List<Car> getCarsByTimesRented(@PathVariable int count) throws SQLException {
        List<Car> cars2 = new ArrayList<>();
        List<Car> cars = new ArrayList<>();
        PreparedStatement ps = manageSQL.SQLConnection("SELECT car_id, COUNT(car_id) AS 'duplicate' FROM rental GROUP BY car_id HAVING COUNT(car_id) > ?");
        ps.setInt(1, count);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Car car = getCarById(rs.getInt("car_id"));
            cars.add(car);
        }
        return cars;
    }










}
