public class Direction
{
  int xadj;
  int yadj;

  public static Direction fromKeyboard(String s)
  {
    if (s.equals("a"))
      return west();
    if (s.equals("s"))
      return south();
    if (s.equals("d"))
      return east();
    if (s.equals("w"))
      return north();
    
    System.out.println("NULL DIRECTION! '" + s + "' is not good input");
  
    return null;  
  }

  public Direction(int xadj, int yadj)
  {
    this.xadj = xadj;
    this.yadj = yadj;
  }

  public static Direction north()
  {
    return new Direction(0,-1);
  }

  public static Direction south()
  {
    return new Direction(0,1);
  }

  public static Direction east()
  {
    return new Direction(1,0);
  }

  public static Direction west()
  {
    return new Direction(-1,0);
  }
}
