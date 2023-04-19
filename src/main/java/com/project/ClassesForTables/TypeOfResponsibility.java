package com.project.ClassesForTables;

public class TypeOfResponsibility {
    private Integer id;
    private String group_id;
    private String name;

    public TypeOfResponsibility(Integer id, String group_id, String name) {
        this.id = id;
        this.group_id = group_id;
        this.name = name;
    }

    public TypeOfResponsibility(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
