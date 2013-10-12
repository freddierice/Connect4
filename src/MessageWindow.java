/**
 * Class MessageWindow
 * 
 * @author Freddie Rice
 * @version 1.0
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class MessageWindow extends JFrame implements ActionListener
{
    private JButton button1, button2;
    private String Message, bString1, bString2;
    private boolean hasAnswer;
    private boolean answer;

    public MessageWindow( String m, String b1, String b2 )
    {
        this.setAlwaysOnTop( true );
        hasAnswer = false;
        
        Message = m;
        bString1 = b1;
        bString2 = b2;
        
        this.initComp();
        this.setVisible( true );
        this.pack();
    }

    public void initComp()
    {
        button1 = new JButton( bString1 );
        button2 = new JButton( bString2 );

        JLabel label = new JLabel( Message );

        button1.setSize( 100, 50 );
        button2.setSize( 100, 50 );
        
        button1.addActionListener( this );
        button2.addActionListener( this );

        JPanel pan1 = new JPanel();
        pan1.add( button1, BorderLayout.WEST );
        pan1.add( button2, BorderLayout.EAST );

        JPanel pan2 = new JPanel();
        pan2.add( label );

        this.getContentPane().add( pan1, BorderLayout.SOUTH );
        this.getContentPane().add( pan2, BorderLayout.NORTH );
    }

    public void actionPerformed(ActionEvent e) 
    {
        hasAnswer = true;
        
        if( e.getSource().equals( button1 ))
        {
            answer = true;
        }else if( e.getSource().equals( button2 ) ){
            
            answer = false;
        }
        
        this.setVisible( false );
    }
    
    public boolean getHasAnswer()
    {
        return hasAnswer;
    }
    
    public boolean getAnswer()
    {
        return answer;
    }
}