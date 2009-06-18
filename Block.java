import java.util.*;

public class Block
{
  Board board;
  ArrayList<Square> squares = new ArrayList<Square>();
  String color;
  String state; // Can be "Frozen" or "Falling"
  Mass mass;

  static String[] color_options = {"Red", "Green", "Blue", "Yellow"};
  static Random rand = new Random();

  public static Block randomBlock(Square location, Board board)
  {
    String random_color = color_options[rand.nextInt(color_options.length)];    

    Block toReturn = new Block(random_color, board);
    toReturn.addSquare(location);   

    toReturn.growRandomly(2);
    toReturn.setState("Falling");
  
    return toReturn;
  }

  public void setState(String s)
  {
    state = s;
  }
  
  public void growRandomly(int times)
  {
    for(int i = 0; i < times; i++)
      growRandomly();
  }

  public void growRandomly()
  {
    if(squares.size() == 0) return;

    ArrayList<Square> toAdd = new ArrayList<Square>();
    for(Square square : squares)
    {
      List<Square> nearby = board.nearTo(square);
      
      for(Square near : nearby)
      {
        int coin_flip = rand.nextInt(2);
        
        if(coin_flip == 1)
        {
          toAdd.add(near);
        }
      }
    }

    for(Square square : toAdd)
          addSquare(square);
  }

  public void move(Direction direction)
  {
    ArrayList<Square> newSquares = new ArrayList<Square>();
    
    for(Square square : squares)
    {
      newSquares.add(board.findSquare(square, direction));
    }

    if(newSquares.contains(null))
    {
      freeze();
      System.out.println("Couldn't move off the edge: freezing block");
  
      return;
    }

    for(Square square : newSquares)
    {
      if(square.isOccupied() && !square.isPartOf(this))
      {
        freeze();
        System.out.println("Couldn't move into another block: freezing block");
        return;
      }
    }
    
    moveTo(newSquares);
  }

  public void setMass(Mass aMass)
  {
    mass = aMass;
  }

  public void freeze()
  {
    state = "Frozen";

    ArrayList<Block> newMass = adjacentBlocks();
    if(!newMass.contains(this))
      newMass.add(this);

    board.makeMass(newMass); 
  }

  public ArrayList<Block> adjacentBlocks()
  {
    ArrayList<Square> adjacent = new ArrayList<Square>();

    for(Square square : squares)
    {
      Square north = board.findSquare(square, Direction.north());
      Square south = board.findSquare(square, Direction.south());
      Square east = board.findSquare(square, Direction.east());
      Square west = board.findSquare(square, Direction.west());

      if(north != null && !north.isPartOf(this) && !adjacent.contains(north))
        adjacent.add(north); 
      if(south != null && !south.isPartOf(this) && !adjacent.contains(south))
        adjacent.add(south); 
      if(east != null && !east.isPartOf(this) && !adjacent.contains(east))
        adjacent.add(east); 
      if(west != null && !west.isPartOf(this) && !adjacent.contains(west))
        adjacent.add(west); 
    }

    ArrayList<Block> adjacentBlocks = new ArrayList<Block>();

    for(Square square : adjacent)
    {
      if(!adjacentBlocks.contains(square.block) && square.block != null)
      {
        adjacentBlocks.add(square.block);
      }
    }

    return adjacentBlocks;
  }


  public void moveTo(ArrayList<Square> newSquares)
  {
    ArrayList<Square> toRemove = new ArrayList<Square>();
    for(Square square : squares)
      toRemove.add(square);
    
    for(Square square : toRemove)
      removeSquare(square);

    ArrayList<Square> temp = new ArrayList<Square>();
    
    for(Square square : newSquares)
      addSquare(square);
  }


  public Block(String color, Board board)
  {
    this.board = board;
    this.color = color;
  }

  public void print()
  {
    System.out.print(color.substring(0,1) + " ");
  }

  public void addSquare(Square s)
  {
    squares.add(s);
    s.partOf(this);
  }

  public void removeSquare(Square s)
  {
    s.clear();
  }

  public void cleanUp()
  {
    ArrayList<Square> toRemove = new ArrayList<Square>();
    for(Square square : squares)
    {
      if(!square.isOccupied())
        toRemove.add(square);
    }

    squares.removeAll(toRemove);
  }

  public boolean falling()
  {
    return state == "Falling";
  }

  public void clear()
  {
    for(Square square : squares)
      square.clear();
    
    cleanUp();
  }
}
