package com.kord.beerservice.events;

import com.kord.beerservice.web.model.BeerDto;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
public class BeerEvent implements Serializable {

    static final long serialVersionUID = -5465587622001599752L;

    private BeerDto beerDto;

    public BeerEvent(BeerDto beerDto) {
        this.beerDto = beerDto;
    }
}

