package Crawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class FindBrokenLinks {

  private static BufferedReader bufferedReader;

  @SuppressWarnings("unused")
  public static void main(String[] args) throws Exception {
    String allLinksFile = "/home/sayak/workspace/FindBrokenLinks/Resources/AllURL.txt";
    String brokenLinksFile = "/home/sayak/workspace/FindBrokenLinks/Resources/BrokenLinkURL.txt";
    String appLogFile = "/home/sayak/workspace/FindBrokenLinks/Resources/appLog.txt";
    HashMap<String, Integer> brokenLinks = new HashMap<>();
    HashMap<String, Integer> allLinks = new HashMap<>();

    bufferedReader = new BufferedReader(new FileReader(new File(allLinksFile)));
    String line;
    Response response;

    try {
      while ((line = bufferedReader.readLine()) != null) {
        response = FindBrokenLinks.pingURL(line, 1000);
        // System.out.println(response.message + "\n");
        allLinks.put(response.message, 1);
        if (response.status.toString().equals("false")) {
          // System.out.println(response.message + "::" +
          // response.status + "\n");
          brokenLinks.put(response.message, 1);

        }

      }
      
      if(allLinks != null){
      StoreWebPageLinks.storeAllLinks(appLogFile, allLinks);
      }
      
      if(brokenLinks != null){
      StoreWebPageLinks.storeAllLinks(brokenLinksFile, brokenLinks);
      } else
        System.out.println("No Broken Links");
      
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public static Response pingURL(String url, int timeout) throws Exception {
    Response response;
    String message = null;
    url = url.replaceFirst("^https", "http");
    try {
      HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
      connection.setConnectTimeout(timeout);
      connection.setReadTimeout(timeout);
      connection.setRequestMethod("GET");
      int responseCode = connection.getResponseCode();

      message = url + "::" + responseCode + "::" + connection.getResponseMessage();
      // System.out.println(message);

      if (responseCode == 200) {
        response = new Response(message, true);
      } else {
        response = new Response(message, false);
      }

    } catch (Exception e) {
      response = new Response(message, false);
    }
    return response;

  }
}


class Response {
  String message;
  Boolean status;

  Response(String message, Boolean status) {
    this.message = message;
    this.status = status;
  }
}
