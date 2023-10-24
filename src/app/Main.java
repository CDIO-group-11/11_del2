package app;

import lang.Lang;
import lang.Language;
import lang.Language.LanguageCode;
import java.util.Scanner;

public class Main {
  private final static String ROLL_COMMAND = "r"; 
  private static Board table;
  private static LanguageCode currentLanguage;
  private static int currentPlayer = 0;
  private static long turnNumber = 0;
  private static Player[] players = new Player[2];
  private static Scanner userInput = new Scanner(System.in);
  
  public static void main(String[] args) {
    currentLanguage = Language.getLanguage(userInput);
    ValueReader.loadValues();
    Lang.loadLang(currentLanguage);
    table = new Board(6, currentLanguage);
    for (int i = 0; i < players.length; i++) {
      players[i] = new Player(i, 1000);
    }
    while (true) {
      while (true) {
        String in = userInput.nextLine();
        Lang.redoInput();
        System.out.print(" ".repeat(in.length()));
        System.out.print("\033["+ in.length() + "D");
        if(in.equals(ROLL_COMMAND)){
          break;
        }
      }
      Tile currentTile = table.makeMove();
      Lang.moveToStartFromInput();
      PrintUI(currentTile);
      Lang.moveToInput();
      if(players[currentPlayer].addGold(currentTile.value)){
        System.out.println("player " + currentPlayer + " wins!");
        break;
      }
      currentPlayer++;
      currentPlayer %= players.length;
      turnNumber++;
    }
  }

  private static void PrintUI(Tile currentTile) {
    String UI  = Lang.getUI(currentLanguage);
    UI = UI.replace("\uE001", "" + (currentPlayer + 1));
    UI = UI.replace("\uE002", "" + table.getCup().getSides()[0]);
    UI = UI.replace("\uE003", "" + table.getCup().getSides()[1]);
    UI = UI.replace("\uE004", "" + (table.getCup().getSides()[0] + table.getCup().getSides()[1]) + "  ");
    UI = UI.replace("\uE005", "" + players[currentPlayer].getGold());
    UI = UI.replace("\uE006", "" + players[0].getGold());
    UI = UI.replace("\uE007", "" + players[1].getGold());
    UI = UI.replace("\uE008", "" + turnNumber);
    UI = UI.replace("\uE009", "" + currentTile.text);
    UI = UI.replace("\uE00A", "" + currentTile.number);
    System.out.println(UI);
  }
}
