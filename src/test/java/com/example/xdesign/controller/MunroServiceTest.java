package com.example.xdesign.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MunroServiceTest {
    private List<Munro> munros = null;
    @BeforeEach
    void setUp() {
        Munro munro = new Munro("Tom", 101.0f, "gridref", Category.MUN);
        Munro munro1 = new Munro("Thomas", 102.0f, "gridref", Category.TOP);
        Munro munro2 = new Munro("Bob", 107.0f, "gridref", Category.MUN);
        Munro munro3 = new Munro("Tigger", 103.0f, "gridref", Category.TOP);
        Munro munro4 = new Munro("Barry", 104.0f, "gridref", Category.MUN);
        Munro munro5 = new Munro("Adam", 106.0f, "gridref", Category.TOP);
        Munro munro6 = new Munro("John", 105.0f, "gridref", Category.MUN);
        Munro munro7 = new Munro("Luke", 108.0f, "gridref", Category.TOP);
        Munro munro8 = new Munro("Callam", 109.0f, "gridref", Category.MUN);
        Munro munro9 = new Munro("Lydon", 101.0f, "gridref", Category.TOP);
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

    @Test
    void filterApplyLimit() {

        Criteria criteria = new Criteria(Category.EITHER, 5, SortOrder.ASC, SortOrder.ASC, 108f, 101f);
        MunroService munroService = new MunroService(munros);
        List<Munro> filtered = munroService.filter(criteria);
        assertEquals(5,filtered.size());
    }
}