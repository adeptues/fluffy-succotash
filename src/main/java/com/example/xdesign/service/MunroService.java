package com.example.xdesign.service;

import com.example.xdesign.model.Munro;
import com.example.xdesign.service.Criteria;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MunroService {

    private List<Munro> munros;

    public MunroService(List<Munro> munros) {
        this.munros = munros;
    }


    /**
     * Search and filter based on an input criteria
     * @param criteria the criteria to be search
     * @return
     */
    public List<Munro> filter(Criteria criteria) {

        Stream<Munro> munroStream = munros.stream().filter(criteria.buildFilters());

        if (criteria.getLimit().isPresent()) {
            munroStream = munroStream.limit(criteria.getLimit().get());
        }
        if (criteria.buildComparator().isPresent()) {
            return munroStream.sorted(criteria.buildComparator().get()).collect(Collectors.toList());
        }

        return munroStream.collect(Collectors.toList());
    }
}
