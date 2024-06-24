package com.APIAutomobiliuNuoma.APIAutomobiliuNuoma;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RentalController {

    @PostMapping("/registerRental")
    public String registerRental(@RequestBody Rental r) throws SQLException {

        Car car = CarController.getCarById(r.carId);
        Client client = ClientController.getClient(r.clientId);
        if(car.available != true){
            return "Car is not available at the moment.";
        } else if (client == null) {
            return "Client with id " + r.clientId + " doesn't exist.";
        }else{
            String s = String.valueOf(r.rentalDate);
            LocalDate rental = LocalDate.parse("8-Jan-2020", DateTimeFormatter.ofPattern("d-MMM-yyyy"));
            PreparedStatement ps = manageSQL.SQLConnection("INSERT INTO rental (car_id, client_id, rental_date, return_date) VALUES (?,?,?,?)");
            ps.setInt(1, r.carId);
            ps.setInt(2, r.clientId);
            ps.setString(3, String.valueOf(s));
            ps.setString(4, String.valueOf(r.returnDate));
            ps.executeUpdate();
            CarController.renewCarInfo(car);
            return "Registered.";
        }
    }
    @GetMapping("/getRentals")
    public List<Rental> getRentals() throws SQLException {
        List<Rental> rentals = new ArrayList<>();
        PreparedStatement ps = manageSQL.SQLConnection("SELECT * FROM rental");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            LocalDate rentalDate = LocalDate.parse(rs.getString("rental_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate returnDate = LocalDate.parse(rs.getString("return_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Rental rental = new Rental(rs.getInt("rental_id"), rs.getInt("car_id"), rs.getInt("client_id"), rentalDate, returnDate);
            rentals.add(rental);
        }
        return rentals;
    }
    @GetMapping("/getRentalById")
    public static Rental getClient(int id) throws SQLException {
        Rental rental = null;
        PreparedStatement ps = manageSQL.SQLConnection("SELECT * FROM rental WHERE rental_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            LocalDate rentalDate = LocalDate.parse(rs.getString("rental_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate returnDate = LocalDate.parse(rs.getString("return_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            rental = new Rental(rs.getInt("rental_id"), rs.getInt("car_id"), rs.getInt("client_id"), rentalDate, returnDate);
        }
        return rental;
    }
    @PutMapping("/renewReturnDate")
    public void renewReturnDate(@RequestBody Rental r, int id) throws SQLException {
        PreparedStatement ps = manageSQL.SQLConnection("UPDATE rental " +
                "SET return_date = ?" +
                " WHERE rental_id = ?");
        ps.setString(1, String.valueOf(r.returnDate));
        ps.setInt(2, id);
        ps.executeUpdate();
    }
    @DeleteMapping("/removeRental")
    public String removeClient(int id) throws SQLException {
        List<Rental> rentalList = getRentals();
        for(Rental r : rentalList){
            if (r.id == id){
                PreparedStatement ps = manageSQL.SQLConnection("DELETE FROM rental WHERE rental_id = ?");
                ps.setInt(1, id);
                ps.executeUpdate();
                return "Sekmingai pasalinta";
            }
        }
        return "Nerasta";
    }
}
