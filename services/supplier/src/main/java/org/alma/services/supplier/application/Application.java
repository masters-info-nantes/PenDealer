package org.alma.services.supplier.application;

import org.alma.services.supplier.api.services.IDomainService;
import org.alma.services.supplier.domain.entities.Product;
import org.alma.services.supplier.domain.services.impl.Service;
import org.alma.services.supplier.domain.valueobjects.impl.Data;
import org.alma.services.supplier.infra.factories.OrderDataFactory;
import org.alma.services.supplier.infra.repositories.EventStoreRepo;

import java.util.List;

/**
 * Created by jeremy on 23/11/15.
 */
public class Application implements IDomainService {

    private IDomainService domainService;

    public Application(){
        this.domainService = new Service();
    }

    @Override
    public List<Product> GetProductsList() {
        List<Product> result = this.domainService.GetProductsList();
        return result;
    }

    @Override
    public int GetProductAvailability(String productReference) {

        EventStoreRepo eventStoreRepo =  new EventStoreRepo();

        int result = this.domainService.GetProductAvailability(productReference);

        Data data = new OrderDataFactory().getData("GetProductAvailability", productReference, result);
        eventStoreRepo.sendEvent(data);

        return result;
    }

    @Override
    public Product GetProduct(String productReference) {
        EventStoreRepo eventStoreRepo =  new EventStoreRepo();

        Product result = this.domainService.GetProduct(productReference);

        Data data = new OrderDataFactory().getData("GetProduct", productReference, this.GetProductAvailability(productReference));
        eventStoreRepo.sendEvent(data);

        return result;
    }

    @Override
    public boolean OrderProduct(String productReference, int quantity) {
        EventStoreRepo eventStoreRepo =  new EventStoreRepo();

        boolean result = this.domainService.OrderProduct(productReference, quantity);

        Data data = new OrderDataFactory().getData("OrderProduct", productReference, quantity);
        eventStoreRepo.sendEvent(data);

        return result;
    }
}
