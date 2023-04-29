package com.project.classesForTables;

import java.math.BigInteger;
import java.util.Date;

public class Decree {

    private Integer id;
    private BigInteger dln_number;
    private String violation;
    private Date date;
    private String vehicle;
    private String region;
    private String status;
    private String responsibility;

    public Decree(){

    }

    public Decree(Integer id, BigInteger dln_number, String violation, Date date, String vehicle, String region, String status, String responsibility) {
        this.id = id;
        this.dln_number = dln_number;
        this.violation = violation;
        this.date = date;
        this.vehicle = vehicle;
        this.region = region;
        this.status = status;
        this.responsibility = responsibility;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigInteger getDln_number() {
        return dln_number;
    }

    public void setDln_number(BigInteger dln_number) {
        this.dln_number = dln_number;
    }

    public String getViolation() {
        return violation;
    }

    public void setViolation(String violation) {
        this.violation = violation;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }
}
