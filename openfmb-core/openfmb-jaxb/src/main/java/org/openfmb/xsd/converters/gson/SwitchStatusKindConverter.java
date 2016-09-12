package org.openfmb.xsd.converters.gson;

import java.lang.reflect.Type;

import org.openfmb.xsd._2015._12.openfmb.commonmodule.SwitchStatusKind;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class SwitchStatusKindConverter
{
    public static class Serializer implements JsonSerializer<SwitchStatusKind>
    {
        @Override
        public JsonElement serialize(SwitchStatusKind switchStatusKind, Type type, JsonSerializationContext jsonSerializationContext)
        {
            return new JsonPrimitive(switchStatusKind.value());
        }
    }

    public static class Deserializer implements JsonDeserializer<SwitchStatusKind>
    {
        @Override
        public SwitchStatusKind deserialize(JsonElement jsonElement, Type type,
                JsonDeserializationContext jsonDeserializationContext)
        {
            try
            {
                return SwitchStatusKind.valueOf(jsonElement.getAsString());
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return null;
            }
        }
    }
}
