package test;

public class Fail implements Response {
  private String test;
  private String data;
  public Fail(String test, String data){
    this.test = test;
    this.data = data;
  }
  public Fail(String test){
    this.test = test;
    this.data = null;
  }

  @Override
  public void print() {
    System.out.println(test + (test.length()<8 ? "\t" : "") + "\t\u001b[31mFAILED\u001b[0m");
  }

  @Override
  public boolean didPass() {
    return false;
  }
  @Override
  public void printData() {
    if(data != null)
    System.out.println(data);
  }
}
