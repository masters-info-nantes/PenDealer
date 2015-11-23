package org.alma.services.bank.domain.services.impl;

import org.alma.services.bank.api.services.IDomainService;

/**
 * Created by jeremy on 23/11/15.
 */
public class Service implements IDomainService {

    private double credit = 25;

    // Transfer money to the account
    public double CreditAccount(double amount){
        this.credit += amount;
        return this.credit;
    }

    // Consult the current credit
    public double GetCredit(){
        return this.credit;
    }

    // Make an online payment
    public boolean MakeOnlinePayment(double amount){
        double remainingCredit = this.credit - amount;

        if(remainingCredit >= 0){
            this.credit = remainingCredit;
            return true;
        }

        return false;
    }
}
