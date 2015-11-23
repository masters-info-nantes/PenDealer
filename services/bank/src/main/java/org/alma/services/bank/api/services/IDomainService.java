package org.alma.services.bank.api.services;

/**
 * Created by jeremy on 23/11/15.
 */
public interface IDomainService {
    public double CreditAccount(double amount);
    public double GetCredit();
    public boolean MakeOnlinePayment(double amount);
}
