package test;

public class Fail implements Response {
  public Fail(String test){}

  @Override
  public void print() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'print'");
  }

  @Override
  public boolean didPass() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'didPass'");
  }
}
