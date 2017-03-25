package me.benjozork.onyx.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benjozork on 2015-02-23.
 */
public abstract class FileParser {

     protected FileParser(String path) {
          file = new File(path);
     }
     private File file;

     private String[] decodeLines() {
          BufferedReader br = null;
          List<String> content = new ArrayList<String>();

          try {
               String currentLine;

               br = new BufferedReader(new FileReader(file.getAbsolutePath()));

               while ((currentLine = br.readLine()) != null) {
                    content.add(currentLine);
               }
               return content.toArray(new String[content.size()]);
          } catch (IOException e) {
               e.printStackTrace();
          } finally {
               try {
                    if (br != null) br.close();
               } catch (IOException e) {
                    e.printStackTrace();
               }
          }
          return null;
     }

     protected abstract void parseLines(String[] lines) ;

     protected void parseFile(String[] lines) {
          parseLines(lines);
     }

}
