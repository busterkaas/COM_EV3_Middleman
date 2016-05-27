package Helpers;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import IHelpers.ICommunicationObserver;



public class PCClientInputHelper extends Thread {
	
	DataInputStream pcInput;
	List<ICommunicationObserver> observers;
	
	
	public PCClientInputHelper(DataInputStream dis,ICommunicationObserver obs){
		pcInput = dis;
		observers = new ArrayList<ICommunicationObserver>();
		addAsObserver(obs);
	}
	
	public void run(){
		while(true){
		
			try {
				String clientMessage = pcInput.readUTF();
				sendMsgToObservers(clientMessage);
				//LCD.clear();
				//LCD.drawString("Client: " + clientMessage, 0, 2);
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
	}
	
	private void sendMsgToObservers(String clientMessage){
		for(ICommunicationObserver obs : observers){
			obs.ev3Message(clientMessage);
		}
	}
	
	
	public void addAsObserver(ICommunicationObserver observer){
		observers.add(observer);
	}
	public void removeAsObserver(ICommunicationObserver observer){
		observers.remove(this);
	}

}
