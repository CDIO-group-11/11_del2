package app;

public class Board {
  private Tile[] tiles;
  private RaffleCup cup;
  public Board(int numberOfSides){
    tiles = new Tile[numberOfSides*2-1];
    cup = new RaffleCup(2, numberOfSides);
  }
  public Tile makeMove(){
    cup.roll();
    return tiles[cup.getSides()[0]+cup.getSides()[1]-1];
  }
}
