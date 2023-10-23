package app;

import lang.Lang;
import lang.Language.LanguageCode;

public class Board {
  private Tile[] tiles;
  private RaffleCup cup;
  public Board(int numberOfSides, LanguageCode lang){
    tiles = new Tile[numberOfSides*2-1];
    cup = new RaffleCup(2, numberOfSides);
    for (int i = 0; i < tiles.length; i++) {
      tiles[i] = new Tile(ValueReader.getTileValue(i+1),Lang.getTileText(lang, numberOfSides),i+2);
    }
  }
  public Tile makeMove(){
    cup.roll();
    return tiles[cup.getSides()[0]+cup.getSides()[1]-2];
  }
  public RaffleCup getCup() {
    return cup;
  }
}
