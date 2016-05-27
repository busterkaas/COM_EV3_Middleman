package UI;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import BE.MyRobot;
import Helpers.PositionHelper;
import IHelpers.ICommunicationObserver;

import lejos.hardware.lcd.LCD;

public class EV3Client implements ICommunicationObserver {

	Socket s;
	DataInputStream dis;
	DataOutputStream dos;

	public EV3Client(String serverIpAddress, MyRobot robot) {
		try {
			s = new Socket(serverIpAddress, 5000);
			dis = new DataInputStream(s.getInputStream());
			dos = new DataOutputStream(s.getOutputStream());
			
			PositionHelper posThread = new PositionHelper(robot, this);
			posThread.start();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void sendMessage(String message) {
		try {
			dos.writeUTF(message);
			dos.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void ev3Message(String stringPosition) {
		sendMessage(stringPosition);
	}
	
	public void close(){
		
		try {
			s.close();
			dis.close();
			dos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
