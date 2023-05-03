package com.project.classesForTables;

public class Vehicle {

    private Integer id;
    private String stateNumber;
    private String WIN;
    private String color;
    private String brand;

    public Vehicle(Integer id, String stateNumber, String WIN, String color, String brand) {
        this.id = id;
        this.stateNumber = stateNumber;
        this.WIN = WIN;
        this.color = color;
        this.brand = brand;
    }

    public Vehicle(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStateNumber() {
        return stateNumber;
    }

    public void setStateNumber(String stateNumber) {
        this.stateNumber = stateNumber;
    }

    public String getWIN() {
        return WIN;
    }

    public void setWIN(String WIN) {
        this.WIN = WIN;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
