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
import java.util.stream.Collectors;

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
        if (sortOrder.isPresent() && sortOrder.get().size() > 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid argument only two sort options may be defined at a time");
        }
        if (sortOrder.isPresent() && sortOrder.get().size() == 2) {
            if (sortOrder.get().stream().distinct().count() == 1) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid argument sortOrder must not contain the same value twice");
            }
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
