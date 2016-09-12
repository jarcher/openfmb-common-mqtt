package org.openfmb.xsd.converters.gson;

import java.lang.reflect.Type;

import org.openfmb.xsd._2015._12.openfmb.commonmodule.PhaseCodeKind;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class PhaseCodeKindConverter
{
    public static class Serializer implements JsonSerializer<PhaseCodeKind>
    {
        @Override
        public JsonElement serialize(PhaseCodeKind phaseCodeKind, Type type, JsonSerializationContext jsonSerializationContext)
        {
            return new JsonPrimitive(phaseCodeKind.value());
        }
    }

    public static class Deserializer implements JsonDeserializer<PhaseCodeKind>
    {
        @Override
        public PhaseCodeKind deserialize(JsonElement jsonElement, Type type,
                JsonDeserializationContext jsonDeserializationContext)
        {
            try
            {
                return PhaseCodeKind.valueOf(jsonElement.getAsString());
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return null;
            }
        }
    }
}
