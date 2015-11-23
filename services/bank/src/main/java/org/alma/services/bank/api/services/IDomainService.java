package org.alma.services.bank.api.services;

/**
 * Created by jeremy on 23/11/15.
 */
public interface IDomainService {
    public int CreditAccount(int amount);
    public int GetCredit();
    public boolean MakeOnlinePayment(int amount);
}
