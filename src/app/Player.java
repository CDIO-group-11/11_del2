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
  /**
   * supports ID: 0,1,2,3
   * @return the hash of this player
   */
  public long toHash() {
    long hash = 0;
    hash |= ID;// or=
    hash |= gold<<2; // or= bitshift
    return ~hash; //not
  }
  /**
   * supports ID: 0,1,2,3
   * @param hash the hashes of players
   * @return an array of players from the hash
   */
  public static Player[] fromHash(Long[] hash){
    Player[] players = new Player[hash.length];
    for (int i = 0; i < players.length; i++) {
      players[i] = new Player((int) ((~hash[i]) & 0b11), (int) ((~hash[i]) >> 2));
    }
    return players;
  }
}
