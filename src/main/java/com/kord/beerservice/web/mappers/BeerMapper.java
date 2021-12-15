package com.kord.beerservice.web.mappers;

import com.kord.beerservice.domain.Beer;
import com.kord.beerservice.web.model.BeerDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {
    BeerDto beerToBeerDto(Beer beer);

    BeerDto beerToBeerDtoWithInventory(Beer beer);

    Beer beerDtoToBeer(BeerDto beerDto);
}
