package org.openfmb.xsd.converters.gson;

import java.lang.reflect.Type;

import org.openfmb.xsd._2015._12.openfmb.commonmodule.UnitSymbolKind;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class UnitSymbolKindConverter
{
    public static class Serializer implements JsonSerializer<UnitSymbolKind>
    {
        @Override
        public JsonElement serialize(UnitSymbolKind unitSymbolKind, Type type, JsonSerializationContext jsonSerializationContext)
        {
            return new JsonPrimitive(unitSymbolKind.value());
        }
    }

    public static class Deserializer implements JsonDeserializer<UnitSymbolKind>
    {
        @Override
        public UnitSymbolKind deserialize(JsonElement jsonElement, Type type,
                JsonDeserializationContext jsonDeserializationContext)
        {
            try
            {
                return UnitSymbolKind.valueOf(jsonElement.getAsString());
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return null;
            }
        }
    }
}
