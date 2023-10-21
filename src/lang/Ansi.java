package lang;

public class Ansi {
  public static String r() {
    return "\u001b[31m";
  }
  public static String g() {
    return "\u001b[32m";
  }
  public static String b() {
    return "\u001b[34m";
  }
  public static String c() {
    return "\u001b[36m";
  }
  public static String y() {//sometimes has problems
    return "\u001b[38;5;220m";
  }
  public static String p() {
    return "\u001b[35m";
  }
  public static String reset() {
    return "\u001b[0m";
  }
}