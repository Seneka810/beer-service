package com.kord.beerservice.web.mappers;

import com.kord.beerservice.entity.Beer;
import com.kord.beerservice.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {
    BeerDto beerToBeerDto(Beer beer);

    Beer beerDtoToBeer(BeerDto beerDto);
}