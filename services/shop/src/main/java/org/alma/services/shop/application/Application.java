package org.alma.services.shop.application;

import org.alma.services.shop.api.services.IDomainService;
import org.alma.services.shop.domain.entities.CartItem;
import org.alma.services.shop.domain.services.impl.Service;
import org.alma.services.shop.domain.valueobjects.impl.Data;
import org.alma.services.shop.infra.factories.OrderDataFactory;
import org.alma.services.shop.infra.repositories.EventStoreRepo;
import org.alma.services.supplier.domain.services.impl.SupplierStub;
import org.apache.axis2.AxisFault;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by jeremy on 23/11/15.
 */
public class Application implements IDomainService {

    private IDomainService domainService;

    public Application() throws AxisFault {
        this.domainService = new Service();
    }

    @Override
    public String[] GetCurrencies() throws RemoteException {
        String[] result = this.domainService.GetCurrencies();
        return result;
    }

    @Override
    public SupplierStub.Product GetProduct(String productReference) throws AxisFault, RemoteException {
        EventStoreRepo eventStoreRepo =  new EventStoreRepo();

        SupplierStub.Product result = this.domainService.GetProduct(productReference);

        Data data = new OrderDataFactory().getData("GetProduct", productReference, 0);
        eventStoreRepo.sendEvent(data);

        return result;
    }

    @Override
    public SupplierStub.Product[] GetProductsList() throws RemoteException {
        SupplierStub.Product[] result = this.domainService.GetProductsList();
        return result;
    }

    @Override
    public boolean AddToCart(String productReference) throws RemoteException {
        EventStoreRepo eventStoreRepo =  new EventStoreRepo();

        boolean result = this.domainService.AddToCart(productReference);

        Data data = new OrderDataFactory().getData("AddToCart", productReference, 1);
        eventStoreRepo.sendEvent(data);

        return result;
    }

    @Override
    public void RemoveFromCart(String productReference) {
        EventStoreRepo eventStoreRepo =  new EventStoreRepo();

        this.domainService.RemoveFromCart(productReference);

        Data data = new OrderDataFactory().getData("RemoveFromCart", productReference, 1);
        eventStoreRepo.sendEvent(data);
    }

    @Override
    public List<CartItem> GetCart(String currency) throws RemoteException {
        List<CartItem> result = this.domainService.GetCart(currency);
        return result;
    }

    @Override
    public boolean ProcessOrder() throws AxisFault, RemoteException {
        EventStoreRepo eventStoreRepo =  new EventStoreRepo();

        boolean result = this.domainService.ProcessOrder();

        for(CartItem item : this.domainService.GetCart("EUR")) {
            Data data = new OrderDataFactory().getData("ProcessOrder", item.getProduct().getReference(), item.getQuantity());
            eventStoreRepo.sendEvent(data);
        }

        return result;
    }
}
