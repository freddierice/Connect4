/**
 * Class Choose
 * 
 * @author Freddie Rice
 * @version 1.0
 */

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Choose extends JFrame implements ActionListener
{
    private Vector stringVector;
    private Vector nameVector;
    private JList box;
    private JButton button;
    private JProgressBar progressBar;
    private String choice;
    private String myAddress;
    boolean hasChoice;

    public Choose( String s )
    {
        myAddress = s;
        hasChoice = false;
        stringVector = new Vector();
        nameVector = new Vector();
        initComps();
    }

    public void initComps()
    {
        box = new JList();
        box.setListData( stringVector );

        box.setPreferredSize( new Dimension( 300, 150) );
        box.setLayoutOrientation( JList.VERTICAL );

        button = new JButton( "Connect..." );
        button.setPreferredSize( new Dimension( 100, 25 ) );
        button.addActionListener(this);

        progressBar = new JProgressBar();
        progressBar.setIndeterminate( true );
        progressBar.setStringPainted( true );
        setBarStatus( false );

        this.add( box, BorderLayout.NORTH );
        this.add( button, BorderLayout.CENTER );
        this.add( progressBar, BorderLayout.SOUTH );
        this.pack();
        
        addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });
            
        this.setVisible( true );
    }

    public void addString( String s, String name )
    {
        boolean temp = false;

        if( s.equals( myAddress ) )
        {
            temp = true;
        }

        for( int i = 0; i < stringVector.size(); ++i )
        {
            if( stringVector.get( i ).equals( s ) )
            { 
                temp = true;
                break;
            }
        }

        if( !temp )
        {
            stringVector.add( s );
            nameVector.add( name );
            update();
        }
    }

    public void removeString( int i )
    {
        stringVector.remove( i );
        nameVector.remove( i );
        update();
    }

    public boolean getHasChoice()
    {
        return hasChoice;
    }

    public String getChoice()
    {
        return choice;
    }

    public void setBarStatus( boolean x )
    {
        if( x )
        {
            progressBar.setString( "Connecting..." );
        }else{
            progressBar.setString( "Searching..." );
        }
    }

    public void update()
    {
        box.setListData( nameVector );
    }

    public void actionPerformed(ActionEvent e) 
    {
        if( (box.getSelectedValues()).length == 0 )
            return;
        if( ((String)(box.getSelectedValues()[0])) != null )
        {
            choice = (String)stringVector.elementAt( box.getSelectedIndex() );
            hasChoice = true;
        }
    }
}