package org.alma.services.bourse.api.factories;

import org.alma.services.bourse.domain.valueobjects.impl.Data;

/**
 * Created by jeremy on 23/11/15.
 */
public interface DataFactory {
    public Data getData(String eventType, String currency, double rate);
}
