package Logic;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import lejos.hardware.lcd.LCD;


public class EV3Server {
	
	EV3Client client;
	public EV3Server(String serverIpAddress, int serverSocketNumber){
	try
	{
		client = new EV3Client(serverIpAddress);
	    ServerSocket ss = new ServerSocket(serverSocketNumber);
	    
	    LCD.clear();
	    LCD.drawString("Waiting for client connection...", 0, 0);
	   
	    Socket s = ss.accept();
	    
	    LCD.clear();
	    LCD.drawString("Client connected", 0, 0);
	    
	    DataInputStream dis = new DataInputStream(s.getInputStream());
	    DataOutputStream dos = new DataOutputStream(s.getOutputStream());
	    
	    boolean done = false;
	    while (! done)
	    {
		String message = dis.readUTF();
		LCD.clear();
		LCD.drawString("Client: " + message, 0, 2);
		client.sendMessage(message);
		
		dos.writeUTF(message.toUpperCase());
		dos.flush();
		if (message.equalsIgnoreCase("quit"))
		{
		    done = true;
		    
		}
	    }
	    LCD.clear();
	    LCD.drawString("EV3 terminating", 0, 1);
	} 
	catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
    }

}
