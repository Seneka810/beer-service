package com.kord.beerservice.web.mappers;

import com.kord.beerservice.domain.Beer;
import com.kord.beerservice.service.inventory.BeerInventoryService;
import com.kord.beerservice.web.model.BeerDto;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BeerMapperDecorator implements BeerMapper {
    private BeerInventoryService beerInventoryService;
    private BeerMapper mapper;

    @Autowired
    public void setBeerInventoryService(BeerInventoryService beerInventoryService) {
        this.beerInventoryService = beerInventoryService;
    }

    @Autowired
    public void setMapper(BeerMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public BeerDto beerToBeerDto(Beer beer) {
        return mapper.beerToBeerDto(beer);
    }

    @Override
    public Beer beerDtoToBeer(BeerDto beerDto) {
        return mapper.beerDtoToBeer(beerDto);
    }

    @Override
    public BeerDto beerToBeerDtoWithInventory(Beer beer) {
        BeerDto dto = mapper.beerToBeerDto(beer);
        dto.setQuantityOnHand(beerInventoryService.getOnhandInventory(beer.getId()));
        return dto;
    }
}
