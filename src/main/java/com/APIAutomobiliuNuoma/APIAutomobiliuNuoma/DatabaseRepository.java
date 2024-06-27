package com.APIAutomobiliuNuoma.APIAutomobiliuNuoma;

import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.time.LocalDate;

public class DatabaseRepository {

    private static final String URL = "jdbc:mysql://localhost:3306/autonuoma";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private static Connection connection;

    public static PreparedStatement SQLConnection(String sqls) throws SQLException {
        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        String sql = sqls;
        PreparedStatement ps = connection.prepareStatement(sql);
        return ps;
    }

    public void addCar(Car car) throws SQLException {

        PreparedStatement ps = SQLConnection("INSERT INTO car (manufacturer, model, year, available) VALUES (?,?,?,?)");
        ps.setString(1, car.manufacturer);
        ps.setString(2, car.model);
        ps.setInt(3, car.year);
        ps.setBoolean(4, car.available);
        ps.executeUpdate();
    }
    public ResultSet getCars() throws SQLException {
        PreparedStatement ps = SQLConnection("SELECT * FROM car");
        ResultSet rs = ps.executeQuery();
        return rs;
    }
    public ResultSet getCarById(int id) throws SQLException {

        PreparedStatement ps = SQLConnection("SELECT * FROM car WHERE car_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        return rs;
    }
    public void renewCarInfo(Car car) throws SQLException {

        PreparedStatement ps = SQLConnection("UPDATE car " +
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

    public void removeCar(int id) throws SQLException {
        PreparedStatement ps = SQLConnection("DELETE FROM car WHERE car_id = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
    }
    public ResultSet getAvailableCars() throws SQLException {

        PreparedStatement ps = SQLConnection("SELECT * FROM car WHERE available = 1");
        ResultSet rs = ps.executeQuery();
        return rs;
    }
    public ResultSet getCarsByMaker(String make) throws SQLException {
        PreparedStatement ps = SQLConnection("SELECT * FROM car WHERE manufacturer = ?");
        ps.setString(1, make);
        ResultSet rs = ps.executeQuery();
        return rs;
    }
    public ResultSet checkWhenCarIsAvailable(int id, LocalDate date) throws SQLException {
        PreparedStatement ps = SQLConnection("SELECT * FROM rental WHERE car_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        return rs;
    }
    public ResultSet getCarsByYear(int year) throws SQLException {

        PreparedStatement ps = SQLConnection("SELECT * FROM car WHERE year = ?");
        ps.setInt(1, year);
        ResultSet rs = ps.executeQuery();
        return rs;
    }
    public ResultSet getCarsByTimesRented(int count) throws SQLException {

        PreparedStatement ps = SQLConnection("SELECT car_id, COUNT(car_id) AS 'duplicate' FROM rental GROUP BY car_id HAVING COUNT(car_id) > ?");
        ps.setInt(1, count);
        ResultSet rs = ps.executeQuery();
        return rs;
    }
    public ResultSet getClients() throws SQLException {

        PreparedStatement ps = SQLConnection("SELECT * FROM client");
        ResultSet rs = ps.executeQuery();
        return rs;
    }
    public void addClient(Client c) throws SQLException {

        PreparedStatement ps = SQLConnection("INSERT INTO client (first_name, last_name, email, phone) VALUES (?,?,?,?)");
        ps.setString(1, c.firstName);
        ps.setString(2, c.lastName);
        ps.setString(3, c.email);
        ps.setString(4, c.phone);
        ps.executeUpdate();
    }
    public ResultSet getClient(int id) throws SQLException {
        PreparedStatement ps = SQLConnection("SELECT * FROM client WHERE client_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    public void renewClientInfo(Client c, int id) throws SQLException {
        PreparedStatement ps = SQLConnection("UPDATE client " +
                "SET first_name = ?," +
                "last_name = ?," +
                "email = ?," +
                "phone = ?" +
                " WHERE client_id = ?");
        ps.setString(1, c.firstName);
        ps.setString(2, c.lastName);
        ps.setString(3, c.email);
        ps.setString(4, c.phone);
        ps.setInt(5, id);
        ps.executeUpdate();
    }

    public void removeClient(int id) throws SQLException {

        PreparedStatement ps = SQLConnection("DELETE FROM client WHERE client_id = ?");
        ps.setInt(1, id);
        ps.executeUpdate();

    }

    public ResultSet getClientsRentals(int id) throws SQLException {

        PreparedStatement ps = SQLConnection("SELECT * FROM rental WHERE client_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    public ResultSet getClientsActiveRentals() throws SQLException {

        PreparedStatement ps = SQLConnection("SELECT * FROM rental");
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    public void registerRental(Rental r) throws SQLException {

        PreparedStatement ps = SQLConnection("INSERT INTO rental (car_id, client_id, rental_date, return_date) VALUES (?,?,?,?)");
        ps.setInt(1, r.carId);
        ps.setInt(2, r.clientId);
        ps.setString(3, String.valueOf(r.rentalDate));
        ps.setString(4, String.valueOf(r.returnDate));
        ps.executeUpdate();
    }

    public ResultSet getRentals() throws SQLException {

        PreparedStatement ps = SQLConnection("SELECT * FROM rental");
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    public ResultSet getRentalById(int id) throws SQLException {

        PreparedStatement ps = SQLConnection("SELECT * FROM rental WHERE rental_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    public void renewReturnDate(Rental r, int id) throws SQLException {
        PreparedStatement ps = SQLConnection("UPDATE rental " +
                "SET return_date = ?" +
                " WHERE rental_id = ?");
        ps.setString(1, String.valueOf(r.returnDate));
        ps.setInt(2, id);
        ps.executeUpdate();
    }

    public void removeRental(int id) throws SQLException {

        PreparedStatement ps = SQLConnection("DELETE FROM rental WHERE rental_id = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    public ResultSet rentalsPeriod(LocalDate fromDate, LocalDate toDate) throws SQLException {

        PreparedStatement ps = SQLConnection("SELECT * FROM rental");
        ResultSet rs = ps.executeQuery();
        return rs;

    }

}
