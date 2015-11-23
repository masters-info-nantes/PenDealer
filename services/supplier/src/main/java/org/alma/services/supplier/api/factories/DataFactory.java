package org.alma.services.supplier.api.factories;

import org.alma.services.supplier.domain.valueobjects.impl.Data;

/**
 * Created by jeremy on 23/11/15.
 */
public interface DataFactory {
    public Data getData(String eventType, String productReference, int quantity);
}
