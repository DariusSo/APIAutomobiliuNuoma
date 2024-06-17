package com.APIAutomobiliuNuoma.APIAutomobiliuNuoma;

import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CarController {
    public static List<Car> carList = new ArrayList<>();

    @PostMapping("/addCar")
    public void addCar(@RequestBody Car car) throws SQLException {
        PreparedStatement ps = manageSQL.SQLConnection("INSERT INTO car (manufacturer, model, year, available) VALUES (?,?,?,?)");
        ps.setString(1, car.manufacturer);
        ps.setString(2, car.model);
        ps.setInt(3, car.year);
        ps.setBoolean(4, car.available);
        ps.executeUpdate();
    }
    @GetMapping("/getCars")
    public List<Car> getCars() throws SQLException {
        List<Car> cars = new ArrayList<>();
        PreparedStatement ps = manageSQL.SQLConnection("SELECT * FROM car");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Car car = new Car(rs.getString("manufacturer"), rs.getString("model"),
                    rs.getInt("year"), rs.getBoolean("available"));
            cars.add(car);
        }
        return cars;
    }
    @GetMapping("/getCarById")
    public static Car getCarById(int id) throws SQLException {
        Car car = null;
        PreparedStatement ps = manageSQL.SQLConnection("SELECT * FROM car WHERE car_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            car = new Car(rs.getInt("car_id"), rs.getString("manufacturer"), rs.getString("model"),
                    rs.getInt("year"), rs.getBoolean("available"));
        }
        return car;
    }
    @PutMapping("/renewCarInfo")
    public void renewCarInfo(@RequestBody Car car) throws SQLException {
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
    }
    @DeleteMapping("/removeCar")
    public void removeCar(int id) throws SQLException {
        PreparedStatement ps = manageSQL.SQLConnection("DELETE FROM car WHERE car_id = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
    }





}
