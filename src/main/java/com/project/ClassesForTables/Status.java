package com.project.ClassesForTables;

public class Status {

    private Integer id;

    private String name;

    public Status(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Status(){}

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
