package app;

public class Player {
  private static final int winningGold = 3000;
  private int gold;
  private int ID;
  public Player(int ID, int startingGold){
    this.gold = startingGold;
    this.ID = ID;
  }
  public boolean addGold(int gold){
    this.gold += gold;
    if(this.gold < 0){
      this.gold = 0;
    }
    return (this.gold > winningGold);
  }
  public int getGold() {
    return gold;
  }
  public int getID() {
    return ID;
  }
}
