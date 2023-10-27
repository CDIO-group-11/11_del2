package test;

public class TestException extends Exception{
  private String message;
  public TestException(String string) {
    this.message = string;
  }
  public String getStacktrace(){
    String out = message + "\n\n" + this.toString() + "\n";
    for (StackTraceElement stack : getStackTrace()) {
      out += "\t" + stack.toString() + "\n";
    }
    return out;
  }
}
