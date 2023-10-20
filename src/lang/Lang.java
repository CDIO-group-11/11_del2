package lang;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import lang.Language.LanguageCode;

public class Lang {
  String[][] tiles;
  public static void loadLang(LanguageCode lang){
    ArrayList<String> tempText = new ArrayList<String>();
    Scanner languageReader;
    try {
      languageReader = new Scanner(new File("./Lang/" + lang.name() + ".lang"));
      String descriptorTile = "@";
      int TileCount = 11;
      String descriptorInterface = "&";
      String descriptorErrorStart = "[";
      String descriptorErrorEnd = "]";
      {
        String[] descriptor = languageReader.nextLine().split(" ");
        if(descriptor.length == 3){
          if(!descriptor[0].equals("")){
            descriptorTile = descriptor[0];
          }
          try{
            if(!descriptor[1].equals("")){
              TileCount = Integer.parseInt(descriptor[1]);
              }
          }catch(NumberFormatException e){
            System.out.println("tile count failed to read using default");
          }
          if(!descriptor[2].equals("")){
            descriptorInterface = descriptor[2];
          }
        }
      }
        while(languageReader.hasNextLine()){
        
      }
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  private static String text[][];
  /**
   * @deprecated
   * @param lang
   * @param textIndex
   * @return
   */
  public static String getText(LanguageCode lang, int textIndex) {
    if(text[lang.ordinal()] == null){
      try {
        generate(lang);
      } catch (FileNotFoundException e) {
        System.out.println("the file supposed to be stored at ./Lang/" + lang.name() + ".lang is missing\nthis file should contain the language: " + lang.name() + "\nplease choose a different language");
      }
    }
    return text[lang.ordinal()][textIndex];
  }
  /**
   * @deprecated
   * @param lang
   * @throws FileNotFoundException
   */
  private static void generate(LanguageCode lang) throws FileNotFoundException {
    ArrayList<String> tempText = new ArrayList<String>();
    Scanner languageReader = new Scanner(new File("./Lang/" + lang.name() + ".lang"));
    while(languageReader.hasNextLine()){
      tempText.add(languageReader.nextLine());
    }
    languageReader.close();
    text[lang.ordinal()] = (String[]) tempText.toArray();
  }
}
