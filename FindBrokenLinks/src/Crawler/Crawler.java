package Crawler;
/*
 * The program is capable to crawl through a website and fetch the associated links Pass the URL as
 * part of string variable url
 */

import java.util.HashMap;
import org.apache.commons.validator.UrlValidator;

@SuppressWarnings("deprecation")
public class Crawler {
  public static void main(String[] args) throws Exception {
    int i = 0, j = 0, tmp = 0, total = 0, MAX = 1000;
    String url = "http://stackoverflow.com";
    String fileAllLinks = "/home/sayak/workspace/FindBrokenLinks/Resources/LinkURL.txt";

    String urls[] = new String[MAX];
    HashMap<String, Integer> links = new HashMap<>();

    int start = 0, end = 0;
    String[] schemes = {"http", "https"};
    UrlValidator urlValidator = new UrlValidator(schemes);

    String webpage = FetchWebPageContents.getWeb(url);
    end = webpage.indexOf("<body");
    for (i = total; i < MAX; i++, total++) {
      start = webpage.indexOf("http://", end);

      /*
       * Error handling for cases where no url or links starting with https:// are found starting
       * from<body> in the webpage contents
       */

      if (start == -1) {
        start = 0;
        end = 0;
        try {
          webpage = FetchWebPageContents.getWeb(urls[j++]);
        } catch (Exception e) {
          System.out.println("******************");
          System.out.println(urls[j - 1]);
          System.out.println("Exception caught \n" + e);
        }

        /* logic to fetch urls out of body of webpage only */
        end = webpage.indexOf("<body");
        if (end == -1) {
          end = start = 0;
          continue;
        }
      }
      end = webpage.indexOf("\"", start);
      tmp = webpage.indexOf("'", start);
      if (tmp < end && tmp != -1) {
        end = tmp;
      }
      url = webpage.substring(start, end);

      // Consider the URL only if it is a valid url
      if (urlValidator.isValid(url)) {
        urls[i] = url;
        links.put(url, 1);
        // System.out.println(urls[i]);
      }
    }

    /*
     * Result Summary : Total Links | Unique Links Save the results in Resources/LinkURL.txt
     * 
     */

    System.out.println("Total URLS Fetched are " + total);
    System.out.println("Total Unique URLS Fetched are " + links.keySet().size());

    StoreWebPageLinks.storeAllLinks(fileAllLinks, links);

  }
}
