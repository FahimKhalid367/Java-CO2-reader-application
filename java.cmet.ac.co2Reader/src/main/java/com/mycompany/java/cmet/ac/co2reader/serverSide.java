/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java.cmet.ac.co2reader;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author fahim
 */
public class serverSide {
        int 				portnumber;
	ServerSocket 			server_socket;
	
	// reference variable to store IO streams
	DataInputStream 		dis;
	DataOutputStream 		dos;
	
	/**
	 * Constructor to run the server..
	 * @param port
	 */
	public serverSide(int port) {
		
        this.portnumber = port;

        try {
            this.server_socket = new ServerSocket(port);

            System.out.println("## server is listening to port: " + port + " waiting for client connections..");

            while (true) //infinite while loop
            { 
                Socket s = null; //Declare a variable s of type socket and set it to null

                try
                { 
                    // socket object to receive incoming client requests 
                    s = this.server_socket.accept(); 

                    System.out.println("A new client is connected : " + s); 

                    // obtaining input and out streams 
                    dis = new DataInputStream(s.getInputStream()); 
                    dos = new DataOutputStream(s.getOutputStream()); 

                    System.out.println("Assigning new thread for this client"); 

                    // create a new thread to handle the connected client 
                    Thread t = new ClientHandler(s, dis, dos); //declare a new thread t of type ClientHandler

                    // Invoking the start() method 
                    t.start(); //Start the client handler

                } // End try part
                catch (Exception e){ 
                        s.close(); 
                        e.printStackTrace(); 
                    } // End catch
            } // End while

        } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        } 
		
		
	}
	
	/**
	 * Main program...
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException 
	{ 
		
		// Get port from command line argument
		// int port = Integer.parseInt(args[0]);	
		
		// server is listening on port 5056
		int port = 5056;	
		serverSide server = new serverSide(port);		
		
	} // End Main

    serverSide() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
} // End Server Class


/**
 * A client handler class.
 * an instance of this class is created when a new client client is connected..
 * Each instance acts as a separate thread.
 * @author thanuja
 *
 */
class ClientHandler extends Thread 
{ 
	
	final DataInputStream 			dis; //Declare dis as DataInputStream
	final DataOutputStream 			dos; //Declare dos as DataOutputStream
	
	final Socket 					s; //Declare s as a Socket

	/**
	 * Constructor.
	 * 
	 * @param s
	 * @param dis
	 * @param dos
	 */
	public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) 
	{ 
            this.s = s; 
            this.dis = dis; 
            this.dos = dos; 
	} 

	@Override
	/**
	 * run method, called when a client handler thread is starting..
	 * responds to client requests
	 */
	public void run() 
	{ 
        //Declare string to receive information
        String received; 

        //Infinite loop setup
        while (true) 
        { 
            try { 

                // Ask user what he wants 
                dos.writeUTF("Welcome to the server connection. Type something..."); 

                // receive the answer from client 
                received = dis.readUTF(); 

                if(received.equals("Exit")) 
                { 
                        System.out.println("Client " + this.s + " sends exit..."); 
                        System.out.println("Closing this connection."); 
                        this.s.close(); 
                        System.out.println("Connection closed"); 
                        break; 
                } 

                // server response is simply the upper case of client request.
                dos.writeUTF("Server response: " + received.toUpperCase()); 				

            } catch (IOException e) { 
                    e.printStackTrace(); 
            } 
        } 

        try
        { 
                // closing resources 
                this.dis.close(); 
                this.dos.close(); 

        }catch(IOException e){ 
                e.printStackTrace(); 
        } 
} 
} 

