package guru.kord.beerservice.repository;

import guru.kord.beerservice.domain.Beer;
import guru.kord.beerservice.web.model.BeerStyle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
    Page<Beer> findAllByBeerNameAndBeerStyle(Integer beerName, BeerStyle beerStyle, PageRequest pageRequest);

    Page<Beer> findAllByBeerName(Integer beerName, PageRequest pageRequest);

    Page<Beer> findAllByBeerStyle(BeerStyle beerStyle, PageRequest pageRequest);

    Beer findByUpc(String upc);
}
