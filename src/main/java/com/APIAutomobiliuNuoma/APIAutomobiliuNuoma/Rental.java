package com.APIAutomobiliuNuoma.APIAutomobiliuNuoma;

import java.time.LocalDateTime;

public class Rental {
    public int id;
    public int carId;
    public int clientId;
    LocalDateTime rentalDate;
    LocalDateTime returnDate;

    public Rental(int id, int carId, int clientId, LocalDateTime rentalDate, LocalDateTime returnDate) {
        this.id = id;
        this.carId = carId;
        this.clientId = clientId;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }

    public Rental(int carId, int clientId, LocalDateTime rentalDate, LocalDateTime returnDate) {
        this.carId = carId;
        this.clientId = clientId;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }

    public Rental() {
    }
}
