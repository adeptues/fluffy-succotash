package com.example.xdesign.controller;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestExecutionListeners;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class CriteriaTest {
    private List<Munro> munros;

    @BeforeEach
    void setUp() {
        Munro munro = new Munro("alpha", 101.0f, "gridref", Category.MUN);
        Munro munro1 = new Munro("blpha", 102.0f, "gridref", Category.TOP);
        Munro munro2 = new Munro("clpha", 107.0f, "gridref", Category.MUN);
        Munro munro3 = new Munro("dlpha", 103.0f, "gridref", Category.TOP);
        Munro munro4 = new Munro("elpha", 104.0f, "gridref", Category.MUN);
        Munro munro5 = new Munro("flpha", 106.0f, "gridref", Category.TOP);
        Munro munro6 = new Munro("glpha", 105.0f, "gridref", Category.MUN);
        Munro munro7 = new Munro("hlpha", 108.0f, "gridref", Category.TOP);
        Munro munro8 = new Munro("ilpha", 109.0f, "gridref", Category.MUN);
        Munro munro9 = new Munro("jlpha", 101.0f, "gridref", Category.TOP);
        List<Munro> munroList = new LinkedList<>();
        munroList.add(munro);
        munroList.add(munro1);
        munroList.add(munro2);
        munroList.add(munro3);
        munroList.add(munro4);
        munroList.add(munro5);
        munroList.add(munro6);
        munroList.add(munro7);
        munroList.add(munro8);
        munroList.add(munro9);
        munros = munroList;

    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void buildFilters() {
        Criteria criteria = new Criteria();
        criteria.setCategory(Category.EITHER);
        Predicate<Munro> filter = criteria.buildFilters();
        List<Munro> actual = munros.stream().filter(filter).collect(Collectors.toList());
        assertEquals(munros, actual);
        assertEquals(actual.size(), munros.size());
    }

    @Test
    void buildFiltersWithTwoCriteria() {
        Criteria criteria = new Criteria(Category.EITHER, null, null, 105f, null);
        Predicate<Munro> filter = criteria.buildFilters();
        List<Munro> actual = munros.stream().filter(filter).collect(Collectors.toList());
        assertEquals(5, actual.size());
    }

    @Test
    void buildFiltersWithHeightRange() {
        Criteria criteria = new Criteria(Category.EITHER, null, null, 107f, 102f);
        Predicate<Munro> filter = criteria.buildFilters();
        List<Munro> actual = munros.stream().filter(filter).collect(Collectors.toList());
        assertEquals(4, actual.size());
    }

    @Test
    void buildFiltersWithHeightRange2() {
        Criteria criteria = new Criteria(Category.TOP, null, null, 107f, 102f);
        Predicate<Munro> filter = criteria.buildFilters();
        List<Munro> actual = munros.stream().filter(filter).collect(Collectors.toList());
        assertEquals(2, actual.size());
    }

    @Test
    void buildComparators() {
        List<SortOrder> sortOrder = new LinkedList<SortOrder>();
        sortOrder.add(SortOrder.HEIGHTASC);

        Criteria criteria = new Criteria(Category.EITHER, null, sortOrder, null, null);
        Optional<Comparator<Munro>> comparator = criteria.buildComparator();
        assertTrue(comparator.isPresent());
        munros.sort(comparator.get());
        assertEquals(munros.get(0).getHeight(), 101f);
        assertEquals(munros.get(1).getHeight(), 101f);
        assertEquals(munros.get(2).getHeight(), 102f);
    }

    @Test
    void buildComparatorsHeightDSC() {
        List<SortOrder> sortOrder = new LinkedList<SortOrder>();
        sortOrder.add(SortOrder.HEIGHTDSC);
        Criteria criteria = new Criteria(Category.EITHER, null, sortOrder, null, null);
        Optional<Comparator<Munro>> comparator = criteria.buildComparator();
        assertTrue(comparator.isPresent());
        munros.sort(comparator.get());
        assertEquals(munros.get(0).getHeight(), 109f);
        assertEquals(munros.get(1).getHeight(), 108f);
        assertEquals(munros.get(2).getHeight(), 107f);
    }

    @Test
    void buildComparatorsNameASC() {
        List<SortOrder> sortOrder = new LinkedList<SortOrder>();
        sortOrder.add(SortOrder.NAMEASC);
        Criteria criteria = new Criteria(Category.EITHER, null, sortOrder, null, null);
        Optional<Comparator<Munro>> comparator = criteria.buildComparator();
        assertTrue(comparator.isPresent());
        munros.sort(comparator.get());
        assertEquals(munros.get(0).getName(), "alpha");
        assertEquals(munros.get(1).getName(), "blpha");
        assertEquals(munros.get(2).getName(), "clpha");
    }

    @Test
    void buildComparatorsNameDSC() {
        List<SortOrder> sortOrder = new LinkedList<SortOrder>();
        sortOrder.add(SortOrder.NAMEDSC);
        Criteria criteria = new Criteria(Category.EITHER, null, sortOrder, null, null);
        Optional<Comparator<Munro>> comparator = criteria.buildComparator();
        assertTrue(comparator.isPresent());
        munros.sort(comparator.get());
        assertEquals(munros.get(0).getName(), "jlpha");
        assertEquals(munros.get(1).getName(), "ilpha");
        assertEquals(munros.get(2).getName(), "hlpha");
    }

    @Test
    void buildComparatorsCombination() {
        Munro munro = new Munro("Bob", 101.0f, "gridref", Category.MUN);
        Munro munro1 = new Munro("Bob", 102.0f, "gridref", Category.TOP);
        Munro munro2 = new Munro("Tom", 107.0f, "gridref", Category.MUN);
        Munro munro3 = new Munro("Tom", 103.0f, "gridref", Category.TOP);
        Munro munro4 = new Munro("Toma", 103.0f, "gridref", Category.TOP);
        munros.clear();
        munros.add(munro);
        munros.add(munro1);
        munros.add(munro2);
        munros.add(munro4);
        munros.add(munro3);
        List<SortOrder> sortOrder = new LinkedList<SortOrder>();
        sortOrder.add(SortOrder.NAMEASC);
        sortOrder.add(SortOrder.HEIGHTASC);
        Criteria criteria = new Criteria(Category.EITHER, null, sortOrder, null, null);
        Optional<Comparator<Munro>> comparator = criteria.buildComparator();
        assertTrue(comparator.isPresent());

        Collections.sort(munros, comparator.get());
        assertEquals("Bob", munros.get(0).getName());
        assertEquals(101f, munros.get(0).getHeight());
        assertEquals("Bob", munros.get(1).getName());
        assertEquals(102f, munros.get(1).getHeight());
        assertEquals("Tom", munros.get(2).getName());
        assertEquals(103f, munros.get(2).getHeight());
        assertEquals("Toma", munros.get(3).getName());
        assertEquals(103f, munros.get(3).getHeight());
        assertEquals("Tom", munros.get(4).getName());
        assertEquals(107f, munros.get(4).getHeight());


    }
}