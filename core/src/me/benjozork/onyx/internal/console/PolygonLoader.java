package me.benjozork.onyx.internal.console;

import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

/**
 * Used to load polygons from the json file containing models
 * Created by Raj on 28-03-2017.
 */

public class PolygonLoader {
    private JsonReader reader;
    private JsonValue value;
    private static final String FILENAME="test.json";
    public static final String SHIP="Ship";



    public PolygonLoader()
    {
        reader=new JsonReader();
    }

    /**
     * Gives back a polygon from the Physics Body Editor Configuration file
     * @param name Name for the model whose polygon is to be taken
     * @return
     */
//    public Polygon getPolygon(String name) {
//        value=reader.parse(Gdx.files.internal(FILENAME));
//        value.get("rigidBodies");
//        for (JsonValue val:
//             value.iterator()) {
//            if(val.get("name"),equals(name))
//        }
//        return null;
//    }
}
