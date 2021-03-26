package com.example.xdesign;

import com.example.xdesign.model.Category;
import com.example.xdesign.model.Munro;
import com.example.xdesign.service.MunroService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

@SpringBootApplication
public class XdesignApplication {


    @Bean
    public MunroService munroService() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("munrotab_v6.2.csv");
        InputStreamReader inputStreamReader = new InputStreamReader(classPathResource.getInputStream());
        List<String> csvLines = new LinkedList<>();
        try {
            try (BufferedReader br = new BufferedReader(inputStreamReader)) {
                for (String line; (line = br.readLine()) != null; ) {
                    // process the line.
                    csvLines.add(line);
                }
            }
        } finally {
            inputStreamReader.close();
        }

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
