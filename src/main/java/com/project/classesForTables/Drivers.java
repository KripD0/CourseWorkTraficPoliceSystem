package com.project.classesForTables;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

}
