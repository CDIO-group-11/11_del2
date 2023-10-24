package test;

import app.Board;
import app.Tile;
import lang.Language.LanguageCode;

public class Test_Board extends Board {
  private int finalSum;
  public Test_Board(int numberOfSides, LanguageCode lang, int finalSum) {
    super(numberOfSides, lang);
    this.finalSum = finalSum;
  }
  public Tile makeMove(){
    return tiles[finalSum];
  }
}
