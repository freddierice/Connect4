/**
 * Class Main
 * 
 * @author Freddie Rice
 * @version 1.0
 */

import javax.swing.*;
import java.applet.*;
import java.applet.Applet;

public class Main
{
    public static void main( String[] args ) throws Exception
    {
        //input the users name
        InputString input = new InputString( "What is your name?" );
        while( !(input.getAnswer()) ){  Thread.sleep( 200 );  }
        String myName = input.getString();
        
        //Start Listening for the address.
        Listen l = new Listen();
        Thread listenThread = new Thread( l );
        listenThread.start();

        //start giving out the address
        Yell y = new Yell( l, myName );
        Thread yellThread = new Thread( y );
        yellThread.start();

        //open the window to choose the correct address.
        Choose chooser = new Choose( l.getMyAddress() );
        String chosenAddress = "";
        
        //check to see if the listener has gotten the address or if the
        //user has chosen a person to play against.
        while( true )
        {
            while( !l.hasAddress() )
            {
                if( chooser.getHasChoice() )
                    break;
                Thread.sleep( 200 );
            }
                                                                            
            if( chooser.getHasChoice() )
            {
                chosenAddress = chooser.getChoice();
                chooser.setBarStatus( true );
                break;
            }else{
                chooser.addString( l.getAddress(), l.getName() );
                l.setGotAddress( false );
            }
        }

        //start the sockets with the address chosen by the user.
        SocketClass s = new SocketClass( chosenAddress );
        Thread t = new Thread( s );
        t.start();
        s.startClient();

        //wait for the connection to be complete
        while( !(s.serverIsDone()) )
        {
            Thread.sleep(200);
        }

        boolean gameIsOver = false;

        chooser.setVisible( false );
        
        //Initialize the board
        MainGUI game = new MainGUI( s );
        
        //Initialize the main Frame
        JFrame frame = new JFrame();
        frame.add( game );
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        frame.setSize( 500, 500 * 6 / 7);
        frame.setVisible(true);
        frame.disable();
        
        //Initialize the player classes with their names
        Player p1 = new Player( myName );
        s.printString( myName );
        String player2Name = s.readString();
        Player p2 = new Player( player2Name );
        
        //Start the chat client
        ChatUI chat = new ChatUI( myName, player2Name );
        Thread chatThread = new Thread( chat );
        chatThread.start();
        
        //Initialize the utility class and sound files.
        Utility util = new Utility();
        AudioClip winClip = Applet.newAudioClip( util.stringToURL( "win.wav" ));
        AudioClip loseClip = Applet.newAudioClip( util.stringToURL( "lose.wav" ));
        
        //choose who goes first based off who chooses the largest random number
        int random, other;
        while( true )
        {
            random = (int)(Math.random() * 10000 % 1000 );
            s.printString( random + "" );
            String tempString = s.readString();
            
            tempString = tempString.trim();
            other = Integer.parseInt(tempString);

            if( random > other )
            {
                p1.setIsMyTurn( true );
                p2.setIsMyTurn( false );
                break;
            }else if( random < other ){
                p1.setIsMyTurn( false );
                p2.setIsMyTurn( true );
                break;
            }
        }

        while( true )
        {
            while( !gameIsOver )
            {
                if( p1.getIsMyTurn() )
                {
                    frame.enable();
                    game.setWait( true );
                    while( game.getWait() )
                    {
                        Thread.sleep( 5 );
                    }
                    frame.disable();

                    if( game.checkForWinner() )
                    {
                        p1.addWin();
                        p2.addLoss();
                        gameIsOver = true;
                        break;
                    }else if( game.checkForDraw() ){
                        
                        p1.addDraw();
                        p2.addDraw();
                        gameIsOver = true;
                        break;
                    }

                    p1.setIsMyTurn( false );
                    p2.setIsMyTurn( true );
                }else{
                    
                    //read a string from the other user.  formats it
                    //so the game can use it.
                    String temp = s.readString();
                    temp = temp.trim();
                    int x = Integer.parseInt( temp );

                    //add the move to the right column.
                    //I don't have to check here for a valid move
                    //because the other computer has checked.
                    game.addToColumn( x , 2);

                    if( game.checkForWinner() )
                    {
                        p1.addLoss();
                        p2.addWin();
                        gameIsOver = true;
                        break;
                    }else if( game.checkForDraw() ){
                        p1.addDraw();
                        p2.addDraw();
                        gameIsOver = true;
                        break;
                    }
                    
                    p1.setIsMyTurn( true );
                    p2.setIsMyTurn( false );
                }

                yellThread.stop();
            }

            frame.disable();
            MessageWindow w;
            
            //acts based off of who won.  Save who won locally in an int.
            //0 is win, 1 is loss, 2 is draw
            int iAmWinner;
            if( p1.getIsWinner() && !(p2.getIsWinner()) )
            {
                w = new MessageWindow( p1.getName() + " is the winner!  Would you like to play again?", "Yes", "No" );
                winClip.play();
                iAmWinner = 0;
            }else if( !(p1.getIsWinner()) && p2.getIsWinner() ){
                w = new MessageWindow( p2.getName() + " is the winner!  You lose!  Would you like to play again?", "Yes", "No" );
                loseClip.play();
                iAmWinner = 1;
            }else{
                w = new MessageWindow( p1.getName() + " and " + p2.getName() + " tied! Would you like to play again?", "Yes", "No" );
                iAmWinner = 2;
            }
                
            while( !w.getHasAnswer() ){ Thread.sleep( 200 ); }

            if( !w.getAnswer() )
            {
                s.printString( "notAgain" );
            }else{
                s.printString( "again" );
            }

            String recvString = s.readString();
            recvString = recvString.trim();

            //Make sure both parties want to play again before going again.
            if( !(recvString.equals( "again" )) || !(w.getAnswer()) )
            {
                MessageWindow m = new MessageWindow( "Thanks for playing! You had " + p1.getWins() + " wins, " + p1.getDraws() + " draws, and " + p1.getLosses() + " losses.", "Cancel", "OK" );
                while( true )
                {
                    if( m.getHasAnswer() )
                        break;
                        
                    Thread.sleep( 100 );
                }
                frame.setVisible( false );
                break;
            }else{
                //If they want to play again, reset the board and turns.
                
                p1.setIsWinner( false );
                p2.setIsWinner( false );
                
                gameIsOver = false;
                game.resetBoard();
                
                //let loser go first.
                if( iAmWinner == 0 )
                {
                    p1.setIsMyTurn( true );
                    p2.setIsMyTurn( false );
                }else if( iAmWinner == 1 ){
                    p1.setIsMyTurn( false );
                    p2.setIsMyTurn( true );
                }else{
                    //If they drew, let the computer decide.
                }
            }
        }
        
        System.exit( 0 );
    }
}