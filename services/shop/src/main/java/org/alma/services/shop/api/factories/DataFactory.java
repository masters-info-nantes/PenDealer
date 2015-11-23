package org.alma.services.shop.api.factories;

import org.alma.services.shop.domain.valueobjects.impl.Data;

/**
 * Created by jeremy on 23/11/15.
 */
public interface DataFactory {
    public Data getData(String eventType, String productReference, int quantity);
}
