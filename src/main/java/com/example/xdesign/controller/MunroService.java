package com.example.xdesign.controller;

import java.util.List;

public class MunroService {

    private List<Munro> munros;

    public MunroService(List<Munro> munros) {
        this.munros = munros;
    }

    public List<Munro> all(){
        return munros;
    }

    public void filter(){



    }
}
