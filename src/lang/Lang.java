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
  private void generate(Language lang) throws FileNotFoundException {
    ArrayList<String> tempText = new ArrayList<String>();
    Scanner languageReader = new Scanner(new File("./Lang/" + lang.name() + ".lang"));
    while(languageReader.hasNextLine()){
      tempText.add(languageReader.nextLine());
    }
    languageReader.close();
    text[lang.ordinal()] = (String[]) tempText.toArray();
  }
}
