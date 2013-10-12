/**
 * Class InputString
 * 
 * @author Freddie Rice
 * @version 1.0
 */

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InputString implements ActionListener
{
    private boolean answer;
    private String response;
    private JFrame frame;
    JTextField area;

    public InputString( String s ) throws Exception
    {
        answer = false;
        
        frame = new JFrame();
        frame.setSize( 300, 300 );
        
        JPanel pan1 = new JPanel();
        JPanel pan2 = new JPanel();
        
        JButton button1 = new JButton("Enter");
        button1.addActionListener(this);
        
        area = new JTextField();
        area.setSize( 100, 50);
        
        pan1.add( new JLabel( s ) );
        pan2.add( button1 );
        
        frame.add( pan1, BorderLayout.NORTH );
        frame.add( area );
        frame.add( pan2, BorderLayout.SOUTH );
        frame.pack();
        
        frame.setVisible( true );
        while( answer ){    Thread.sleep(100);   }
    }
    
    public String getString()
    {
        return response;
    }
    
    public boolean getAnswer()
    {
        return answer;   
    }
    
    public void actionPerformed(ActionEvent e) 
    {
        response = area.getText();
        answer = true;
        frame.setVisible( false );
    }
}