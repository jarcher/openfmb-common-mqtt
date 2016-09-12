package org.openfmb.xsd.converters.gson;

import java.lang.reflect.Type;

import org.openfmb.xsd._2015._12.openfmb.commonmodule.CurveStyleKind;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class CurveStyleKindConverter
{
    public static class Serializer implements JsonSerializer<CurveStyleKind>
    {
        @Override
        public JsonElement serialize(CurveStyleKind curveStyle, Type type, JsonSerializationContext jsonSerializationContext)
        {
            return new JsonPrimitive(curveStyle.value());
        }
    }

    public static class Deserializer implements JsonDeserializer<CurveStyleKind>
    {
        @Override
        public CurveStyleKind deserialize(JsonElement jsonElement, Type type,
                JsonDeserializationContext jsonDeserializationContext)
        {
            try
            {
                return CurveStyleKind.valueOf(jsonElement.getAsString());
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return null;
            }
        }
    }
}
