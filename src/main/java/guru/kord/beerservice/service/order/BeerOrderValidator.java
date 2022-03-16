package guru.kord.beerservice.service.order;

import guru.kord.beerservice.repository.BeerRepository;
import guru.sfg.brewery.model.BeerOrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderValidator {
    private final BeerRepository beerRepository;

    public boolean validateOrder(BeerOrderDto beerOrder) {
        AtomicInteger beersNotFound = new AtomicInteger();
        beerOrder.getBeerOrderLines().forEach(orderLine -> {
            if(beerRepository.findByUpc(orderLine.getUpc()) == null) {
                beersNotFound.incrementAndGet();
            }
        });

        return beersNotFound.get() == 0;
    }
}
