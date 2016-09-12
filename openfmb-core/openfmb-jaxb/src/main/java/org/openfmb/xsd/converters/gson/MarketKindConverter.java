package org.openfmb.xsd.converters.gson;

import java.lang.reflect.Type;

import org.openfmb.xsd._2015._12.openfmb.commonmodule.MarketKind;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class MarketKindConverter
{
    public static class Serializer implements JsonSerializer<MarketKind>
    {
        @Override
        public JsonElement serialize(MarketKind market, Type type, JsonSerializationContext jsonSerializationContext)
        {
            return new JsonPrimitive(market.value());
        }
    }

    public static class Deserializer implements JsonDeserializer<MarketKind>
    {
        @Override
        public MarketKind deserialize(JsonElement jsonElement, Type type,
                JsonDeserializationContext jsonDeserializationContext)
        {
            try
            {
                return MarketKind.valueOf(jsonElement.getAsString());
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return null;
            }
        }
    }
}
