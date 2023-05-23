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
public class Decree {

    private Integer id;
    private BigInteger dln_number;
    private String violation;
    private Date date;
    private String vehicle;
    private String region;
    private String status;
    private String responsibility;

}
