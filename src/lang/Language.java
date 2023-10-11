package lang;

import java.util.Scanner;

public class Language { //using ISO 639-1
  public enum LanguageCode{
    da,
    en
  }
  public static LanguageCode getLanguage(){
    System.out.println("choose Language; vælg sprog");
    Scanner scan = new Scanner(System.in);
    while(true){
      switch(scan.nextLine()){
        case "da":
        case "dansk":
        case "dk":
        scan.close();
        return LanguageCode.da;
        case "en":
        case "english":
        scan.close();
        return LanguageCode.en;
        default:
        System.out.println("not available; ikke tilgænlig");
      }
    }
  }
}
