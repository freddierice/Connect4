/**
 * Class Server
 * 
 * @author Freddie Rice
 * @version 1.0
 */

import java.io.*;
import java.net.*;
import java.util.*;

public class SocketClass implements Runnable
{
    private boolean serverDone;
    private Socket socket = null;           //the socket accepted from the client
    private Socket clientSocket = null;     //the socket to be accepted by the remote server
    ServerSocket server = null;             //the local server socket
    final int port = 9000;                  //the party port :)
    String host;                            //holds the host to connect to
    DataInputStream in;                     //holds the input stream to the remote server
    BufferedReader d;                       //holds the output stream from the remote server
    PrintWriter out;                        //the writer to the output stream

    public SocketClass()
    {
        serverDone = false;
    }

    public SocketClass(String HOST)
    {
        host = HOST;
        serverDone = false;
    }

    public void printString(String str)
    {
        out.println( str );
    }

    public String readString() throws Exception
    {
        return in.readLine();
    }

    public void close() throws Exception
    {
        clientSocket.close();
        server.close();
    }

    public void setHost( String HOST )
    {
        host = HOST;
    }

    public void startClient()
    {
        while( true )
        {
            try{
                clientSocket = new Socket( host, port );
            }catch( IOException e ){continue;}
            break;
        }
        
        try{
            out = new PrintWriter( clientSocket.getOutputStream(),true );
        }catch( IOException e ){}
    }

    public boolean serverIsDone()
    {
        return serverDone;
    }

    /*
     * This thread is run seperatly so the rest of the main function can 
     * go without waiting.  If it were not on a seperate thread, both sides
     * of the conversation would be listening, and that would be awkward.
     */
    public void run()
    {
        try{
            server = new ServerSocket( port );
            socket = server.accept();
            in = new DataInputStream( socket.getInputStream() );
            d = new BufferedReader( new InputStreamReader(in) );
            serverDone = true;
        }catch( IOException e ){}
    }
}