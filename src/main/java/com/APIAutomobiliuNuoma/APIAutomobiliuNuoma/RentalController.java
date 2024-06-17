package com.APIAutomobiliuNuoma.APIAutomobiliuNuoma;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@RestController
public class RentalController {

    @PostMapping("/registerRental")
    public String registerRental(@RequestBody Rental r) throws SQLException {

        Car car = CarController.getCarById(r.carId);
        if(car.available == true){
            PreparedStatement ps = manageSQL.SQLConnection("INSERT INTO car (car_id, client_id, rental_date, return_date) VALUES (?,?,?,?)");
            ps.setInt(1, r.carId);
            ps.setInt(2, r.clientId);
            ps.setString(3, String.valueOf(r.rentalDate));
            ps.setString(4, String.valueOf(r.returnDate));
            ps.executeUpdate();
            return "Pavyko";
        }else{
            return "Siuo metu automobilis nera laisvas";
        }
    }
}
