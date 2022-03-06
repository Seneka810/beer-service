package guru.sfg.common.events;

import guru.kord.beerservice.web.model.BeerDto;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeerEvent implements Serializable {

    static final long serialVersionUID = -5465587622001599752L;

    private BeerDto beerDto;

}

