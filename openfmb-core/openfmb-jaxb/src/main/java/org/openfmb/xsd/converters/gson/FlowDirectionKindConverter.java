package org.openfmb.xsd.converters.gson;

import java.lang.reflect.Type;

import org.openfmb.xsd._2015._12.openfmb.commonmodule.FlowDirectionKind;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class FlowDirectionKindConverter
{
    public static class Serializer implements JsonSerializer<FlowDirectionKind>
    {
        @Override
        public JsonElement serialize(FlowDirectionKind flowDirectionKind, Type type, JsonSerializationContext jsonSerializationContext)
        {
            return new JsonPrimitive(flowDirectionKind.value());
        }
    }

    public static class Deserializer implements JsonDeserializer<FlowDirectionKind>
    {
        @Override
        public FlowDirectionKind deserialize(JsonElement jsonElement, Type type,
                JsonDeserializationContext jsonDeserializationContext)
        {
            try
            {
                return FlowDirectionKind.valueOf(jsonElement.getAsString());
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return null;
            }
        }
    }
}
