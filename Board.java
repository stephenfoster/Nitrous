import java.util.*;

public class Board
{
  ArrayList<ArrayList<Square>> rows = new ArrayList<ArrayList<Square>>();
  ArrayList<Block> blocks = new ArrayList<Block>();
  int height;
  int width;
  Random rand = new Random();
  ArrayList<Mass> masses = new ArrayList<Mass>();

  public Board(int width, int height)
  {
    this.height = height;
    this.width = width;
    for(int i = 0; i < height; i++)
    {
      ArrayList<Square> row = new ArrayList<Square>();
      for(int j = 0; j < width; j++)
      {
        row.add(new Square(j,i));
      }
      rows.add(row);
    }
    
  }

  public void update(Direction direction)
  {
    if (direction == null) return;


    boolean noFallingBlocks = true;

    Block lastFaller = null;

    for(Block block : blocks)
    {
      if(block.falling())
      {
        lastFaller = block;
        noFallingBlocks = false;
        block.move(direction);
      }
    }

    if (lastFaller != null)
      removeCompletedLinesFor(lastFaller, direction);

    if(noFallingBlocks)
    {

      newBlock();
    }
  }

  public void makeMass(ArrayList<Block> newMass)
  {
    Mass mass = new Mass();
    for(Block block : newMass)
    {
      if(block.mass != null)
        masses.remove(block.mass);
      mass.merge(block);
    }
    registerMass(mass);
  }

  public void registerMass(Mass aMass)
  {
    if(!masses.contains(aMass))
      masses.add(aMass);
  }

  public void removeCompletedLinesFor(Block block, Direction direction)
  {
    for(Square square : block.squares)
    {
      ArrayList<Square> row = searchEastWest(square);
      ArrayList<Square> col = searchNorthSouth(square);

      if(row != null)
        clearAllSquaresAndGravitate(row, direction);
      if(col != null)
        clearAllSquaresAndGravitate(col, direction);
    }  

    block.cleanUp(); //Gets ride of cleared squares
  }

  public void clearAllSquaresAndGravitate(ArrayList<Square> squares, Direction direction)
  {
    for(Square square : squares)
    {
      square.block.removeSquare(square);
    }
  }

  public ArrayList<Square> searchEastWest(Square square)
  {
    ArrayList<Square> eastPath = searchToWall(square, Direction.east());
    ArrayList<Square> westPath = searchToWall(square, Direction.west());

    if(eastPath != null && westPath != null)
    {
      return mergePaths(eastPath, westPath);
    }

    return null;
  }

  public ArrayList<Square> searchNorthSouth(Square square)
  {
    ArrayList<Square> northPath = searchToWall(square, Direction.north());
    ArrayList<Square> southPath = searchToWall(square, Direction.south());

    if(northPath != null && southPath != null)
    {
      return mergePaths(northPath, southPath);
    }

    return null;
  }

  public ArrayList<Square> searchToWall(Square square, Direction direction)
  {
    ArrayList<Square> ret = new ArrayList<Square>();

    Square nextSquare = square;

    while(nextSquare != null && nextSquare.isOccupied())
    {
      ret.add(nextSquare);
      nextSquare = findSquare(nextSquare, direction);
    }

    if(nextSquare == null)
      return ret; // Because we hit a wall.

    return null; // Because we hit an unoccupied square
  }

  public ArrayList<Square> mergePaths(ArrayList<Square> first, ArrayList<Square> second)
  {
    first.remove(0);
    first.addAll(second);
    return first;
  }

  public Square findSquare(Square reference, Direction direction)
  {
    try{
      ArrayList<Square> row = rows.get(reference.y + direction.yadj);
      return row.get(reference.x + direction.xadj);
    } catch (IndexOutOfBoundsException iob) {
      return null;
    }
  }

  public void newBlock()
  {
    try{
      int rand_height = rand.nextInt(height);
      int rand_width = rand.nextInt(width);
      Block randomBlock = Block.randomBlock(rows.get(rand_height).get(rand_width), this); 
      blocks.add(randomBlock); 
    } catch (IllegalStateException i) {
      newBlock();
    }
  }

  public ArrayList<Square> nearTo(Square square)
  {
    int northRow = square.y -1;
    int northCol  = square.x;

    int southRow = square.y +1;
    int southCol = square.x;

    int eastRow = square.y;
    int eastCol = square.x + 1;

    int westRow = square.y;
    int westCol = square.x - 1;

    Square north, south, east, west;
    north = null;
    south = null;
    east  = null;
    west  = null;

    if (northRow > -1)
    {
       north = rows.get(northRow).get(northCol);
    }

    if (southRow < rows.size())
    {
       south = rows.get(southRow).get(southCol);
    }

    if (eastCol < rows.get(0).size())
    {
       east = rows.get(eastRow).get(eastCol);
    }

    if (westCol > -1)
    {
       west = rows.get(westRow).get(westCol);
    }

    ArrayList<Square> ret = new ArrayList<Square>();

    if (north != null)
      ret.add(north);
    if (south != null)
      ret.add(south);
    if (east != null)
      ret.add(east);
    if (west != null)
      ret.add(west);

    return ret;
  }

  public void print()
  {
    for(ArrayList<Square> row : rows)
    {
      for(Square square : row)
      {
        square.print();
      }
      System.out.println();
    }
  }

  public ArrayList<ArrayList<String>> getColors()
  {
    ArrayList<ArrayList<String>> colors = new ArrayList<ArrayList<String>>();

    for(ArrayList<Square> row : rows)
    {
      ArrayList<String> colorRow = new ArrayList<String>();
      for(Square square : row)
      {
        colorRow.add(square.getColor());
      }
      colors.add(colorRow);
    }

    return colors;
  }
}
