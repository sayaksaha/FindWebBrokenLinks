package Crawler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/*
 * This class contains a static function which will fetch the webpage of the given url and return as
 * a string
 */

class FetchWebPageContents {
  public static String getWeb(String address) throws Exception {
    String webpage = "";
    String inputLine = "";
    URL url = new URL(address);
    BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
    while ((inputLine = in.readLine()) != null)
      webpage += inputLine;
    in.close();
    return webpage;
  }
}
