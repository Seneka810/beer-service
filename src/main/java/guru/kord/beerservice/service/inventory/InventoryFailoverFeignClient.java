package guru.kord.beerservice.service.inventory;

import guru.kord.beerservice.service.inventory.model.BeerInventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "inventory-failover")
public interface InventoryFailoverFeignClient {

    @GetMapping(value = "/inventory-failover")
    ResponseEntity<List<BeerInventoryDto>> getOnhandInventory();
}
