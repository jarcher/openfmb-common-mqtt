package org.openfmb.xsd.converters.gson;

import java.lang.reflect.Type;

import org.openfmb.xsd._2015._12.openfmb.commonmodule.InterTieDirectionKind;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class InterTieDirectionKindConverter
{
    public static class Serializer implements JsonSerializer<InterTieDirectionKind>
    {
        @Override
        public JsonElement serialize(InterTieDirectionKind interTieDirection, Type type, JsonSerializationContext jsonSerializationContext)
        {
            return new JsonPrimitive(interTieDirection.value());
        }
    }

    public static class Deserializer implements JsonDeserializer<InterTieDirectionKind>
    {
        @Override
        public InterTieDirectionKind deserialize(JsonElement jsonElement, Type type,
                JsonDeserializationContext jsonDeserializationContext)
        {
            try
            {
                return InterTieDirectionKind.valueOf(jsonElement.getAsString());
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return null;
            }
        }
    }
}
