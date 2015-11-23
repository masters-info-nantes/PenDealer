package org.alma.services.bank.domain.services.impl;

import org.alma.services.bank.api.services.IDomainService;

/**
 * Created by jeremy on 23/11/15.
 */
public class Service implements IDomainService {

    private int credit = 25;

    // Transfer money to the account
    public int CreditAccount(int amount){
        this.credit += amount;
        return this.credit;
    }

    // Consult the current credit
    public int GetCredit(){
        return this.credit;
    }

    // Make an online payment
    public boolean MakeOnlinePayment(int amount){
        int remainingCredit = this.credit - amount;

        if(remainingCredit >= 0){
            this.credit = remainingCredit;
            return true;
        }

        return false;
    }
}
