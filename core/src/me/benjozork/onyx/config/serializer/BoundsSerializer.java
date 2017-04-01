package me.benjozork.onyx.config.serializer;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializer;
import com.badlogic.gdx.utils.JsonValue;
import me.benjozork.onyx.utils.Bounds;

public class BoundsSerializer implements Serializer<Bounds> {

    @Override
    public void write(Json json, Bounds object, Class knownType) {
	json.writeObjectStart();
	json.writeValue("width", object.width);
	json.writeValue("height", object.height);
	json.writeObjectEnd();
    }

    @Override
    public Bounds read(Json json, JsonValue jsonData, Class type) {
	int width = jsonData.getInt("width");
	int height = jsonData.getInt("height");
	Bounds bounds = new Bounds(width, height);
	return bounds;
    }

}
