package lang;

import java.util.Scanner;

public class Language { //using ISO 639-1
  public enum LanguageCode{
    da,
    en
  }
  public static LanguageCode getLanguage(Scanner scan){
    System.out.println("language options:");
    for (int i = 0; i < LanguageCode.values().length; i++) {
      System.out.println("\t" + LanguageCode.values()[i].name());
    }
    System.out.println("choose Language; vælg sprog");
    while(true){
      switch(scan.nextLine().toLowerCase()){
        case "da":
        case "dansk":
        case "dan":
        case "dk":
        return LanguageCode.da;
        case "en":
        case "eng":
        case "english":
        return LanguageCode.en;
        default:
        System.out.println("not available; ikke tilgængelig");
      }
    }
  }
}
