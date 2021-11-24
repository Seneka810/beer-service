package com.kord.beerservice.repository;

import com.kord.beerservice.entity.Beer;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface BeerRepository extends CrudRepository<Beer, UUID> {
}
