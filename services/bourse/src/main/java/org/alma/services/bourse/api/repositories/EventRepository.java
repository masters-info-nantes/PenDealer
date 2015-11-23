package org.alma.services.bourse.api.repositories;

import org.alma.services.bourse.api.valueobjects.IData;

/**
 * Created by jeremy on 23/11/15.
 */
public interface EventRepository {
    void sendEvent(IData event);
}
