package com.project.classesForTables;

import java.math.BigInteger;
import java.util.Date;

public class Drivers {

    private Integer id;
    private String Fio;
    private BigInteger passport;
    private BigInteger telephoneNumber;
    private String registrationAddres;
    private BigInteger driversLicenseNumber;
    private Date date;
    private String  trafficPoliceDepartament;
    private String category;

    public Drivers(Integer id, String fio, BigInteger passport, BigInteger telephoneNumber, String registrationAddres,
                   BigInteger driversLicenseNumber, Date date, String trafficPoliceDepartament, String category) {
        this.id = id;
        Fio = fio;
        this.passport = passport;
        this.telephoneNumber = telephoneNumber;
        this.registrationAddres = registrationAddres;
        this.driversLicenseNumber = driversLicenseNumber;
        this.date = date;
        this.trafficPoliceDepartament = trafficPoliceDepartament;
        this.category = category;
    }

    public Drivers(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFio() {
        return Fio;
    }

    public void setFio(String fio) {
        Fio = fio;
    }

    public BigInteger getPassport() {
        return passport;
    }

    public void setPassport(BigInteger passport) {
        this.passport = passport;
    }

    public BigInteger getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(BigInteger telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getRegistrationAddres() {
        return registrationAddres;
    }

    public void setRegistrationAddres(String registrationAddres) {
        this.registrationAddres = registrationAddres;
    }

    public BigInteger getDriversLicenseNumber() {
        return driversLicenseNumber;
    }

    public void setDriversLicenseNumber(BigInteger driversLicenseNumber) {
        this.driversLicenseNumber = driversLicenseNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTrafficPoliceDepartament() {
        return trafficPoliceDepartament;
    }

    public void setTrafficPoliceDepartament(String trafficPoliceDepartament) {
        this.trafficPoliceDepartament = trafficPoliceDepartament;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
