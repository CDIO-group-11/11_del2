package app;

import lang.Lang;
import lang.Language;
import lang.Language.LanguageCode;

import java.io.File;
import java.util.Scanner;

import data.Save;

public class Main {
  private final static String ROLL_COMMAND = "r"; 
  private final static String EXIT_COMMAND = "x";
  private final static String SAVE_COMMAND = "s";
  private static Board table;
  private static LanguageCode currentLanguage;
  private static int currentPlayer = 0;
  private static long turnNumber = 0;
  private static Player[] players = new Player[2];
  private static Scanner userInput = new Scanner(System.in);
  private static int previousPlayer;
  public static void main(String[] args) {
    currentLanguage = Language.getLanguage(userInput);
    ValueReader.loadValues();
    Lang.loadLang(currentLanguage);
    table = new Board(6, currentLanguage);
    for (int i = 0; i < players.length; i++) {
      players[i] = new Player(i, 1000);
    }
    PrintUI(null);
    Lang.moveToInput();
    while (true) {
      boolean readingInput = true;
      while (readingInput) {
        String in = userInput.nextLine();
        Lang.redoInput();
        System.out.print(" ".repeat(in.length()));
        System.out.print("\033["+ in.length() + "D");
        switch (in) {
          case ROLL_COMMAND:
            readingInput = false;
            break;
          case EXIT_COMMAND:
            Lang.moveToEnd();
            System.out.println();
            readingInput = false;
            System.exit(0);
            break;
          case SAVE_COMMAND:
            Lang.moveToEnd();
            System.out.println();
            readingInput = false;
            System.out.print("\rwhat should the save file be called:                  \033[17D");
            Save.state(players, new File("data/" + userInput.nextLine() + ".state"), currentLanguage);
            System.exit(0);
            break;
        }
      }
      Tile currentTile = table.makeMove();
      Lang.moveToStartFromInput();
      PrintUI(currentTile);
      Lang.moveToInput();
      previousPlayer = currentPlayer;
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
    if(currentTile == null){
      UI = UI.replace("\uE001", "" + (currentPlayer + 1));
      UI = UI.replace("\uE002", " ");
      UI = UI.replace("\uE003", " ");
      UI = UI.replace("\uE004", " ");
      UI = UI.replace("\uE005", "" + players[currentPlayer].getGold());
      UI = UI.replace("\uE006", "" + players[0].getGold());
      UI = UI.replace("\uE007", "" + players[1].getGold());
      UI = UI.replace("\uE008", "" + turnNumber);
      UI = UI.replace("\uE009", " ");
      UI = UI.replace("\uE00A", " ");
      UI = UI.replace("\uE00B", "" + ROLL_COMMAND);
      UI = UI.replace("\uE00C", "" + SAVE_COMMAND);
      UI = UI.replace("\uE00D", "" + EXIT_COMMAND);
      UI = UI.replace("\uE00E", "" + previousPlayer);
    }else{
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
      UI = UI.replace("\uE00B", "" + ROLL_COMMAND);
      UI = UI.replace("\uE00C", "" + SAVE_COMMAND);
      UI = UI.replace("\uE00D", "" + EXIT_COMMAND);
      UI = UI.replace("\uE00E", "" + previousPlayer);
    }
    System.out.println(UI);
  }
}
