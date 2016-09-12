package org.openfmb.xsd.converters.gson;

import java.lang.reflect.Type;

import org.openfmb.xsd._2015._12.openfmb.commonmodule.EnergyProductKind;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class EnergyProductKindConverter
{
    public static class Serializer implements JsonSerializer<EnergyProductKind>
    {
        @Override
        public JsonElement serialize(EnergyProductKind energyProduct, Type type, JsonSerializationContext jsonSerializationContext)
        {
            return new JsonPrimitive(energyProduct.value());
        }
    }

    public static class Deserializer implements JsonDeserializer<EnergyProductKind>
    {
        @Override
        public EnergyProductKind deserialize(JsonElement jsonElement, Type type,
                JsonDeserializationContext jsonDeserializationContext)
        {
            try
            {
                return EnergyProductKind.valueOf(jsonElement.getAsString());
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return null;
            }
        }
    }
}
