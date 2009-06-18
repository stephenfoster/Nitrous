import java.util.*;

public class Mass
{
  ArrayList<Block> blocks = new ArrayList<Block>();

  public Mass()
  {

  }

  public void merge(Block block)
  {
    blocks.add(block);
    block.setMass(this);
  }

  public void clear()
  {
    for(Block block : blocks)
      block.clear(); 
  }
}
