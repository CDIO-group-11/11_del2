package test;

import java.util.ArrayList;

import app.Board;
import app.Main;
import app.Player;
import app.RaffleCup;
import app.ValueReader;
import app.ValueReader_for_ekstra_turn;
import lang.Lang;
import lang.Language.LanguageCode;

public class Test {
  private static RaffleCup cup = new RaffleCup(2, 6);
  private static ArrayList<Response> responses = new ArrayList<Response>();
  private static LanguageCode currentLang = LanguageCode.en;
  public static void main(String[] args) {
    int runCount = 1000;
    ValueReader.loadValues();
    ValueReader_for_ekstra_turn.loadValues();
    Lang.loadLang(currentLang);
    Main.setData(new Board(6, currentLang),currentLang, new Player[]{new Player(0, 1000), new Player(1, 1000)});
    responses.add(isFair(runCount));
    responses.add(isFast(runCount));
    responses.add(negativeGold(runCount));
    responses.add(werewall(runCount));
    responses.add(correctGold());
    responses.add(exception());
    
    System.out.print("\033[H\033[2J");
    System.out.flush();
    System.out.println("\n".repeat(2));
    int passes = 0;
    for (int i = 0; i < responses.size(); i++) {
      responses.get(i).print();
      passes += responses.get(i).didPass() ? 1 : 0;
    }
    System.out.println(
      "passed "  + passes + " out of " + responses.size() + " tests\n" + 
      "this is equivalent to a " + ((float) (passes * 100) / (float) responses.size() + "% pass rate")
    );
    System.out.println("\ndetails:");
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
      Math.pow(1 - mean, 2) * (1d / 6d) +
      Math.pow(2 - mean, 2) * (1d / 6d) +
      Math.pow(3 - mean, 2) * (1d / 6d) +
      Math.pow(4 - mean, 2) * (1d / 6d) +
      Math.pow(5 - mean, 2) * (1d / 6d) +
      Math.pow(6 - mean, 2) * (1d / 6d)
    );
    String strMean = ("" + mean).length() <= 5 ? ("" + mean) : ("" + mean).substring(0,5);
    String strDeviation = ("" + deviation).length() <= 5 ? ("" + deviation) : ("" + deviation).substring(0,5);;
    
    String strFairMeanMax = ("" + (fairMean * 1.1d)).length() <= 5 ? ("" + (fairMean * 1.1d)) : ("" + (fairMean * 1.1d)).substring(0,5);;
    String strFairMeanMin = ("" + (fairMean / 1.1d)).length() <= 5 ? ("" + (fairMean / 1.1d)) : ("" + (fairMean / 1.1d)).substring(0,5);;
    String strFairDeviationMax = ("" + (fairDeviation * 1.1d)).length() <= 5 ? ("" + (fairDeviation * 1.1d)) : ("" + (fairDeviation * 1.1d)).substring(0,5);;
    String strFairDeviationMin = ("" + (fairDeviation / 1.1d)).length() <= 5 ? ("" + (fairDeviation / 1.1d)) : ("" + (fairDeviation / 1.1d)).substring(0,5);;
    String name = "isFair";
    String data = 
      "dice fairness\n\t" + 
      "mean: " + strMean + " allowed max/min: " + strFairMeanMax + "/" + strFairMeanMin + "\n\t" + 
      "deviation: " + strDeviation + " allowed max/min: " + strFairDeviationMax + "/" + strFairDeviationMin;
    if(
      deviation < (fairDeviation * 1.1d) &&
      deviation > (fairDeviation / 1.1d) && 
      mean < (fairMean) * 1.1d && 
      mean > (fairMean) / 1.1d
      ){
      return new Pass(name,data);
    }else{
      return new Fail(name,data);
    }
  }

  private static Response isFast(int runCount) {
    int i = 0;
    long end = 0;
    long start = System.currentTimeMillis();
    while (i < runCount) {
      if(Main.turn(true)){
        Main.setData(new Board(6, currentLang),currentLang, new Player[]{new Player(0, 1000), new Player(1, 1000)});
      }
      i++;
    }
    end = System.currentTimeMillis();
    System.out.flush();
    String name = "isFast";
    String data = "dice speed\n\ttime taken: " + ((float)(end-start)/(float)(runCount)) + "ms\n\tallowed: " + (333.333f) + "ms";
    if(end-start < 333.333f*runCount){
      return new Pass(name,data);
    }else{
      return new Fail(name,data);
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
    int player = Main.getCurrentPlayer();
    for (int i = 0; i < runCount; i++) {
      Main.turn(false);
      if(Main.getCurrentPlayer() != player){
        return new Fail("werewall");
      }
    }
    Main.setData(new Test_Board(6,currentLang,9), currentLang, new Player[]{new Player(0, 1000), new Player(1, 1000)});
    for (int i = 0; i < runCount/2; i++) {
      Main.turn(false);
      Main.setData(new Test_Board(6,currentLang,9), currentLang, new Player[]{new Player(0, 1000), new Player(1, 1000)});
      if(Main.getCurrentPlayer() == player){
        return new Fail("werewall");
      }
      player = Main.getCurrentPlayer();
    }
    Main.setData(new Test_Board(6,currentLang,11), currentLang, new Player[]{new Player(0, 1000), new Player(1, 1000)});
    for (int i = 0; i < runCount/2; i++) {
      Main.turn(false);
      Main.setData(new Test_Board(6,currentLang,9), currentLang, new Player[]{new Player(0, 1000), new Player(1, 1000)});
      if(Main.getCurrentPlayer() == player){
        return new Fail("werewall");
      }
      player = Main.getCurrentPlayer();
    }
    return new Pass("werewall");
  }

  private static Response correctGold(){
    Main.setData(new Board(6,currentLang), currentLang, new Player[]{new Player(0, 1000), new Player(1, 1000)});
    Player[] players = Main.getplayers();
    if(players[0].getGold() != 1000 && players[1].getGold() != 1000){
      return new Fail("correctGold");
    }
    return new Pass("correctGold");
  }

  private static Response exception(){
    if(Lang.error(new TestException("this is a test"))){
      return new Pass("exception","the test exception is specified");
    }
    return new Fail("exception","the test exception is not specified");
  }

}