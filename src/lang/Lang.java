package lang;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;

import lang.Language.LanguageCode;

public class Lang {
  private static final int TileValue = 0;
  private static String[][] tiles = new String[LanguageCode.values().length][];
  private static String[] UserInterface = new String[LanguageCode.values().length];
  private static HashMap<String,String> errors;
  public static void loadLang(LanguageCode lang){
    String path = "./Lang/" + lang.name() + ".lang";
    ArrayList<String> tempText = new ArrayList<String>();
    Scanner languageReader;
    try {
      languageReader = new Scanner(new File(path));
      String descriptorTile = "@";
      String descriptorInterface = "&";
      char descriptorErrorStart = '[';
      char descriptorErrorEnd = ']';
      {
        String[] descriptor = languageReader.nextLine().split(" ");
        if(descriptor.length == 3){
          if(!descriptor[0].equals("")){
            descriptorTile = descriptor[0];
          }
          try{
            if(!descriptor[1].equals("")){
              tiles[lang.ordinal()] = new String[Integer.parseInt(descriptor[1])];
              }
          }catch(NumberFormatException e){
            System.out.println("tile count failed to read, using default");
          }
          if(!descriptor[2].equals("")){
            descriptorInterface = descriptor[2];
          }
        }
      }
      {
        String descriptor = languageReader.nextLine();
        if(descriptor.length() > 0 && descriptor.charAt(0) != ' '){
          descriptorErrorStart = descriptor.charAt(0);
        }
        if(descriptor.length() > 1 && descriptor.charAt(1) != ' '){
          descriptorErrorEnd = descriptor.charAt(1);
        }
      }
      String currentLine = languageReader.nextLine();
      while(true){//read tiles
        if(!currentLine.split(" ")[0].contains(descriptorTile)){
          break;
        }
        tiles[
          lang.ordinal()
        ][
          Integer.parseInt(
            currentLine.split(" ")[0]
            .replace(descriptorTile, "")
          )-1
        ] = 
        currentLine
        .replaceFirst
        (currentLine.split(" ")[0] + " ", "")
        .replace(descriptorTile, "" + TileValue);
        languageReader.nextLine();
      }
      UserInterface[lang.ordinal()] = "";
      while(true){
        if(!currentLine.split(" ")[0].contains(descriptorInterface)){
          break;
        }
        UserInterface[lang.ordinal()] += setTags(currentLine,descriptorInterface);
        languageReader.nextLine();
      }
      while (true) {
        // if(!currentLine.charAt(){
        //   break;
        // }
        
      }
    } catch (FileNotFoundException e) {
      System.out.println("lang not found\n" +
      "the language that was requested does not exist");
      System.out.println("lang: " + lang.name());
      System.out.println("expected path: " + path);
    }
  }
  private static String setTags (String input, String descriptor){//TODO write as two arrays and a loop
    input.replace(descriptor + "player", "\u0400");
    input.replace(descriptor + "die1"  , "\u0401");
    input.replace(descriptor + "die2"  , "\u0402");
    input.replace(descriptor + "sum"   , "\u0403");
    input.replace(descriptor + "gold"  , "\u0404");
    input.replace(descriptor + "gold1" , "\u0405");
    input.replace(descriptor + "gold2" , "\u0406");
    input.replace(descriptor + "turn"  , "\u0407");
    input.replace(descriptor + "tileT" , "\u0408");
    input.replace(descriptor + "tileNR", "\u0409");
    return setColor(input, descriptor);
  }
  private static String setColor(String input, String descriptor){//TODO make a loop
    input.replace(" " + descriptor, descriptor);
    input.replace(descriptor + "red", Ansi.r());
    input.replace(descriptor + "green", Ansi.g());
    input.replace(descriptor + "blue", Ansi.b());
    input.replace(descriptor + "cyan", Ansi.c());
    input.replace(descriptor + "yellow", Ansi.y());
    input.replace(descriptor + "purple", Ansi.p());
    input.replace(descriptor + "r", Ansi.r());
    input.replace(descriptor + "g", Ansi.g());
    input.replace(descriptor + "b", Ansi.b());
    input.replace(descriptor + "c", Ansi.c());
    input.replace(descriptor + "y", Ansi.y());
    input.replace(descriptor + "p", Ansi.p());
    input.replace(descriptor + "reset", Ansi.reset());
    input.replace(descriptor + "default", Ansi.reset());
    input.replace(descriptor, Ansi.reset());
    return input;
  } 
  public static void error(Exception e){
    String[] out = errors.get(e.getClass().getSimpleName()).split("[]");
    if(out == null){
      e.printStackTrace();
      return;
    }
    for (int i = 0; i < out.length; i++) {
      System.out.println(out[i]);
      if(i + 1 < out.length){
        e.printStackTrace();
      }
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
