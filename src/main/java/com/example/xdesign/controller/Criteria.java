package com.example.xdesign.controller;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;

public class Criteria {
    private Category category;
    private Integer limit;
    private SortOrder heightOrder;
    private SortOrder nameOrder;
    private Float maxHeight;
    private Float minHeight;

    public Criteria(Category category, Integer limit, SortOrder heightOrder, SortOrder nameOrder, Float maxHeight, Float minHeight) {
        this.category = category;
        this.limit = limit;
        this.heightOrder = heightOrder;
        this.nameOrder = nameOrder;
        this.maxHeight = maxHeight;
        this.minHeight = minHeight;
    }

    public Predicate<Munro> buildFilters(){
        Predicate<Munro> categoryPred = null;
        Predicate<Munro> heightFilter = null;

        if(category != null && category != Category.EITHER){
            categoryPred = f -> f.getHillCategory() == category;
        }else{
            categoryPred = f -> f.getHillCategory() == Category.MUN || f.getHillCategory() == Category.TOP;
        }
        if(maxHeight != null){
            heightFilter = f -> f.getHeight() < maxHeight;
        }
        if (minHeight != null) {
            heightFilter = f -> f.getHeight() > minHeight;
        }
        if(maxHeight != null && minHeight != null){
            heightFilter = f -> f.getHeight() > minHeight && f.getHeight() < maxHeight;
        }

        if(heightFilter != null){
            return categoryPred.and(heightFilter);
        }
        return categoryPred;
    }

    public Optional<Comparator<Munro>> buildComparator(){
        Comparator<Munro> comparator = null;
        if(nameOrder != null){
            if(nameOrder == SortOrder.ASC){
                comparator = Comparator.comparing(Munro::getName);
            }else{
                comparator = Comparator.comparing(Munro::getName).reversed();
            }
        }
        if(heightOrder != null){
            if(heightOrder == SortOrder.ASC){
                comparator = Comparator.comparing(Munro::getHeight);
            }else{
                comparator = Comparator.comparing(Munro::getHeight).reversed();
            }
        }
        if(heightOrder != null && nameOrder != null){
            if(nameOrder == SortOrder.ASC){
                comparator = Comparator.comparing(Munro::getName);
            }else{
                comparator = Comparator.comparing(Munro::getName).reversed();
            }
            if(heightOrder == SortOrder.ASC){
                comparator = comparator.thenComparing(Munro::getHeight);
            }else{
                Comparator<Munro> temp = Comparator.comparing(Munro::getHeight).reversed();
                comparator = comparator.thenComparing(temp);
            }

        }
        return Optional.ofNullable(comparator);
    }
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public SortOrder getHeightOrder() {
        return heightOrder;
    }

    public void setHeightOrder(SortOrder heightOrder) {
        this.heightOrder = heightOrder;
    }

    public SortOrder getNameOrder() {
        return nameOrder;
    }

    public void setNameOrder(SortOrder nameOrder) {
        this.nameOrder = nameOrder;
    }

    public Float getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(Float maxHeight) {
        this.maxHeight = maxHeight;
    }

    public Float getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(Float minHeight) {
        this.minHeight = minHeight;
    }
}
