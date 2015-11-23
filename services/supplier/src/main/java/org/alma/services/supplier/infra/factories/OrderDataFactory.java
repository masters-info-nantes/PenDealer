package org.alma.services.supplier.infra.factories;

import org.alma.services.supplier.api.factories.DataFactory;
import org.alma.services.supplier.domain.valueobjects.impl.Data;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jeremy on 23/11/15.
 */
public class OrderDataFactory implements DataFactory {
    public Data getData(String eventType, String productReference, int quantity) {
        Map<String, Object> fieldsMetadata = new HashMap<String, Object>();

        fieldsMetadata.put("timestamp", new DateTime(DateTimeZone.UTC).toString());
        fieldsMetadata.put("eventType", eventType);

        JSONObject metadata = new JSONObject(fieldsMetadata);

        Map<String, Object> fieldsData = new HashMap<String, Object>();

        fieldsData.put("productReference", productReference);
        fieldsData.put("quantity", quantity);

        JSONObject data = new JSONObject(fieldsData);

        return new Data(data.toString(), metadata.toString());
    }
}
