package guru.kord.beerservice.service.brewing;

import guru.kord.beerservice.config.JmsConfig;
import guru.kord.beerservice.domain.Beer;
import guru.sfg.common.events.BrewBeerEvent;
import guru.kord.beerservice.repository.BeerRepository;
import guru.kord.beerservice.service.inventory.BeerInventoryService;
import guru.kord.beerservice.web.mappers.BeerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrewingService {
    private final BeerRepository beerRepository;
    private final BeerInventoryService beerInventoryService;
    private final JmsTemplate jmsTemplate;
    private final BeerMapper beerMapper;

    @Scheduled(fixedRate = 5000)
    public void checkForLowInventory() {
        List<Beer> beers = beerRepository.findAll();

        beers.forEach(beer -> {
            Integer invQOH = beerInventoryService.getOnhandInventory(beer.getId());
            BrewBeerEvent brewBeerEvent = new BrewBeerEvent(beerMapper.beerToBeerDto(beer));

            log.debug("Min on hand is: " + beer.getMinOnHand());
            log.debug("Inventory is: " + invQOH);

            if(beer.getMinOnHand() >= invQOH) {
                jmsTemplate.convertAndSend(JmsConfig.BREWING_REQUEST_QUEUE, brewBeerEvent);
            }
        });
    }
}

