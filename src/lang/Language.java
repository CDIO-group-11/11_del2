package lang;

import java.util.Scanner;

public class Language { //using ISO 639-1
  public enum LanguageCode{
    da,
    en
  }
  public static LanguageCode getLanguage(Scanner scan){
    System.out.println("choose Language; vælg sprog");
    while(true){
      switch(scan.nextLine()){
        case "da":
        case "dansk":
        case "dk":
        return LanguageCode.da;
        case "en":
        case "english":
        return LanguageCode.en;
        default:
        System.out.println("not available; ikke tilgængelig");
      }
    }
  }
}
