import java.util.*;

public class Tester
{
  public static void main(String[] args)
  {
    Board board = new Board(10,10);

    board.newBlock();

    Scanner s = new Scanner(System.in);


    String line = ""; 
    while(line != null)
    {
      line = s.next();
      board.update(Direction.fromKeyboard(line+""));
      print(board.getColors());
      System.out.println(board.masses.size());
    }
  }

  public static void print(ArrayList<ArrayList<String>> colors)
  {
    for(ArrayList<String> row : colors)
    {
      for(String s : row)
        System.out.print(s.substring(0,1) + " ");
      System.out.println();
    }
      
  }
}
