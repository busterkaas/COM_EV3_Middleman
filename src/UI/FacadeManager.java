package UI;

import java.util.ArrayList;

import BE.MyRobot;
import IHelpers.ICommunicationObserver;

public class FacadeManager {

	MyRobot robot;
	ArrayList<ICommunicationObserver> observers;
	

	public FacadeManager(MyRobot robot, ICommunicationObserver obs) {
		this.robot = robot;
		observers = new ArrayList<>();
		addAsObserver(obs);
	}

	public void navigate(String request) throws Exception {

		switch (request) {
		case "forward":
			robot.goForward();
			break;
		case "backward":
			robot.goBackward();
			break;
		case "left":
			robot.getPilot().rotate(45);
			break;
		case "right":
			robot.getPilot().rotate(-45);
			break;
		case "stop":
			robot.getPilot().quickStop();
			break;
		case "quit":
			robot.close();
			System.exit(0);
			break;
		case "slavego":
			sendMsgToObservers("follow");
			break;
		case "slavestop":
			sendMsgToObservers("stop");
			break;
		default:
			break;
		}

	}
	private void sendMsgToObservers(String message) {
		for (ICommunicationObserver obs : observers) {
			obs.ev3Message(message);
		}
	}

	public void addAsObserver(ICommunicationObserver observer) {
		observers.add(observer);
	}

	public void removeAsObserver(ICommunicationObserver observer) {
		observers.remove(this);
	}


}
