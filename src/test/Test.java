package test;

import java.util.ArrayList;

import app.RaffleCup;

public class Test {
  private static RaffleCup cup = new RaffleCup(2, 6);
  private static ArrayList<Response> responses = new ArrayList<Response>();
  public static void main(String[] args) {
    int runCount = 1000;
    responses.add(isFair(runCount));
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
}
