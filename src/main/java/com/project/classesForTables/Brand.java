package com.project.classesForTables;

public class Brand {

    private Integer id;

    private String name;

    public Brand(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Brand(){}

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
