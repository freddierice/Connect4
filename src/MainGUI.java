/**
 * Class MainGUI
 * 
 * @author Freddie Rice
 * @version 1.0
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class MainGUI extends JPanel implements ActionListener
{
    private boolean otherIsDone;
    private boolean canMove;
    private boolean wait;
    private final int width = 7;
    private final int height = 6;
    private SocketClass s;

    Cell[][] grid;   

    public MainGUI( SocketClass S )
    {
        s = S;
        //initialize boolean values
        otherIsDone = false;
        wait = false;

        //initialize a 2d array of buttons
        this.setLayout( new GridLayout( height, width) ); 
        grid = new Cell[width][height]; 

        for( int y = 0; y < height; ++y )
        { 
            for( int x = 0; x < width; ++x )
            {
                grid[x][y] = new Cell();
                if( y == 0 )
                    grid[x][y].addActionListener( this );

                this.add( grid[x][y] );
            }
        }
    }

    public void resetBoard()
    {
        for( int y = 0; y < height; ++y )
        { 
            for( int x = 0; x < width; ++x )
            {
                grid[x][y].setIdentifier(0);
            }
        }
    }

    public void paintComponent( Graphics g )
    {
        super.paintComponent( g );
    }

    public boolean checkForWinner()
    {
        int counter = 0;

        //vertical
        for( int x = 0; x < width; ++x ) 
        {
            for( int y = 0; y < 3; ++y ) 
            {
                counter = 0;

                if( grid[x][y].getIdentifier() == 0 )
                    continue;

                while( grid[x][y].getIdentifier() == grid[x][y + counter].getIdentifier() )
                {
                    ++counter;
                    if( counter == 4 )
                    {
                        return true;
                    }
                }
            }
        }

        //horizontal
        for( int x = 0; x < 4; ++x ) 
        {
            for( int y = 0; y < height; ++y ) 
            {
                counter = 0;

                if( grid[x][y].getIdentifier() == 0 )
                    continue;

                while( grid[x][y].getIdentifier() == grid[x + counter][y].getIdentifier() )
                {
                    ++counter;

                    if( counter == 4 )
                    {
                        return true;
                    }
                }
            }
        }

        //down right diagonal
        for( int x = 0; x < 4; ++x ) 
        {
            for( int y = 0; y < 3; ++y ) 
            {
                counter = 0;

                if( grid[x][y].getIdentifier() == 0 )
                    continue;

                while( grid[x][y].getIdentifier() == grid[x + counter][y + counter].getIdentifier() )
                {
                    ++counter;
                    if( counter == 4 )
                    {
                        return true;
                    }
                }
            }
        }

        //down left diagonal
        for( int x = 3; x < width; ++x ) 
        {
            for( int y = 0; y < 3; ++y ) 
            {
                counter = 0;

                if( grid[x][y].getIdentifier() == 0 )
                    continue;

                while( grid[x][y].getIdentifier() == grid[x - counter][y + counter].getIdentifier() )
                {
                    ++counter;
                    if( counter == 4 )
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }
    
    public boolean checkForDraw()
    {
        for( int x = 0; x < 7; ++x )
        {
            for( int y = 0; y < 6; ++y )
            {
                if( grid[x][y].getIdentifier() == 0 )
                {
                    return false;
                }
            }
        }
        
        return true;
    }

    public boolean addToColumn( int x, int identifier )
    {

        for( int i = height - 1; i >= 0; --i )
        {
            if( grid[x][i].getIdentifier() == 0 )
            {
                grid[x][i].setIdentifier( identifier );
                setWait( false );
                return true;
            }
        }

        return false;
    }

    public void actionPerformed(ActionEvent e) 
    {
        for( int i = 0; i < width; ++i )
        {
            if( ((Cell)(e.getSource())) == grid[i][0] )
            {
                if( !this.addToColumn( i, 1) )
                {
                    new MessageWindow("Could not add to column, please try again...", "Cancel", "OK" );
                }else{
                    s.printString( i + "" );
                }
            }
        }
    }

    public void setWait( boolean x )
    {
        wait = x;
    }

    public boolean getWait()
    {
        return wait;
    }
}