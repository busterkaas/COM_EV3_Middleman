package Behaviors;

import java.io.DataInputStream;

import Helpers.PCClientInputHelper;
import IHelpers.ICommunicationObserver;
import UI.FacadeManager;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

public class UserControlBehavior implements Behavior, ICommunicationObserver {
	
	private boolean isSupressed = false;
	private boolean userControl = true;
	private String clientMessage = "";
	private String lastMessage = "";
	
	
	FacadeManager navigator;
	DataInputStream dis;
	
	public UserControlBehavior(FacadeManager navigator, DataInputStream dis){
		this.navigator = navigator;
		this.dis = dis;

		PCClientInputHelper clientInputThread = new PCClientInputHelper(dis, this);
		clientInputThread.start();
	}

	@Override
	public boolean takeControl() {
		return (!isSupressed && userControl);
	}

	@Override
	public void action() {
		try {
			if(!clientMessage.equalsIgnoreCase(lastMessage) && !clientMessage.trim().isEmpty()){
			navigator.navigate(clientMessage);
			lastMessage = clientMessage;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void suppress() {
		isSupressed = true;
	}		
	

	@Override
	public void ev3Message(String clientMessage) {
		this.clientMessage = clientMessage;
		if(clientMessage.equalsIgnoreCase("usercontrol")){
			userControl = true;
		}
		else if(clientMessage.equalsIgnoreCase("explore")){
			userControl = false;
			Thread.yield();
		}
	}

	
	
}
