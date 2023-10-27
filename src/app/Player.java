package app;

import lang.Language.LanguageCode;

public class Player {
  private static final int winningGold = 3000;
  private int gold;
  private int ID;
  public Player(int ID, int startingGold){
    this.gold = startingGold;
    this.ID = ID;
  }
  public boolean addGold(int Gold){
    int prevGold = this.gold;
    this.gold += Gold;
    if(this.gold < 0){
      this.gold = 0;
    }
    if(this.gold == prevGold){
      return false;
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
  public String toHash(LanguageCode lang) {
    long hash = 0;
    hash |= ID;
    hash |= gold<<2;
    hash <<=lang.ordinal(); 
    return (~hash < 0 ? "-" : "") + Long.toHexString(Math.abs(~hash));
  }
  /**
   * supports ID: 0,1,2,3
   * @param hash the hashes of players
   * @return an array of players from the hash
   */
  public static Player[] fromHash(long[] hash, LanguageCode lang){
    Player[] players = new Player[hash.length];
    for (int i = 0; i < players.length; i++) {
      players[i] = new Player((int) ((~(hash[i] >> lang.ordinal())) & 0b11), (int) ((~(hash[i] >> lang.ordinal())) >> 2));
    }
    return players;
  }
}
