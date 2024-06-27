package services;

import com.APIAutomobiliuNuoma.APIAutomobiliuNuoma.Car;
import com.APIAutomobiliuNuoma.APIAutomobiliuNuoma.Client;
import com.APIAutomobiliuNuoma.APIAutomobiliuNuoma.DatabaseRepository;
import com.APIAutomobiliuNuoma.APIAutomobiliuNuoma.Rental;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RentalService {
    DatabaseRepository dbR = new DatabaseRepository();

    public String registerRental(Rental r) throws SQLException {
        CarService carService = new CarService();
        ClientService clientService = new ClientService();
        Car car = carService.getCarById(r.carId);
        Client client = clientService.getClient(r.clientId);
        if(car.available != true){
            return "Car is not available at the moment.";
        } else if (client == null) {
            return "Client with id " + r.clientId + " doesn't exist.";
        }else{
            dbR.registerRental(r);
            carService.renewCarInfo(car);
            return "Registered.";
        }
    }

    public List<Rental> getRentals() throws SQLException {
        List<Rental> rentals = new ArrayList<>();
        ResultSet rs = dbR.getRentals();
        while (rs.next()){
            LocalDate rentalDate = LocalDate.parse(rs.getString("rental_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate returnDate = LocalDate.parse(rs.getString("return_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Rental rental = new Rental(rs.getInt("rental_id"), rs.getInt("car_id"), rs.getInt("client_id"), rentalDate, returnDate);
            rentals.add(rental);
        }
        return rentals;
    }

    public Rental getRentalById(int id) throws SQLException {
        Rental rental = null;
        ResultSet rs = dbR.getRentalById(id);
        if(rs.next()){
            LocalDate rentalDate = LocalDate.parse(rs.getString("rental_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate returnDate = LocalDate.parse(rs.getString("return_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            rental = new Rental(rs.getInt("rental_id"), rs.getInt("car_id"), rs.getInt("client_id"), rentalDate, returnDate);
        }
        return rental;
    }

    public void renewReturnDate(Rental r, int id) throws SQLException {
        dbR.renewReturnDate(r, id);
    }

    public String removeRental(int id) throws SQLException {
        List<Rental> rentalList = getRentals();
        for(Rental r : rentalList){
            if (r.id == id){
                dbR.removeRental(id);
                return "Sekmingai pasalinta";
            }
        }
        return "Nerasta";
    }

    public List<Rental> rentalsPeriod(LocalDate fromDate, LocalDate toDate) throws SQLException {
        List<Rental> rentals = new ArrayList<>();
        List<Rental> comparedRentals = new ArrayList<>();
        ResultSet rs = dbR.rentalsPeriod(fromDate, toDate);
        while (rs.next()){
            LocalDate rentalDate = LocalDate.parse(rs.getString("rental_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate returnDate = LocalDate.parse(rs.getString("return_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Rental rental = new Rental(rs.getInt("rental_id"), rs.getInt("car_id"), rs.getInt("client_id"), rentalDate, returnDate);
            rentals.add(rental);
        }
        for(Rental r : rentals){
            if(r.getRentalDate().isAfter(fromDate) && r.getReturnDate().isBefore(toDate)){
                comparedRentals.add(r);
            }
        }
        return comparedRentals;

    }
}
