/**
 * Class Listen
 * 
 * @author Freddie Rice
 * @version 1.0
 */

import java.lang.Runnable;
import java.io.*;
import java.net.*;
import java.util.*;

public class Listen implements Runnable
{
    private InetAddress addr;
    private MulticastSocket socket;
    private String myAddress;
    private String otherAddress;
    private String name;
    private Utility util;
    private boolean gotAddress;

    public Listen() throws Exception
    {
        util = new Utility();
        otherAddress = "";
        gotAddress = false;
        addr = InetAddress.getLocalHost();
        myAddress = addr.getHostAddress();

        InetAddress group = InetAddress.getByName("235.7.8.9");
        socket = new MulticastSocket(4444);
        socket.joinGroup(group);
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
            name = util.stripAddress( temp );
            temp = util.stripName( temp );

            if( !(myAddress.equals( temp )) && !(myAddress.equals( "127.0.0.1"  )) )
            {
                otherAddress = temp;

                System.out.println("Received Correct Address: " + otherAddress );

                gotAddress = true;
                break;
            }
        }
    }

    public boolean hasAddress()
    {
        return gotAddress;
    }

    public String getAddress()
    {
        return otherAddress;
    }
    
    public String getMyAddress()
    {
        return myAddress;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setGotAddress( boolean x )
    {
        gotAddress = x;
        otherAddress = "";
    }
}