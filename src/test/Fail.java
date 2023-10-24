package test;

public class Fail implements Response {
  private String test;
  public Fail(String test, String data){
    this.test = test;
  }
  public Fail(String test){
    this.test = test;
  }

  @Override
  public void print() {
    System.out.println(test + "\t\t\u001b[31mFAILLED\u001b[0m");
  }

  @Override
  public boolean didPass() {
    return false;
  }
}
