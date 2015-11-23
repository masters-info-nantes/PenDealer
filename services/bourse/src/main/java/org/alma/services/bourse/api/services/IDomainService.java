package org.alma.services.bourse.api.services;

import java.util.ArrayList;

/**
 * Created by jeremy on 23/11/15.
 */
public interface IDomainService {
    public ArrayList<String> GetCurrencies();
    public Double Convert(String currency, Double value);
}
