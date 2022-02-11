package com.kord.beerservice.service.brewing;

import com.kord.beerservice.config.JmsConfig;
import com.kord.beerservice.domain.Beer;
import com.kord.beerservice.events.BeerEvent;
import com.kord.beerservice.events.NewInventoryEvent;
import com.kord.beerservice.repository.BeerRepository;
import com.kord.beerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrewingBeerListener {
    private final BeerRepository beerRepository;
    private final JmsTemplate jmsTemplate;

    @Transactional
    @JmsListener(destination = JmsConfig.BREWING_REQUEST_QUEUE)
    public void listen(BeerEvent beerEvent) {
        BeerDto beerDto = beerEvent.getBeerDto();

        Optional<Beer> beerOptional = beerRepository.findById(beerDto.getId());
        Beer beer = new Beer();

        if(beerOptional.isPresent()) {
            beer = beerOptional.get();
        }

        beerDto.setQuantityOnHand(beer.getQuantityToBrew());

        NewInventoryEvent newInventoryEvent = new NewInventoryEvent(beerDto);

        log.debug("Brewed beer " + beer.getBeerName() + ": QOH: " + beerDto.getQuantityOnHand());

        jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY_QUEUE, newInventoryEvent);
    }
}

