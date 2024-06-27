package services;

import com.APIAutomobiliuNuoma.APIAutomobiliuNuoma.Client;
import com.APIAutomobiliuNuoma.APIAutomobiliuNuoma.DatabaseRepository;
import com.APIAutomobiliuNuoma.APIAutomobiliuNuoma.Rental;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    DatabaseRepository dbR = new DatabaseRepository();
    public List<Client> getClients() throws SQLException {
        List<Client> clients = new ArrayList<>();
        ResultSet rs = dbR.getClients();
        while (rs.next()){
            Client client = new Client(rs.getInt("client_id"), rs.getString("first_name"), rs.getString("last_name"),
                    rs.getString("email"), rs.getString("phone"));
            clients.add(client);
        }
        return clients;
    }
    public String addClient(Client c) throws SQLException {
        if(c.firstName == null || c.lastName == null || c.email == null || c.phone == null){
            return "Truksta paramentru";
        }
        dbR.addClient(c);
        return "Klientas pridetas";
    }

    public Client getClient(int id) throws SQLException {
        Client client = null;
        ResultSet rs = dbR.getClient(id);
        if(rs.next()){
            client = new Client(rs.getInt("client_id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"), rs.getString("phone"));

        }
        return client;
    }

    public void renewClientInfo(Client c, int id) throws SQLException {
        dbR.renewClientInfo(c, id);
    }

    public String removeClient(int id) throws SQLException {
        List<Client> clientList = getClients();
        for(Client c : clientList){
            if (c.id == id){
                dbR.removeClient(id);
                return "Klientas sekmingai pasalintas";
            }
        }
        return "Tokio kliento nera";
    }

    public List<Rental> getClientsRentals(int id) throws SQLException {
        List<Rental> rentals = new ArrayList<>();
        ResultSet rs = dbR.getClientsRentals(id);
        while (rs.next()) {
            LocalDate rentalDate = LocalDate.parse(rs.getString("rental_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate returnDate = LocalDate.parse(rs.getString("return_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Rental rental = new Rental(rs.getInt("rental_id"), rs.getInt("car_id"), rs.getInt("client_id"), rentalDate, returnDate);
            rentals.add(rental);
        }
        return rentals;
    }

    public List<Rental> getClientsActiveRentals() throws SQLException {
        List<Rental> rentals = new ArrayList<>();
        List<Rental> activeRentals = new ArrayList<>();

        ResultSet rs = dbR.getClientsActiveRentals();
        while (rs.next()) {
            LocalDate rentalDate = LocalDate.parse(rs.getString("rental_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate returnDate = LocalDate.parse(rs.getString("return_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Rental rental = new Rental(rs.getInt("rental_id"), rs.getInt("car_id"), rs.getInt("client_id"), rentalDate, returnDate);
            rentals.add(rental);
        }
        LocalDate today = LocalDate.now();
        for(Rental r : rentals){
            if(today.isAfter(r.getRentalDate()) && today.isBefore(r.getReturnDate()) || today.isEqual(r.getRentalDate()) || today.isEqual(r.getReturnDate())){
                activeRentals.add(r);
            }
        }
        return activeRentals;
    }
}
