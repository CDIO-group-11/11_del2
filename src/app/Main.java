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
  public static boolean clean = false;
  public static void main(String[] args) {
    clean = args.length > 0 && args[0].equals("clean");
    init(null);
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
        System.out.print(" ".repeat(in.length() == 0 ? 1 : in.length()));
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
            System.out.print("\rwhat should the save file be called:" + (clean ? "" : "                  \033[17D"));
            Save.state(players, userInput, currentLanguage);
            System.exit(0);
            break;
        }
      }
      if(turn(true)){
        break;
      }
    }
    System.out.print("\rsaving game\nwhat should the save file be called:" + (clean ? "" : "                  \033[17D"));
    Save.state(players, userInput, currentLanguage);
  }

  public static void init(LanguageCode lang) {
    if(lang == null)
      currentLanguage = Language.getLanguage(userInput);
    else
      currentLanguage = lang;
    ValueReader.loadValues();
    ValueReader_for_ekstra_turn.loadValues();
    Lang.loadLang(currentLanguage);
    table = new Board(6, currentLanguage);
  }
/**
 * 
 * @return whether someone won
 */
  public static boolean turn(boolean print) {
    Tile currentTile = table.makeMove();
    Lang.moveToStartFromInput();
    previousPlayer = currentPlayer;
    if(players[currentPlayer].addGold(currentTile.value)){
      System.out.println("player " + currentPlayer + " wins!");
      return true;
    }
    if(!currentTile.extraTurn){
      currentPlayer++;
      currentPlayer %= players.length;
    }
    turnNumber++;
    if(print)
    PrintUI(currentTile);
    Lang.moveToInput();
    return false;
  }

  private static void PrintUI(Tile currentTile) {
    String UI  = Lang.getUI(currentLanguage);
    if(currentTile == null){
      UI = UI.replace("\uE001", "" + (currentPlayer + 1));
      UI = UI.replace("\uE002", " ");
      UI = UI.replace("\uE003", " ");
      UI = UI.replace("\uE004", " ");
      UI = UI.replace("\uE005", "" + players[currentPlayer].getGold());
      UI = UI.replace("\uE006", "" + players[0].getGold() + "   ");
      UI = UI.replace("\uE007", "" + players[1].getGold() + "   ");
      UI = UI.replace("\uE008", "" + turnNumber);
      UI = UI.replace("\uE009", " ");
      UI = UI.replace("\uE00A", " ");
      UI = UI.replace("\uE00B", "" + ROLL_COMMAND);
      UI = UI.replace("\uE00C", "" + SAVE_COMMAND);
      UI = UI.replace("\uE00D", "" + EXIT_COMMAND);
      UI = UI.replace("\uE00E", "" + (previousPlayer + 1));
    }else{
      UI = UI.replace("\uE001", "" + (currentPlayer + 1));
      UI = UI.replace("\uE002", "" + table.getCup().getSides()[0]);
      UI = UI.replace("\uE003", "" + table.getCup().getSides()[1]);
      UI = UI.replace("\uE004", "" + (table.getCup().getSides()[0] + table.getCup().getSides()[1]) + "  ");
      UI = UI.replace("\uE005", "" + players[currentPlayer].getGold());
      UI = UI.replace("\uE006", "" + players[0].getGold() + "   ");
      UI = UI.replace("\uE007", "" + players[1].getGold() + "   ");
      UI = UI.replace("\uE008", "" + turnNumber);
      UI = UI.replace("\uE009", "" + currentTile.text + "  ");
      UI = UI.replace("\uE00A", "" + currentTile.number + "  ");
      UI = UI.replace("\uE00B", "" + ROLL_COMMAND);
      UI = UI.replace("\uE00C", "" + SAVE_COMMAND);
      UI = UI.replace("\uE00D", "" + EXIT_COMMAND);
      UI = UI.replace("\uE00E", "" + (previousPlayer + 1));
    }
    System.out.println(UI);
  }

  /**
   * only for testing
   * @param table
   */
  public static void setData(Board table, LanguageCode lang, Player[] players){
    Main.table = table;
    Main.currentLanguage = lang;
    Main.players = players;
  }

  public static int getCurrentPlayer(){
    return currentPlayer;
  }

  public static Player[] getplayers() {
    return players;
  }
}
