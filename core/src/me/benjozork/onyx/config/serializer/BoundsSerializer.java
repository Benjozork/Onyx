package me.benjozork.onyx.config.serializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import me.benjozork.onyx.backend.models.Bounds;

import java.lang.reflect.Type;

public class BoundsSerializer implements JsonDeserializer<Bounds> {

    @Override
    public Bounds deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        int width = json.getAsJsonObject().get("width").getAsInt();
        int height = json.getAsJsonObject().get("height").getAsInt();
        Bounds bounds = new Bounds(width, height);
        return bounds;
    }

}