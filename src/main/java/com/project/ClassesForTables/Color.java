package com.project.ClassesForTables;

public class Color {

    private Integer id;

    private String name;

    public Color(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Color(){}

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
