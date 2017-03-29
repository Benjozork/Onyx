package me.benjozork.onyx.internal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;

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
     * Will be completed later
     * Gives back a polygon from the Physics Body Editor Configuration file
     * @param name Name for the model whose polygon is to be taken
     * @return
     */
    public Polygon getPolygon(String name, float width, float height) {
        JsonValue polygon;
        Polygon rp = null;
        int i;
        ArrayList<Float> vals = new ArrayList<Float>();
        value=reader.parse(Gdx.files.internal(FILENAME));
        value=value.get("rigidBodies");
        System.out.println("Rigid Bodies\n"+value);
        System.out.println("************************");
        JsonValue.JsonIterator iterator =value.iterator();
        for (JsonValue jsonValue : iterator) {
            System.out.println((jsonValue.get("name").asString().equals(name)));
            if(jsonValue.get("name").asString().equals(name))
            {
                polygon = jsonValue.get("polygons").get(0);
                System.out.println(polygon);
                System.out.println("************************");
                JsonValue.JsonIterator verticeIterator =polygon.iterator();
                for (JsonValue vertice : iterator) {
                    System.out.println(vertice);
                    System.out.println("************************");
                    vals.add(vertice.get("x").asFloat() * width);
                    vals.add(vertice.get("y").asFloat() * height);
                }

                float[] coordinates = new float[vals.size()];
                int j = 0;
                for (Float f : vals) {
                    coordinates[j++] = f;
                }
                rp = new Polygon(coordinates);
                rp.setOrigin(jsonValue.get("origin").get("x").asFloat() * width,
                        jsonValue.get("origin").get("y").asFloat() * height);
            }
        }
        return rp;
    }
    public static void main(String[] args)
    {
        PolygonLoader loader = new PolygonLoader();
        loader.getPolygon(PolygonLoader.SHIP,30,60);
    }

}

