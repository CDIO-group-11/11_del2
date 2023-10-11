package app;

import lang.Lang;
import lang.Language;

public class Board {
  private Tile[] tiles;
  private RaffleCup cup;
  public Board(int numberOfSides){
    tiles = new Tile[numberOfSides*2-1];
    cup = new RaffleCup(2, numberOfSides);
    for (int i = 0; i < tiles.length; i++) {
      tiles[i] = new Tile(0,Lang.getText(Language.LanguageCode.da,i));
    }
  }
  public Tile makeMove(){
    cup.roll();
    return tiles[cup.getSides()[0]+cup.getSides()[1]-1];
  }
}
