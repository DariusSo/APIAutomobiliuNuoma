package services;

import com.APIAutomobiliuNuoma.APIAutomobiliuNuoma.Car;
import com.APIAutomobiliuNuoma.APIAutomobiliuNuoma.DatabaseRepository;
import com.APIAutomobiliuNuoma.APIAutomobiliuNuoma.Rental;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CarService {
    DatabaseRepository dbR = new DatabaseRepository();
    public String addCar(Car car) throws SQLException {
        if(car.manufacturer == null || car.model == null || car.year == 0){
            return "Truksta paramentru.";
        }
        dbR.addCar(car);
        return "Masina sekmingai prideta.";
    }
    public List<Car> getCars() throws SQLException {
        List<Car> cars = new ArrayList<>();
        ResultSet rs = dbR.getCars();
        while (rs.next()){
            Car car = new Car(rs.getInt("car_id"), rs.getString("manufacturer"), rs.getString("model"),
                    rs.getInt("year"), rs.getBoolean("available"));
            cars.add(car);
        }
        return cars;
    }
    public Car getCarById(int id) throws SQLException {
        Car car = null;
        ResultSet rs = dbR.getCarById(id);
        if(rs.next()){
            car = new Car(rs.getInt("car_id"), rs.getString("manufacturer"), rs.getString("model"),
                    rs.getInt("year"), rs.getBoolean("available"));
        }
        return car;
    }
    public String renewCarInfo(Car car) throws SQLException {
        if(car.manufacturer == null || car.model == null || car.year == 0){
            return "Truksta paramentru.";
        }
        dbR.renewCarInfo(car);
        return "Automobilio informacija atnaujinta";
    }
    public String removeCar(int id) throws SQLException {
        List<Car> carList = getCars();
        for (Car c : carList){
            if(id == c.id){
                dbR.removeCar(id);
                return "Automobilis pasalintas.";
            }
        }
        return "Automobilis su tokiu id nerastas.";
    }
    public List<Car> getAvailableCars() throws SQLException {
        List<Car> cars = new ArrayList<>();
        ResultSet rs = dbR.getAvailableCars();
        while (rs.next()){
            Car car = new Car(rs.getInt("car_id"), rs.getString("manufacturer"), rs.getString("model"),
                    rs.getInt("year"), rs.getBoolean("available"));
            cars.add(car);
        }
        return cars;
    }
    public List<Car> getCarsByMaker(String make) throws SQLException {
        List<Car> cars = new ArrayList<>();
        ResultSet rs = dbR.getCarsByMaker(make);
        while (rs.next()){
            Car car = new Car(rs.getInt("car_id"), rs.getString("manufacturer"), rs.getString("model"),
                    rs.getInt("year"), rs.getBoolean("available"));
            cars.add(car);
        }
        return cars;
    }
    public boolean checkWhenCarIsAvailable(int id, LocalDate date) throws SQLException {
        List<Rental> rentals = new ArrayList<>();
        ResultSet rs = dbR.checkWhenCarIsAvailable(id, date);
        while (rs.next()) {
            LocalDate rentalDate = LocalDate.parse(rs.getString("rental_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate returnDate = LocalDate.parse(rs.getString("return_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            if(date.isAfter(rentalDate) && date.isBefore(returnDate) || date.isEqual(rentalDate) || date.isEqual(returnDate)){
                return false;
            }
        }
        return true;
    }
    public List<Car> getCarsByYear(int year) throws SQLException {
        List<Car> cars = new ArrayList<>();
        ResultSet rs = dbR.getCarsByYear(year);
        while (rs.next()){
            Car car = new Car(rs.getInt("car_id"), rs.getString("manufacturer"), rs.getString("model"),
                    rs.getInt("year"), rs.getBoolean("available"));
            cars.add(car);
        }
        return cars;
    }
    public List<Car> getCarsByTimesRented(int count) throws SQLException {
        List<Car> cars = new ArrayList<>();
        ResultSet rs = dbR.getCarsByTimesRented(count);
        while (rs.next()){
            Car car = getCarById(rs.getInt("car_id"));
            cars.add(car);
        }
        return cars;
    }
}
