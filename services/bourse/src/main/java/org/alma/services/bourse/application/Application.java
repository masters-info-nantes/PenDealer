package org.alma.services.bourse.application;

import org.alma.services.bourse.api.services.IDomainService;
import org.alma.services.bourse.domain.services.impl.Service;
import org.alma.services.bourse.domain.valueobjects.impl.Data;
import org.alma.services.bourse.infra.factories.BourseDataFactory;
import org.alma.services.bourse.infra.repositories.EventStoreRepo;

import java.util.ArrayList;

/**
 * Created by jeremy on 23/11/15.
 */
public class Application implements IDomainService {

    private IDomainService domainService;

    public Application(){
        this.domainService = new Service();
    }

    @Override
    public ArrayList<String> GetCurrencies() {
        ArrayList<String> result = this.domainService.GetCurrencies();
        return result;
    }

    @Override
    public Double Convert(String currency, Double value) {
        EventStoreRepo eventStoreRepo =  new EventStoreRepo();

        double result = this.domainService.Convert(currency, value);

        Data data = new BourseDataFactory().getData("Convert", currency, value, result);
        eventStoreRepo.sendEvent(data);

        return result;
    }
}
