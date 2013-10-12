/**
 * Class Cell
 * 
 * @author Freddie Rice
 * @version 1.0
 */

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.applet.*;

public class Cell extends JButton
{
    private ImageIcon image;        //the image of the piece
    
    //0 = blank, 1 = red, 2 = black
    private int identifier;
    
    public Cell()
    {
        
    }
    
    //set piece to the right color
    public void setIdentifier( int x )
    {
        AudioClip clickClip = Applet.newAudioClip( new Utility().stringToURL( "c.wav" ));
        if( x == 0 )
        {
            image = new ImageIcon( getClass().getResource("blank.gif") );
            identifier = 0;
        }else if( x == 1 )
        {
            image = new ImageIcon( getClass().getResource("RP.gif") );
            identifier = 1;
        }else if( x == 2 ){
            image = new ImageIcon( getClass().getResource("BP.gif") );
            identifier = 2;
        }
        
        this.setIcon( image );    
        clickClip.play();
    }
    
    public int getIdentifier()
    {
        return identifier;
    }
}