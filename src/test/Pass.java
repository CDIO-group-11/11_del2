package test;

public class Pass implements Response {
  private String test;
  private String data; 
  public Pass(String test, String data){
    this.test = test;
    this.data = data;
  }
    public Pass(String test){
    this.test = test;
    this.data = null;
  }

  @Override
  public void print() {
    System.out.println(test + (test.length()<8 ? "\t" : "") + "\t\u001b[32mPASSED\u001b[0m");

  }

  @Override
  public boolean didPass() {
    return true;
  }
  @Override
  public void printData() {
    if(data != null)
    System.out.println(data);
  }
}
