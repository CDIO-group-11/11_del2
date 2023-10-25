package test;

import java.util.ArrayList;

import app.Board;
import app.Main;
import app.Player;
import app.RaffleCup;
import app.ValueReader;
import lang.Lang;
import lang.Language.LanguageCode;

public class Test {
  private static RaffleCup cup = new RaffleCup(2, 6);
  private static ArrayList<Response> responses = new ArrayList<Response>();
  private static LanguageCode currentLang = LanguageCode.en;
  public static void main(String[] args) {
    int runCount = 1000;
    ValueReader.loadValues();
    Lang.loadLang(currentLang);
    Main.setData(new Board(6, currentLang),currentLang, new Player[]{new Player(0, 1000), new Player(1, 1000)});
    responses.add(isFair(runCount));
    responses.add(isFast(runCount));
    responses.add(negativeGold(runCount));
    responses.add(werewall(runCount));
    
    int passes = 0;
    for (int i = 0; i < responses.size(); i++) {
      responses.get(i).print();
      passes += responses.get(i).didPass() ? 1 : 0;
    }
    System.out.println(
      "passed "  + passes + " out of " + responses.size() + " tests\n" + 
      "this is equivalent to a " + ((float) (passes * 100) / (float) responses.size() + "% pass rate")
    );
    System.out.println("\n\ndetails:");
    for (int i = 0; i < responses.size(); i++) {
      responses.get(i).printData();
    }
  }

  private static Response isFair(int runCount) {
    int[] sides = new int[6];
    for (int i = 0; i < runCount; i++) {
      cup.roll();
      for (int j : cup.getSides()) {
        sides[j-1]++;
      }
    }
    double mean = 0;
    for (int i = 0; i < sides.length; i++) {
      mean += sides[i] * (i+1);
    }
    mean /= (double)(runCount*2);
    double deviation = 0;
    for (int i = 0; i < sides.length; i++) {
      deviation += Math.pow((i+1)-mean,2)*sides[i];
    }
    deviation = Math.sqrt(deviation / (double)(runCount * 2));
    double fairMean = (double)(1 + 2 + 3 + 4 + 5 + 6) / 6d;
    double fairDeviation = Math.sqrt(
      Math.pow(1 - mean, 2) * (1d / 6d)+
      Math.pow(2 - mean, 2) * (1d / 6d)+
      Math.pow(3 - mean, 2) * (1d / 6d)+
      Math.pow(4 - mean, 2) * (1d / 6d)+
      Math.pow(5 - mean, 2) * (1d / 6d)+
      Math.pow(6 - mean, 2) * (1d / 6d)
    );
    if(
      deviation < (fairDeviation * 1.1d) &&
      deviation > (fairDeviation / 1.1d) && 
      mean < (fairMean) * 1.1d && 
      mean > (fairMean) / 1.1d
      ){
      return new Pass(
        "isFair","dice fairness\n\t" + 
        "mean: " + ("" + mean).substring(0, 5) + " " +"\n\t" + 
        "deviation: " + ("" + deviation).substring(0, 5)
      );
    }else{
      return new Fail(
        "isFair","dice fairness\n\t" + 
        "mean: " + ("" + mean).substring(0, 5) + " " +"\n\t" + 
        "deviation: " + ("" + deviation).substring(0, 5)
      );
    }
  }

  private static Response isFast(int runCount) {
    long start = System.currentTimeMillis();
    for (int i = 0; i < runCount; i++) {
      if(Main.turn(true)){
        Main.setData(new Board(6, currentLang),currentLang, new Player[]{new Player(0, 1000), new Player(1, 1000)});
      }
    }
    System.out.println("\n".repeat(3));
    System.out.print("\033[H\033[2J");
    System.out.flush();
    long end = System.currentTimeMillis();
    if(end-start < 333.333f*runCount){
      return new Pass("isFast","dice speed\n\ttime taken: " + ((float)(end-start)/runCount) + "ms\n\tallowed: " + (333.333f) + "ms");
    }else{
      return new Fail("isFast","dice speed\n\ttime taken: " + ((float)(end-start)/runCount) + "ms\n\tallowed: " + (333.333f) + "ms");
    }
  }

  private static Response negativeGold(int runCount){
    Player player = new Player(0, 1000);
    for (int i = -runCount/2-1000; i < runCount/2-1000; i++) {
      player.addGold(i);
      if(player.getGold() < 0){
        return new Fail("minusGold");
      }
      player = new Player(0, 1000);
    }
    return new Pass("minusGold");
  }

  private static Response werewall(int runCount){
    Main.setData(new Test_Board(6,currentLang,10), currentLang, new Player[]{new Player(0, 1000), new Player(1, 1000)});
    int play = Main.getCurrentPlayer();
    for (int i = 0; i < runCount; i++) {
      Main.turn(false);
      if(Main.getCurrentPlayer() != play){
        return new Fail("werewall");
      }
    }
    return new Pass("werewall");
  }
}
