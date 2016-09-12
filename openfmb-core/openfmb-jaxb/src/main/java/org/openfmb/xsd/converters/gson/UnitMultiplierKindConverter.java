package org.openfmb.xsd.converters.gson;

import java.lang.reflect.Type;

import org.openfmb.xsd._2015._12.openfmb.commonmodule.UnitMultiplierKind;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class UnitMultiplierKindConverter
{
    public static class Serializer implements JsonSerializer<UnitMultiplierKind>
    {
        @Override
        public JsonElement serialize(UnitMultiplierKind unitMultiplierKind, Type type, JsonSerializationContext jsonSerializationContext)
        {
            return new JsonPrimitive(unitMultiplierKind.value());
        }
    }

    public static class Deserializer implements JsonDeserializer<UnitMultiplierKind>
    {
        @Override
        public UnitMultiplierKind deserialize(JsonElement jsonElement, Type type,
                JsonDeserializationContext jsonDeserializationContext)
        {
            try
            {
                return UnitMultiplierKind.valueOf(jsonElement.getAsString());
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return null;
            }
        }
    }
}
