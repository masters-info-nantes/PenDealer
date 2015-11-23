package org.alma.services.bourse.infra.factories;

import org.alma.services.bourse.api.factories.DataFactory;
import org.alma.services.bourse.domain.valueobjects.impl.Data;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jeremy on 23/11/15.
 */
public class BourseDataFactory implements DataFactory {
    public Data getData(String eventType, String currency, double fromValue, double toValue) {
        Map<String, Object> fieldsMetadata = new HashMap<String, Object>();

        fieldsMetadata.put("timestamp", new DateTime(DateTimeZone.UTC).toString());
        fieldsMetadata.put("eventType", eventType);

        JSONObject metadata = new JSONObject(fieldsMetadata);

        Map<String, Object> fieldsData = new HashMap<String, Object>();

        fieldsData.put("currency", currency);
        fieldsData.put("fromValue", fromValue);
        fieldsData.put("toValue", toValue);

        JSONObject data = new JSONObject(fieldsData);

        return new Data(data.toString(), metadata.toString());
    }
}
