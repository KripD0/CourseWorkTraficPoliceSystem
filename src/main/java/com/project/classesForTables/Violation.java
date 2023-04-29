package com.project.classesForTables;

public class Violation {

    private Integer id;

    private String name;

    public Violation(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Violation(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
