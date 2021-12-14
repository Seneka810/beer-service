package com.kord.beerservice.service;

import com.kord.beerservice.domain.Beer;
import com.kord.beerservice.repository.BeerRepository;
import com.kord.beerservice.web.controller.NotFoundException;
import com.kord.beerservice.web.mappers.BeerMapper;
import com.kord.beerservice.web.model.BeerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BeerServiceImpl implements BeerService {
    private final BeerRepository repository;
    private final BeerMapper mapper;

    @Autowired
    public BeerServiceImpl(BeerRepository repository, BeerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public BeerDto getById(UUID beerId) {
        return mapper.beerToBeerDto(repository.findById(beerId).orElseThrow(NotFoundException::new));
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        return mapper.beerToBeerDto(repository.save(mapper.beerDtoToBeer(beerDto)));
    }

    @Override
    public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
        Beer beer = repository.findById(beerId).orElseThrow(NotFoundException::new);

        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle().name());
        beer.setUpc(beerDto.getUpc());
        beer.setPrice(beerDto.getPrice());

        return mapper.beerToBeerDto(repository.save(beer));
    }
}
