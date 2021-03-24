package com.example.xdesign.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiController {

    @Autowired
    private MunroService munroService;

    @RequestMapping("/api/munro")
    public List<Munro> munro(){
        return munroService.all();
    }
}
