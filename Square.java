public class Square
{
  int x, y;
  Block block = null;

  public Square(int x, int y)
  {
    this.x = x;
    this.y = y;
  }

  public void print()
  {
    if (block == null)
      System.out.print("  ");
    else
      block.print();  
    
  }

  public boolean isPartOf(Block block)
  {
    return this.block == block;
  }

  public void partOf(Block block) throws IllegalStateException
  {
    if(this.block != null && block != this.block)
      throw new IllegalStateException("Unhandled collision!");

    this.block = block;
  }

  public boolean isOccupied()
  {
    return block != null;
  }

  public void clear()
  {
    block = null;
  }

  public String getColor()
  {
    if(block != null)
      return block.color;
    else
      return " ";
  }
}
