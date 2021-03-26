package com.example.xdesign.controller;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Criteria {
    private Category category;
    private Optional<Integer> limit;
    private Optional<List<SortOrder>> sortOrder;
    private Optional<Float> maxHeight;
    private Optional<Float> minHeight;


    public Criteria(Category category, Integer limit, List<SortOrder> sortOrder, Float maxHeight, Float minHeight) {
        this.category = category;
        this.limit = Optional.ofNullable(limit);
        this.sortOrder = Optional.ofNullable(sortOrder);
        this.maxHeight = Optional.ofNullable(maxHeight);
        this.minHeight = Optional.ofNullable(minHeight);
    }


    public Criteria() {
        limit = Optional.empty();
        sortOrder = Optional.empty();
        minHeight = Optional.empty();
        maxHeight = Optional.empty();
    }

    public Predicate<Munro> buildFilters() {
        Predicate<Munro> categoryPred = null;
        Predicate<Munro> heightFilter = null;

        if (category != null && category != Category.EITHER) {
            categoryPred = f -> f.getHillCategory() == category;
        } else {
            categoryPred = f -> f.getHillCategory() == Category.MUN || f.getHillCategory() == Category.TOP;
        }
        if (maxHeight.isPresent()) {
            heightFilter = f -> f.getHeight() < maxHeight.get();
        }
        if (minHeight.isPresent()) {
            heightFilter = f -> f.getHeight() > minHeight.get();
        }
        if (maxHeight.isPresent() && minHeight.isPresent()) {
            heightFilter = f -> f.getHeight() > minHeight.get() && f.getHeight() < maxHeight.get();
        }

        if (heightFilter != null) {
            return categoryPred.and(heightFilter);
        }
        return categoryPred;
    }

    public Optional<Comparator<Munro>> buildComparator() {
        Comparator<Munro> comparator = null;
        if (this.sortOrder.isPresent()) {
            if (sortOrder.get().size() == 1) {
                SortOrder order = sortOrder.get().get(0);
                comparator = makeComparator(order);
            }
            if (sortOrder.get().size() == 2) {
                SortOrder primarySort = sortOrder.get().get(0);
                SortOrder secondarySort = sortOrder.get().get(1);
                Comparator<Munro> primary = makeComparator(primarySort);
                Comparator<Munro> secondary = makeComparator(secondarySort);

                comparator = secondary.thenComparing(primary);
            }
        }
        return Optional.ofNullable(comparator);
    }

    public Comparator<Munro> makeComparator(SortOrder order) {
        Comparator<Munro> comparator = null;
        if (order == SortOrder.HEIGHTASC) {
            comparator = Comparator.comparing(Munro::getHeight);
        } else if (order == SortOrder.HEIGHTDSC) {
            comparator = Comparator.comparing(Munro::getHeight).reversed();
        } else if (order == SortOrder.NAMEASC) {
            comparator = Comparator.comparing(Munro::getName);
        } else {
            comparator = Comparator.comparing(Munro::getName).reversed();
        }
        return comparator;
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

    public Optional<List<SortOrder>> getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Optional<List<SortOrder>> sortOrder) {
        this.sortOrder = sortOrder;
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
