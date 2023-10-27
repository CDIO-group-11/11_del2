package app;

public class Tile {
  public String text;
  public int value;
  public int number;
  public boolean extraTurn;
  public Tile(int value, String text, int number, boolean extraTurn){
    this.value = value;
    this.text  = text;
    this.number= number;
    this.extraTurn = extraTurn;
  }
}
