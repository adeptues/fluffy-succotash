package com.example.xdesign.controller;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;

public class Criteria {
    private Category category;
    private Optional<Integer> limit;
    private Optional<SortOrder> heightOrder;
    private Optional<SortOrder> nameOrder;
    private Optional<Float> maxHeight;
    private Optional<Float> minHeight;

    public Criteria(Category category, Integer limit, SortOrder heightOrder, SortOrder nameOrder, Float maxHeight, Float minHeight) {
        this.category = category;
        this.limit = Optional.ofNullable(limit);
        this.heightOrder = Optional.ofNullable(heightOrder);
        this.nameOrder = Optional.ofNullable(nameOrder);
        this.maxHeight = Optional.ofNullable(maxHeight);
        this.minHeight = Optional.ofNullable(minHeight);
    }

    public Criteria() {
        limit = Optional.empty();
        heightOrder = Optional.empty();
        nameOrder = Optional.empty();
        minHeight = Optional.empty();
        maxHeight = Optional.empty();
    }

    public Predicate<Munro> buildFilters(){
        Predicate<Munro> categoryPred = null;
        Predicate<Munro> heightFilter = null;

        if(category != null && category != Category.EITHER){
            categoryPred = f -> f.getHillCategory() == category;
        }else{
            categoryPred = f -> f.getHillCategory() == Category.MUN || f.getHillCategory() == Category.TOP;
        }
        if(maxHeight.isPresent()){
            heightFilter = f -> f.getHeight() < maxHeight.get();
        }
        if (minHeight.isPresent()) {
            heightFilter = f -> f.getHeight() > minHeight.get();
        }
        if(maxHeight.isPresent() && minHeight.isPresent()){
            heightFilter = f -> f.getHeight() > minHeight.get() && f.getHeight() < maxHeight.get();
        }

        if(heightFilter != null){
            return categoryPred.and(heightFilter);
        }
        return categoryPred;
    }

    public Optional<Comparator<Munro>> buildComparator(){
        Comparator<Munro> comparator = null;
        if(nameOrder.isPresent()){
            if(nameOrder.get() == SortOrder.ASC){
                comparator = Comparator.comparing(Munro::getName);
            }else{
                comparator = Comparator.comparing(Munro::getName).reversed();
            }
        }
        if(heightOrder.isPresent()){
            if(heightOrder.get() == SortOrder.ASC){
                comparator = Comparator.comparing(Munro::getHeight);
            }else{
                comparator = Comparator.comparing(Munro::getHeight).reversed();
            }
        }
        if(heightOrder.isPresent() && nameOrder.isPresent()){
            Comparator<Munro> primary = null;
            Comparator<Munro> secondary = null;
            if(nameOrder.get() == SortOrder.ASC){
                primary = Comparator.comparing(Munro::getName);
            }else{
                primary = Comparator.comparing(Munro::getName).reversed();
            }
            if(heightOrder.get() == SortOrder.ASC){
                secondary = comparator.thenComparing(Munro::getHeight);
            }else{
                secondary = Comparator.comparing(Munro::getHeight).reversed();
            }
            comparator = secondary.thenComparing(primary);
        }
        return Optional.ofNullable(comparator);
    }
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Optional<Integer> getLimit() {
        return limit;
    }

    public void setLimit(Optional<Integer> limit) {
        this.limit = limit;
    }

    public Optional<SortOrder> getHeightOrder() {
        return heightOrder;
    }

    public void setHeightOrder(Optional<SortOrder> heightOrder) {
        this.heightOrder = heightOrder;
    }

    public Optional<SortOrder> getNameOrder() {
        return nameOrder;
    }

    public void setNameOrder(Optional<SortOrder> nameOrder) {
        this.nameOrder = nameOrder;
    }

    public Optional<Float> getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(Optional<Float> maxHeight) {
        this.maxHeight = maxHeight;
    }

    public Optional<Float> getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(Optional<Float> minHeight) {
        this.minHeight = minHeight;
    }
}
