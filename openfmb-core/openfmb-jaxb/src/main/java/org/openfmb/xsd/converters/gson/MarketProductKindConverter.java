package org.openfmb.xsd.converters.gson;

import java.lang.reflect.Type;

import org.openfmb.xsd._2015._12.openfmb.commonmodule.MarketProductKind;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class MarketProductKindConverter
{
    public static class Serializer implements JsonSerializer<MarketProductKind>
    {
        @Override
        public JsonElement serialize(MarketProductKind marketProduct, Type type, JsonSerializationContext jsonSerializationContext)
        {
            return new JsonPrimitive(marketProduct.value());
        }
    }

    public static class Deserializer implements JsonDeserializer<MarketProductKind>
    {
        @Override
        public MarketProductKind deserialize(JsonElement jsonElement, Type type,
                JsonDeserializationContext jsonDeserializationContext)
        {
            try
            {
                return MarketProductKind.valueOf(jsonElement.getAsString());
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return null;
            }
        }
    }
}
