/**
 * Class Player
 * 
 * @author Freddie Rice
 * @version 1.0
 */

public class Player
{
    private boolean isMyTurn, isWinner;
    private int wins, losses, draws;
    private String name;

    public Player( String NAME )
    {
        name = NAME;
        isWinner = false;
        isMyTurn = false;
        wins = 0;
        losses = 0;
    }
    
    public boolean getIsMyTurn()
    {   return isMyTurn;    }
    
    public void setIsMyTurn( boolean x )
    {   isMyTurn = x;   }
    
    public void addWin()
    {   
        ++wins;
        isWinner = true;
    }
    
    public void addLoss()
    {   
        ++losses;
        isWinner = false;
    }
    
    public void addDraw()
    {
        ++draws;
        isWinner = false;
    }
    
    public int getLosses()
    {   return losses;  }
    
    public int getWins()
    {   return wins;    }
    
    public int getDraws()
    {   return draws;   }
    
    public boolean getIsWinner()
    {   return isWinner;    }
    
    public void setIsWinner( boolean x )
    {
        isWinner = x;
    }
    
    public String getName()
    {
        return name; 
    }
}