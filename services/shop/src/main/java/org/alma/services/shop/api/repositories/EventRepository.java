package org.alma.services.shop.api.repositories;

import org.alma.services.shop.api.valueobjects.IData;

/**
 * Created by jeremy on 23/11/15.
 */
public interface EventRepository {
    void sendEvent(IData event);
}
