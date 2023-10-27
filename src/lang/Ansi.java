package lang;

import app.Main;

public class Ansi {
  public static String r() {
    if(Main.clean) return "";
    return "\u001b[31m";
  }
  public static String g() {
    if(Main.clean) return "";
    return "\u001b[32m";
  }
  public static String b() {
    if(Main.clean) return "";
    return "\u001b[34m";
  }
  public static String c() {
    if(Main.clean) return "";
    return "\u001b[36m";
  }
  public static String y() {//sometimes has problems
    if(Main.clean) return "";
    return "\u001b[38;5;220m";
  }
  public static String p() {
    if(Main.clean) return "";
    return "\u001b[35m";
  }
  public static String reset() {
    if(Main.clean) return "";
    return "\u001b[0m";
  }
}