/**
 * Class Yell
 * 
 * @author Freddie Rice
 * @version 1.0
 */

import java.lang.Runnable;
import java.io.*;
import java.net.*;
import java.util.*;

public class Yell implements Runnable
{
    InetAddress addr;               //to store the current address
    DatagramPacket packet;          //to store the packet information to send to other use.
    MulticastSocket socket;         //socket to connect to the multicast group.
    Listen listen;                  //holds address to the listen object stored in the SetupSocket class defined in Main.

    public Yell( Listen l, String name ) throws Exception
    {
        //initializes the variables
        listen = l;
        addr = InetAddress.getLocalHost();
        String address = addr.getHostAddress();
        address = name + "@" + address;

        //connects to multicast group 235.7.8.9 on port 4444
        InetAddress group = InetAddress.getByName("235.7.8.9");
        socket = new MulticastSocket(4444);
        socket.joinGroup(group);

        //initializes the packets to send to the multicast group.
        packet = new DatagramPacket(address.getBytes(), address.length() ,group, 4444);
    }

    public void run()
    {
        //sends packets on a seperate thread every second
        while( true )
        {
            try{
                socket.send( packet );
                System.out.println("Sent my address...");
            }catch( IOException e ){}

            try{
                Thread.sleep(1000);
            }catch( InterruptedException e ){}
        }
    }
}