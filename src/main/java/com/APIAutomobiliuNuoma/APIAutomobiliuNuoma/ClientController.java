package com.APIAutomobiliuNuoma.APIAutomobiliuNuoma;

import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/clients")
public class ClientController {

    @GetMapping
    public List<Client> getClients() throws SQLException {
        List<Client> clients = new ArrayList<>();
        PreparedStatement ps = manageSQL.SQLConnection("SELECT * FROM client");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Client client = new Client(rs.getInt("client_id"), rs.getString("first_name"), rs.getString("last_name"),
                    rs.getString("email"), rs.getString("phone"));
            clients.add(client);
        }
        return clients;
    }
    @PostMapping
    public String addClient(@RequestBody Client c) throws SQLException {
        if(c.firstName == null || c.lastName == null || c.email == null || c.phone == null){
            return "Truksta paramentru";
        }
        PreparedStatement ps = manageSQL.SQLConnection("INSERT INTO client (first_name, last_name, email, phone) VALUES (?,?,?,?)");
        ps.setString(1, c.firstName);
        ps.setString(2, c.lastName);
        ps.setString(3, c.email);
        ps.setString(4, c.phone);
        ps.executeUpdate();
        return "Klientas pridetas";
    }
    @GetMapping("/{id}")
    public static Client getClient(@PathVariable int id) throws SQLException {
        Client client = null;
        PreparedStatement ps = manageSQL.SQLConnection("SELECT * FROM client WHERE client_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            client = new Client(rs.getInt("client_id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"), rs.getString("phone"));

        }
        if (client == null){
            System.out.println("Toks klientas nerastas");
        }
        return client;
    }
    @PutMapping("/{id}")
    public void renewCarInfo(@RequestBody Client c) throws SQLException {
        PreparedStatement ps = manageSQL.SQLConnection("UPDATE client " +
                "SET first_name = ?," +
                "last_name = ?," +
                "email = ?," +
                "phone = ?" +
                " WHERE client_id = ?");
        ps.setString(1, c.firstName);
        ps.setString(2, c.lastName);
        ps.setString(3, c.email);
        ps.setString(4, c.phone);
        ps.setInt(5, c.id);
        ps.executeUpdate();
    }
    @DeleteMapping("/{id}")
    public String removeClient(@PathVariable int id) throws SQLException {
        List<Client> clientList = getClients();
        for(Client c : clientList){
            if (c.id == id){
                PreparedStatement ps = manageSQL.SQLConnection("DELETE FROM client WHERE client_id = ?");
                ps.setInt(1, id);
                ps.executeUpdate();
                return "Klientas sekmingai pasalintas";
            }
        }
        return "Tokio kliento nera";
    }
    @GetMapping("/{id}/rentals")
    public List<Rental> getClientsRentals(@PathVariable int id) throws SQLException {
        List<Rental> rentals = new ArrayList<>();
        PreparedStatement ps = manageSQL.SQLConnection("SELECT * FROM rental WHERE client_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            LocalDate rentalDate = LocalDate.parse(rs.getString("rental_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate returnDate = LocalDate.parse(rs.getString("return_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Rental rental = new Rental(rs.getInt("rental_id"), rs.getInt("car_id"), rs.getInt("client_id"), rentalDate, returnDate);
            rentals.add(rental);
        }
        return rentals;
    }
    @GetMapping("/active-rentals")
    public List<Rental> getClientsActiveRentals() throws SQLException {
        List<Rental> rentals = new ArrayList<>();
        List<Rental> activeRentals = new ArrayList<>();
        PreparedStatement ps = manageSQL.SQLConnection("SELECT * FROM rental");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            LocalDate rentalDate = LocalDate.parse(rs.getString("rental_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate returnDate = LocalDate.parse(rs.getString("return_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Rental rental = new Rental(rs.getInt("rental_id"), rs.getInt("car_id"), rs.getInt("client_id"), rentalDate, returnDate);
            rentals.add(rental);
        }
        LocalDate today = LocalDate.now();
        for(Rental r : rentals){
            if(today.isAfter(r.rentalDate) && today.isBefore(r.returnDate) || today.isEqual(r.rentalDate) || today.isEqual(r.returnDate)){
                activeRentals.add(r);
            }
        }
        return activeRentals;
    }



}
