package me.benjozork.onyx.internal;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.HashMap;

import me.benjozork.onyx.logger.Log;

/**
 * Class to load {@link Polygon} from a JSON value and to get
 * @author Rishi Raj
 */

public class PolygonLoader {

    private static JsonValue value;

    private static Log log;

    private static HashMap<String,Polygon> polygons;

    public static void init() {
        log = Log.create("PolygonLoader");
        polygons = new HashMap<String, Polygon>();
        JsonReader reader = new JsonReader();
        value = reader.parse(new FileHandle("data/models.json"));
    }

    public static void loadPolygon(String name) {
        int len = 0, i = 0;
        Polygon p;
        JsonValue.JsonIterator iter = value.iterator();
        JsonValue temp, required = null;

        //Get the polygon of the required name from json
        while (iter.hasNext()) {
            temp = iter.next();
            if(temp.getString("name").equals(name))
                required = temp;
        }

        if(required == null) {
            log.print("ERROR: Polygon named %s not found", name);
            return;
        }

        iter = required.get("vertices").iterator();

        for (JsonValue vertice : iter) {
            len += 2;
        }

        iter = required.get("vertices").iterator();

        float[] vertices = new float[len];
        for (JsonValue val : iter) {
            vertices[i] = (float) val.getDouble("x");
            vertices[i + 1] = (float) val.getDouble("y");
            i += 2;
        }
        p = new Polygon(vertices);
        p.setOrigin(required.get("origin").getFloat("x"),required.get("origin").getFloat("y"));
        polygons.put(name,p);
        log.print("Polygon for %s added to list", name);
    }

    public static Polygon getPolygon(String name) {
        return getPolygon(name, 1, 1);
    }

    public static Polygon getPolygon(String name, float width, float height)
    {
        if(! polygons.containsKey(name))
            loadPolygon(name);
        Polygon temp = polygons.get(name);
        float[] tempVertices = temp.getVertices();
        float[] returnVertices = new float[tempVertices.length];
        for(int i = 0; i < tempVertices.length; i++)
        {
            if(i % 2 == 0)
                returnVertices[i] = tempVertices[i] * width;
            else returnVertices[i] = tempVertices[i] * height;
        }
        Polygon ret = new Polygon(returnVertices);
        ret.setOrigin(width/2, height/2);
        log.print("Polygon %s added to screen", name);
        return ret;
        //NOTE: Origin might need to be revisited
    }

}