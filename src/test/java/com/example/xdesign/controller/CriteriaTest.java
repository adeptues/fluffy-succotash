package com.example.xdesign.controller;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestExecutionListeners;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
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
        Criteria criteria = new Criteria(Category.EITHER, null, null, null, null, null);
        Predicate<Munro> filter = criteria.buildFilters();
        List<Munro> actual = munros.stream().filter(filter).collect(Collectors.toList());
        assertEquals(munros, actual);
        assertEquals(actual.size(), munros.size());
    }

    @Test
    void buildFiltersWithTwoCriteria() {
        Criteria criteria = new Criteria(Category.EITHER, null, null, null, 105f, null);
        Predicate<Munro> filter = criteria.buildFilters();
        List<Munro> actual = munros.stream().filter(filter).collect(Collectors.toList());
        assertEquals(5, actual.size());
    }

    @Test
    void buildFiltersWithHeightRange() {
        Criteria criteria = new Criteria(Category.EITHER, null, null, null, 107f, 102f);
        Predicate<Munro> filter = criteria.buildFilters();
        List<Munro> actual = munros.stream().filter(filter).collect(Collectors.toList());
        assertEquals(4, actual.size());
    }

    @Test
    void buildFiltersWithHeightRange2() {
        Criteria criteria = new Criteria(Category.TOP, null, null, null, 107f, 102f);
        Predicate<Munro> filter = criteria.buildFilters();
        List<Munro> actual = munros.stream().filter(filter).collect(Collectors.toList());
        assertEquals(2, actual.size());
    }

    @Test
    void buildComparators(){
        Criteria criteria = new Criteria(Category.EITHER, null, SortOrder.ASC, null, null, null);
        Optional<Comparator<Munro>> comparator = criteria.buildComparator();
        assertTrue(comparator.isPresent());
        munros.sort(comparator.get());
        assertEquals(munros.get(0).getHeight(),101f);
        assertEquals(munros.get(1).getHeight(),101f);
        assertEquals(munros.get(2).getHeight(),102f);
    }

    @Test
    void buildComparatorsHeightDSC(){
        Criteria criteria = new Criteria(Category.EITHER, null, SortOrder.DESC, null, null, null);
        Optional<Comparator<Munro>> comparator = criteria.buildComparator();
        assertTrue(comparator.isPresent());
        munros.sort(comparator.get());
        assertEquals(munros.get(0).getHeight(),109f);
        assertEquals(munros.get(1).getHeight(),108f);
        assertEquals(munros.get(2).getHeight(),107f);
    }

    @Test
    void buildComparatorsNameASC(){
        Criteria criteria = new Criteria(Category.EITHER, null, null, SortOrder.ASC, null, null);
        Optional<Comparator<Munro>> comparator = criteria.buildComparator();
        assertTrue(comparator.isPresent());
        munros.sort(comparator.get());
        assertEquals(munros.get(0).getName(),"alpha");
        assertEquals(munros.get(1).getName(),"blpha");
        assertEquals(munros.get(2).getName(),"clpha");
    }

    @Test
    void buildComparatorsNameDSC(){
        Criteria criteria = new Criteria(Category.EITHER, null, null, SortOrder.DESC, null, null);
        Optional<Comparator<Munro>> comparator = criteria.buildComparator();
        assertTrue(comparator.isPresent());
        munros.sort(comparator.get());
        assertEquals(munros.get(0).getName(),"jlpha");
        assertEquals(munros.get(1).getName(),"ilpha");
        assertEquals(munros.get(2).getName(),"hlpha");
    }

    @Test
    void buildComparatorsCombination(){
        Criteria criteria = new Criteria(Category.EITHER, null, SortOrder.DESC, SortOrder.ASC, null, null);
        Optional<Comparator<Munro>> comparator = criteria.buildComparator();
        assertTrue(comparator.isPresent());
        munros.sort(comparator.get());
        assertEquals(munros.get(0).getName(),"alpha");
        assertEquals(munros.get(1).getName(),"jlpha");
        assertEquals(munros.get(2).getName(),"blpha");
    }
}