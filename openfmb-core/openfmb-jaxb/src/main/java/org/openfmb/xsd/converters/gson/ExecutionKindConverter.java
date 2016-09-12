package org.openfmb.xsd.converters.gson;

import java.lang.reflect.Type;

import org.openfmb.xsd._2015._12.openfmb.commonmodule.ExecutionKind;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ExecutionKindConverter
{
    public static class Serializer implements JsonSerializer<ExecutionKind>
    {
        @Override
        public JsonElement serialize(ExecutionKind execution, Type type, JsonSerializationContext jsonSerializationContext)
        {
            return new JsonPrimitive(execution.value());
        }
    }

    public static class Deserializer implements JsonDeserializer<ExecutionKind>
    {
        @Override
        public ExecutionKind deserialize(JsonElement jsonElement, Type type,
                JsonDeserializationContext jsonDeserializationContext)
        {
            try
            {
                return ExecutionKind.valueOf(jsonElement.getAsString());
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return null;
            }
        }
    }
}
