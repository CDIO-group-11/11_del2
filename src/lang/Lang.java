package lang;

import java.util.ArrayList;

public class Lang {
  private String text[][];
  public String getText(Language lang, int textIndex) {
    if(text[lang.ordinal()] == null){
      generate(lang);
    }
    return text[lang.ordinal()][textIndex];
  }
  private void generate(Language lang) {
    ArrayList<String> tempText = new ArrayList<String>();
    //reader
    text[lang.ordinal()] = (String[]) tempText.toArray();
  }
}
