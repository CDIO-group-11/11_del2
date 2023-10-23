package lang;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import app.ValueReader;

import java.util.HashMap;

import lang.Language.LanguageCode;

public class Lang {
  private static String[][] tiles = new String[LanguageCode.values().length][];
  private static String[] UserInterface = new String[LanguageCode.values().length];
  private static HashMap<String,String> errors;
  public static void loadLang(LanguageCode lang){
    String path = "./Lang/" + lang.name() + ".lang";
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
      String currentLine = languageReader.hasNextLine() ? languageReader.nextLine() : "\uE000";
      while(!currentLine.equals("\uE000")){//read tiles
        if(currentLine.equals("")) {
          currentLine = languageReader.hasNextLine() ? languageReader.nextLine() : "\uE000";
          continue;
        }
        if(!currentLine.split(" ")[0].contains(descriptorTile)){
          break;
        }
        int tileNumber = Integer.parseInt(
            currentLine.split(" ")[0]
            .replace(descriptorTile, "")
          );
        tiles[
          lang.ordinal()
        ][
          tileNumber-2
        ] = 
        currentLine = currentLine
        .replaceFirst
        (currentLine.split(" ")[0] + " ", "")
        .replace(descriptorTile, "" + ValueReader.getTileValue(tileNumber));
        currentLine = languageReader.hasNextLine() ? languageReader.nextLine() : "\uE000";
      }
      UserInterface[lang.ordinal()] = "";
      while(!currentLine.equals("\uE000")){//read inteface
        if(currentLine.equals("")) {
          currentLine = languageReader.hasNextLine() ? languageReader.nextLine() : "\uE000";
          continue;
        }
        if(!currentLine.split(" ")[0].contains(descriptorInterface)){
          break;
        }
        UserInterface[lang.ordinal()] += setTags(currentLine,descriptorInterface);
        currentLine = languageReader.hasNextLine() ? languageReader.nextLine() : "\uE000";
      }
      
      while (!currentLine.equals("\uE000")) {//read errors
        if(currentLine.equals("")) {
          currentLine = languageReader.hasNextLine() ? languageReader.nextLine() : "\uE000";
          continue;
        }
        if(currentLine.charAt(0) == descriptorErrorStart){
          String[] all = currentLine.split(descriptorErrorEnd + "");
          String name = all[0];
          String message = "";
          for (int i = 1; i < all.length; i++) {
            message += all[i] + (i + 1 < all.length ? descriptorErrorStart + "" + descriptorErrorEnd : "");
          }
          errors.put(name, message);
        }
        currentLine = languageReader.hasNextLine() ? languageReader.nextLine() : "\uE000";
      }
    } catch (FileNotFoundException e) {
      System.out.println("lang not found\n" +
      "the language that was requested does not exist");
      System.out.println("lang: " + lang.name());
      System.out.println("expected path: " + path);
    }
    System.out.println("done loading " + lang.name());
  }
  public static String[][] tags = {//IMPORTANT Do not change this without communicationg with the groupand changing Main.java PrintUI()!!!!!!! 
    //human written key
    new String[]{"player","die1"  ,"die2"  ,"sum"   ,"gold"  ,"gold1" ,"gold2" ,"turn"  ,"tileT" ,"tileNR","red"   ,"green" ,"blue"  ,"cyan"  ,"yellow","purple","r"     ,"g"     ,"b"     ,"c"     ,"y"     ,"p"     ,"reset"     ,"default"   , ""},
    //computer written key
    new String[]{"\uE001","\uE002","\uE003","\uE004","\uE005","\uE006","\uE007","\uE008","\uE009","\uE00A",Ansi.r(),Ansi.g(),Ansi.b(),Ansi.c(),Ansi.y(),Ansi.p(),Ansi.r(),Ansi.g(),Ansi.b(),Ansi.c(),Ansi.y(),Ansi.p(),Ansi.reset(),Ansi.reset(), Ansi.reset()}
  };
  
  private static String setTags (String input, String descriptor){
    input = input.replace(descriptor + descriptor + " ", "\n");
    input = input.replace(descriptor + descriptor, "\n");
    for (int i = 0; i < tags[0].length; i++) {
      input = input.replace(descriptor + tags[0][i] + " ", tags[1][i]);
      input = input.replace(descriptor + tags[0][i], tags[1][i]);
    }
    return input;
  }
  public static void error(Exception e){
    String[] out = errors.get(e.getClass().getSimpleName()).split("[]");
    if(out == null){
      System.out.println("no translated message");
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
  public static String getUI(LanguageCode lang){
    return UserInterface[lang.ordinal()];
  }
  public static String getTileText(LanguageCode lang, int tileNumber){
    return tiles[lang.ordinal()][tileNumber-2];
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
