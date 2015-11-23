package org.alma.services.bank.api.factories;

import org.alma.services.bank.domain.valueobjects.impl.Data;

/**
 * Created by jeremy on 23/11/15.
 */
public interface DataFactory {
    public Data getData(String eventType, double oldCredit, double newCredit);
}
