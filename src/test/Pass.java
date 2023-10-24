package test;

public class Pass implements Response {
  private String test;
  public Pass(String test, String data){
    this.test = test;
  }
    public Pass(String test){
    this.test = test;
  }

  @Override
  public void print() {
    System.out.println(test + "\t\t\u001b[32mPASSED\u001b[0m");
  }

  @Override
  public boolean didPass() {
    return true;
  }
}
