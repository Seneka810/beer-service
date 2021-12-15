package com.kord.beerservice.repository;

import com.kord.beerservice.domain.Beer;
import com.kord.beerservice.web.model.BeerStyle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface BeerRepository extends PagingAndSortingRepository<Beer, UUID> {
    Page<Beer> findAllByBeerNameAndBeerStyle(Integer beerName, BeerStyle beerStyle, PageRequest pageRequest);

    Page<Beer> findAllByBeerName(Integer beerName, PageRequest pageRequest);

    Page<Beer> findAllByBeerStyle(BeerStyle beerStyle, PageRequest pageRequest);

    Beer findByUpc(String upc);
}
