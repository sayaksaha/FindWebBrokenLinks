package Crawler;

import java.io.*;
import java.util.HashMap;

public class StoreWebPageLinks {

  public static void storeAllLinks(String filePath, HashMap<String, Integer> links)
      throws IOException {
    String fileLocation = filePath;
    File file = new File(fileLocation);

    // creates the file
    file.createNewFile();

    // creates a FileWriter Object
    FileWriter writer = new FileWriter(file);

    // Writes the content to the file
    for (String link : links.keySet()) {
      writer.write(link);
      writer.write("\n");
    }

    writer.close();

  }
}
