package com.example.xdesign;

import com.example.xdesign.controller.Category;
import com.example.xdesign.controller.Munro;
import com.example.xdesign.controller.MunroService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

@SpringBootApplication
public class XdesignApplication {


    @Bean
    public MunroService munroService() throws IOException {
        //load from csv
        //intereested in name ,height ,grid reference,post 1997 hill categoryhill category,
//5,9,13,27
        File file = new File("/home/tom/workspace/xdesign/java-api-takehome/munrotab_v6.2.csv");

        List<String> csvLines = Files.readAllLines(file.toPath());
        List<Munro> munros = new LinkedList<Munro>();
        csvLines.remove(0);
        for (String line : csvLines) {
            String[] parts = line.split(",");
            if (parts.length > 27) {
                String name = parts[6];
                String height = parts[10];
                String gridRef = parts[14];
                String category = parts[28];
                if (category != null && !category.isEmpty()) {
                    Munro munro = new Munro();
                    munro.setGridRef(gridRef);
                    munro.setName(name);
                    munro.setHeight(Float.parseFloat(height));
                    munro.setHillCategory(Category.valueOf(category));
                    munros.add(munro);
                }
            }


        }
        return new MunroService(munros);
    }


    public static void main(String[] args) {
        SpringApplication.run(XdesignApplication.class, args);
    }

}
