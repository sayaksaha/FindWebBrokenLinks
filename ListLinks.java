package Crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.HashMap;

/**
 * Example program to list links from a URL.
 */
public class ListLinks {
  public static void main(String[] args) throws Exception {
    //Validate.isTrue(args.length == 1, "usage: supply url to fetch");
    String url = "https://business.bt.com/products/computing-apps/eshop/";
    String fileAllLinks = "/home/sayak/workspace/FindBrokenLinks/Resources/AllURL.txt";
    HashMap<String, Integer> urls = new HashMap<>();
    
    print("Fetching %s...", url);

    Document doc = Jsoup.connect(url).get();
    Elements links = doc.select("a[href]");
    Elements media = doc.select("[src]");
    Elements imports = doc.select("link[href]");

    print("\nMedia: (%d)", media.size());
    for (Element src : media) {
      
      if (src.tagName().equals("img")){
       print(" * %s: <%s> %sx%s (%s)", src.tagName(), src.attr("abs:src"), src.attr("width"),
            src.attr("height"), trim(src.attr("alt"), 20));
            System.out.println();
      }
      else 
        print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
      
    }

    print("\nImports: (%d)", imports.size());
    for (Element link : imports) {
      print(" * %s <%s> (%s)", link.tagName(), link.attr("abs:href"), link.attr("rel"));
    }

    print("\nLinks: (%d)", links.size());
    for (Element link : links) {
      print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
      urls.put(link.attr("abs:href"), 1);
    }
    StoreWebPageLinks.storeAllLinks(fileAllLinks, urls);
  }

  private static void print(String msg, Object... args) {
    System.out.println(String.format(msg, args));
  }

  private static String trim(String s, int width) {
    if (s.length() > width)
      return s.substring(0, width - 1) + ".";
    else
      return s;
  }
}
