package me.benjozork.onyx.parser;

import java.util.HashMap;

/**
 * Created by Benjozork on 2017-03-24.
 */
public class ConfigFileParser extends FileParser {

     private HashMap<String, String> meta = new HashMap<String, String>();
     private HashMap<String, String> attributes = new HashMap<String, String>();

     private String filterMode;

     protected ConfigFileParser(String path) {
          super(path);
     }

     @Override
     protected void parseLines(String[] lines) {
          for (String line : lines) {
               if (line.startsWith("@Meta")) {
                    String tag = line;
                    tag.replace("@Meta(", "");
                    tag = tag.split("\"")[1];

                    String value = line;
                    value.replace("@Meta(", "");
                    value = value.split("\"")[2];

                    meta.put(tag, value);
               } else if (line.startsWith("@Attribute")) {
                    String tag = line;
                    tag.replace("@Attribute(", "");
                    tag = tag.split("\"")[1];

                    String value = line;
                    value.replace("@Attribute(", "");
                    value = value.split("\"")[2];

                    attributes.put(tag, value);
               } else if (line.startsWith("!ApplyTo")) {
                    String value = line;
                    value.replace("!ApplyTo(", "");
                    value = value.split("\"")[1];

                    filterMode = value;
               }
          }
     }

}
