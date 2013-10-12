/**
 * Class ChatUI
 * 
 * @author Freddie Rice
 * @version 1.0
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.lang.Runnable;
import java.io.*;
import java.net.*;
import java.util.*;

public class ChatUI extends JFrame implements ActionListener, Runnable
{
    final int port = 4445;
    String host = "236.7.8.9";

    JTextField textField;
    JTextArea textArea;
    String name1, name2;

    InetAddress group;
    MulticastSocket socket;
    DatagramPacket packet;
    DatagramPacket recv;
    
    Utility util;

    public ChatUI( String NAME1, String NAME2 ) throws Exception
    {
        name1 = NAME1;
        name2 = NAME2;

        group = InetAddress.getByName( host );
        socket = new MulticastSocket( port );
        socket.joinGroup(group);
        
        util = new Utility();
        
        initComponents();
    }

    public void initComponents()
    {
        textField = new JTextField();
        Dimension dimension1 = new Dimension( 300, 50);
        Dimension dimension2 = new Dimension( 310, 80);
        Dimension dimension3 = new Dimension( 320, 600);
        textField.setPreferredSize( dimension1 );
        textArea = new JTextArea(300, 500);
        
        textArea.disable();
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize( dimension3 );
        JPanel pane = new JPanel();
        pane.setPreferredSize( dimension2 );
        pane.add( textField );
        
        textField.addActionListener(this);

        this.add( scrollPane, BorderLayout.NORTH );
        this.add( pane, BorderLayout.SOUTH );
        this.pack();
        this.move(500, 0);
        this.setVisible( true );
    }

    public void actionPerformed(ActionEvent e) 
    {
        String s = textField.getText();
        s = "[" + name1 + "] " + s; 
        textField.setText("");
        packet = new DatagramPacket( s.getBytes(), s.length(), group, port);
        try{
            socket.send( packet );
        }catch( IOException exception ){}
    }

    public void run()
    {
        while( true )
        {
            byte[] buf = new byte[1000];
            DatagramPacket recv = new DatagramPacket(buf, buf.length);
            try{
                socket.receive(recv);
            }catch(IOException e){}

            String temp = new String(recv.getData());
            temp = temp.trim();
            
            if( util.nameFromMessage(temp).equals( name1 ) || util.nameFromMessage(temp).equals( name2 ) )
                textArea.append( temp + "\n");
        }
    }
}