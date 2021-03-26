package com.example.xdesign.controller;

import com.example.xdesign.model.Category;
import com.example.xdesign.model.Munro;
import com.example.xdesign.model.SortOrder;
import com.example.xdesign.service.Criteria;
import com.example.xdesign.service.MunroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class ApiController {

    @Autowired
    private MunroService munroService;

    @RequestMapping(value = "/api/munro", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Munro> munro(@RequestParam Optional<Category> category, @RequestParam Optional<Float> minHeight,
                             @RequestParam Optional<Float> maxHeight, @RequestParam Optional<Integer> limit,
                             @RequestParam Optional<List<SortOrder>> sortOrder) {

        if (minHeight.isPresent() && maxHeight.isPresent() && minHeight.get() > maxHeight.get()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid argument minHeight must be less than maxHeight");
        }
        Criteria criteria = new Criteria();
        criteria.setCategory(category.orElse(Category.EITHER));
        criteria.setLimit(limit);
        criteria.setSortOrder(sortOrder);
        criteria.setMinHeight(minHeight);
        criteria.setMaxHeight(maxHeight);


        return munroService.filter(criteria);
    }
}
