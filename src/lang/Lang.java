package lang;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import lang.Language.LanguageCode;

public class Lang {
  private String text[][];
  public String getText(LanguageCode lang, int textIndex) {
    if(text[lang.ordinal()] == null){
      try {
        generate(lang);
      } catch (FileNotFoundException e) {
        System.out.println("the file supposed to be stored at ./Lang/" + lang.name() + ".lang is missing\nthis file should contain the language: " + lang.name() + "\nplease choose a different language");
      }
    }
    return text[lang.ordinal()][textIndex];
  }
  private void generate(LanguageCode lang) throws FileNotFoundException {
    ArrayList<String> tempText = new ArrayList<String>();
    Scanner languageReader = new Scanner(new File("./Lang/" + lang.name() + ".lang"));
    while(languageReader.hasNextLine()){
      tempText.add(languageReader.nextLine());
    }
    languageReader.close();
    text[lang.ordinal()] = (String[]) tempText.toArray();
  }
}
