package com.project.ClassesForTables;

public class Region {

    private Integer id;

    private String name;

    public Region(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Region(){}

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
