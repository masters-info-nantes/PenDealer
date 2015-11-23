package org.alma.services.bank.application;

import org.alma.services.bank.api.services.IDomainService;
import org.alma.services.bank.domain.services.impl.Service;
import org.alma.services.bank.domain.valueobjects.impl.Data;
import org.alma.services.bank.infra.factories.BankDataFactory;
import org.alma.services.bank.infra.repositories.EventStoreRepo;

/**
 * Created by jeremy on 23/11/15.
 */
public class Application implements IDomainService {

    private IDomainService domainService;

    public Application(){
        this.domainService = new Service();
    }

    @Override
    public double CreditAccount(double amount) {

        double oldCredit = this.domainService.GetCredit();
        EventStoreRepo eventStoreRepo =  new EventStoreRepo();

        double result = this.domainService.CreditAccount(amount);

        Data data = new BankDataFactory().getData("CreditAccount", oldCredit, this.domainService.GetCredit());
        eventStoreRepo.sendEvent(data);

        return result;
    }

    @Override
    public double GetCredit() {
        double oldCredit = this.domainService.GetCredit();
        EventStoreRepo eventStoreRepo =  new EventStoreRepo();

        double result = this.domainService.GetCredit();

        Data data = new BankDataFactory().getData("GetCredit", oldCredit, this.domainService.GetCredit());
        eventStoreRepo.sendEvent(data);

        return result;
    }

    @Override
    public boolean MakeOnlinePayment(double amount) {
        double oldCredit = this.domainService.GetCredit();
        EventStoreRepo eventStoreRepo =  new EventStoreRepo();

        boolean result = this.domainService.MakeOnlinePayment(amount);

        Data data = new BankDataFactory().getData("MakeOnlinePayment", oldCredit, this.domainService.GetCredit());
        eventStoreRepo.sendEvent(data);

        return result;
    }
}
