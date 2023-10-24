package test;

import java.util.ArrayList;

import app.Board;
import app.Main;
import app.RaffleCup;
import lang.Lang;
import lang.Language.LanguageCode;

public class Test {
  private static RaffleCup cup = new RaffleCup(2, 6);
  private static ArrayList<Response> responses = new ArrayList<Response>();
  public static void main(String[] args) {
    int runCount = 1000;
    responses.add(isFair(runCount));
    responses.add(isFast(runCount));
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
      mean += sides[i];
    }
    mean /= (double)sides.length;
    double deviation = 0;
    for (int i = 0; i < sides.length; i++) {
      deviation += Math.pow(sides[i]-mean,2);
    }
    deviation = Math.sqrt(deviation*1d/(double)sides.length);
    if(deviation < (mean/10f) && mean < ((runCount/3f)*1.1f) && mean > ((runCount/3f)*0.9f)){
      return new Pass("isFair","dice fairness\n\tmean: " + mean +"\n\tdeviation: " + deviation);
    }else{
      return new Fail("isFair","dice fairness\n\tmean: " + mean +"\n\tdeviation: " + deviation);
    }
  }

  private static Response isFast(int runCount) {
    long start = System.currentTimeMillis();
    Lang.loadLang(LanguageCode.en);
    for (int i = 0; i < runCount; i++) {
      Main.turn();
    }
    System.out.println("\n".repeat(3));
    System.out.print("\033[H\033[2J");
    System.out.flush();
    long end = System.currentTimeMillis();
    if(end-start < 333.333f*runCount){
      return new Pass("isFast","dice speed\n\ttime taken: " + (end-start) + "ms\n\tallowed: " + (333.333f*runCount) + "ms");
    }else{
      return new Fail("isFast","dice speed\n\ttime taken: " + (end-start) + "ms\n\tallowed: " + (333.333f*runCount) + "ms");
    }
  }
}
