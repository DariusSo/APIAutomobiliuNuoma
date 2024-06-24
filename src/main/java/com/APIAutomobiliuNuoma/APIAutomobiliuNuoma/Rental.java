package com.APIAutomobiliuNuoma.APIAutomobiliuNuoma;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Rental {
    public int id;
    public int carId;
    public int clientId;
    @JsonFormat(pattern="yyyy-MM-dd")
    LocalDate rentalDate;
    @JsonFormat(pattern="yyyy-MM-dd")
    LocalDate returnDate;

    public Rental(int id, int carId, int clientId, LocalDate rentalDate, LocalDate returnDate) {
        this.id = id;
        this.carId = carId;
        this.clientId = clientId;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }

    public Rental(int carId, int clientId, LocalDate rentalDate, LocalDate returnDate) {
        this.carId = carId;
        this.clientId = clientId;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }

    public Rental() {
    }
}
