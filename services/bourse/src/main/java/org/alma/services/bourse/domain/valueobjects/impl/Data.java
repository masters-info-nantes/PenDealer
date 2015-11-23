package org.alma.services.bourse.domain.valueobjects.impl;

import org.alma.services.bourse.api.valueobjects.IData;

/**
 * Created by jeremy on 23/11/15.
 */
public class Data implements IData {

    String value;
    String metadata;

    public Data(String value,String metaData) {
        this.value = value;
        this.metadata = metaData;

    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getMetadata() {
        return this.metadata;
    }
}
