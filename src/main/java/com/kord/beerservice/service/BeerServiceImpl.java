package com.kord.beerservice.service;

import com.kord.beerservice.domain.Beer;
import com.kord.beerservice.repository.BeerRepository;
import com.kord.beerservice.web.controller.NotFoundException;
import com.kord.beerservice.web.mappers.BeerMapper;
import com.kord.beerservice.web.model.BeerDto;
import com.kord.beerservice.web.model.BeerPagedList;
import com.kord.beerservice.web.model.BeerStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BeerServiceImpl implements BeerService {
    private final BeerRepository repository;
    private final BeerMapper mapper;

    @Autowired
    public BeerServiceImpl(BeerRepository repository, BeerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Cacheable(cacheNames = "beerCache", key = "#beerId", condition = "#showInventoryOnHand == false")
    @Override
    public BeerDto getById(UUID beerId, Boolean showInventoryOnHand) {

        if(Boolean.TRUE.equals(showInventoryOnHand)) {
            return mapper.beerToBeerDtoWithInventory(repository.findById(beerId).orElseThrow(NotFoundException::new));
        } else {
            return mapper.beerToBeerDto(repository.findById(beerId).orElseThrow(NotFoundException::new));
        }
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

    @Cacheable(cacheNames = "beerListCache", condition = "#showInventoryOnHand == false")
    @Override
    public BeerPagedList listBeers(Integer beerName, BeerStyle beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand) {

        BeerPagedList beerPagedList;
        Page<Beer> beerPage;

        if (!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            //search both
            beerPage = repository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        } else if (!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyle)) {
            //search beer_service name
            beerPage = repository.findAllByBeerName(beerName, pageRequest);
        } else if (StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            //search beer_service style
            beerPage = repository.findAllByBeerStyle(beerStyle, pageRequest);
        } else {
            beerPage = repository.findAll(pageRequest);
        }

        if(Boolean.TRUE.equals(showInventoryOnHand)) {

            beerPagedList = new BeerPagedList(beerPage
                    .getContent()
                    .stream()
                    .map(mapper::beerToBeerDtoWithInventory)
                    .collect(Collectors.toList()),
                    PageRequest
                            .of(beerPage.getPageable().getPageNumber(),
                                    beerPage.getPageable().getPageSize()),
                    beerPage.getTotalElements());
        } else {
            beerPagedList = new BeerPagedList(beerPage
                    .getContent()
                    .stream()
                    .map(mapper::beerToBeerDto)
                    .collect(Collectors.toList()),
                    PageRequest
                            .of(beerPage.getPageable().getPageNumber(),
                                    beerPage.getPageable().getPageSize()),
                    beerPage.getTotalElements());

        }

        return beerPagedList;
    }

    @Cacheable(cacheNames = "beerUpcCache")
    @Override
    public BeerDto getByUpc(String upc) {
        return mapper.beerToBeerDto(repository.findByUpc(upc));
    }
}
