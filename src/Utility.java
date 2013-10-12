/**
 * Class Utility
 * 
 * @author Freddie Rice
 * @version 1.0
 */

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.applet.*;
import java.net.URL;

public class Utility
{
    public Utility()
    {

    }

    public URL stringToURL( String s )
    {
        return this.getClass().getResource( s );
    }

    public String stripName( String s )
    {
        int index = 0;
        for( int i = 0; i < s.length(); ++i)
        {
            if( s.charAt(i) == '@' )
            {
                index = i;
                break;
            }
        }
        return s.substring( index + 1 );
    }

    public String stripAddress( String s )
    {
        int index = 0;
        for( int i = 0; i < s.length(); ++i)
        {
            if( s.charAt(i) == '@' )
            {
                index = i;
                break;
            }
        }
        return s.substring( 0, index );
    }

    public String nameFromMessage( String s )
    {
        int index = 0;
        for( int i = 0; i < s.length(); ++i )
        {
            if( s.charAt(i) == ']' )
            {
                index = i;
                break;
            }
        }
        return s.substring( 1, index );
    }
}