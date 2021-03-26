package com.example.xdesign.model;

import com.example.xdesign.model.Category;

public class Munro {

    private String name;
    private Float height;
    private String gridRef;
    private Category hillCategory;

    public Munro() {
    }

    public Munro(String name, Float height, String gridRef, Category hillCategory) {
        this.name = name;
        this.height = height;
        this.gridRef = gridRef;
        this.hillCategory = hillCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public String getGridRef() {
        return gridRef;
    }

    public void setGridRef(String gridRef) {
        this.gridRef = gridRef;
    }

    public Category getHillCategory() {
        return hillCategory;
    }

    public void setHillCategory(Category hillCategory) {
        this.hillCategory = hillCategory;
    }
}
