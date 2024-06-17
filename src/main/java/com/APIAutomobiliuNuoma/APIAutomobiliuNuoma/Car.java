package com.APIAutomobiliuNuoma.APIAutomobiliuNuoma;

public class Car {
    public int id;
    public String manufacturer;
    public String model;
    public int year;
    public boolean available;

    public Car(int id, String manufacturer, String model, int year, boolean available) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.model = model;
        this.year = year;
        this.available = available;
    }

    public Car(String manufacturer, String model, int year, boolean available) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.year = year;
        this.available = available;
    }

    public Car() {
    }
}
