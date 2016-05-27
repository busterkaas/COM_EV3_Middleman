package Helpers;

import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

import BE.MyRobot;
import BE.SlavePosition;
import IHelpers.ICommunicationObserver;
import UI.EV3Client;

import lejos.robotics.localization.OdometryPoseProvider;

public class PositionHelper extends Thread {

	List<ICommunicationObserver> observers;
	DataOutputStream clientOutput;
	OdometryPoseProvider opp;
	EV3Client client;

	private float clientDistance = 15f;
	float thisPosX;
	float thisPosY;
	float heading;

	SlavePosition posRight;
	SlavePosition posLeft;
	SlavePosition posBack;

	SlavePosition selectedPosition;

	public PositionHelper(MyRobot robot, ICommunicationObserver obs) {
		opp = new OdometryPoseProvider(robot.getPilot());
		observers = new ArrayList<ICommunicationObserver>();
		addAsObserver(obs);
		

		setupPositioning();
		selectedPosition = posRight;
	}

	public void run() {
		while (true) {
			try {
				this.sleep(2000);
				
				thisPosX = opp.getPose().getX();
				thisPosY = opp.getPose().getY();
				heading = opp.getPose().getHeading();
				
				selectedPosition.setPosX(thisPosX);
				selectedPosition.setPosY(thisPosY);
				selectedPosition.setHeading(heading);

				sendMsgToObservers();

			} catch (Exception e) {
				System.out.println("PositionHelper ERROR: " + e.getMessage());
			}
		}
	}

	private void setupPositioning() {
		thisPosX = opp.getPose().getX();
		thisPosY = opp.getPose().getY();
		heading = opp.getPose().getHeading();
		

		posRight = new SlavePosition(thisPosX, thisPosY, heading, 0, clientDistance);
		posLeft = new SlavePosition(thisPosX, thisPosY, heading, 0, -clientDistance);
		posBack = new SlavePosition(thisPosX, thisPosY, heading, clientDistance, 0);
	}

	public void setSelectedPosition(String pos) {
		if (pos.equalsIgnoreCase("left")) {
			selectedPosition = posLeft;
		} else if (pos.equalsIgnoreCase("right")) {
			selectedPosition = posRight;
		} else if (pos.equalsIgnoreCase("back")) {
			selectedPosition = posBack;
		}

	}

	private void sendMsgToObservers() {
		for (ICommunicationObserver obs : observers) {
			obs.ev3Message(selectedPosition.stringPlacementPosition());
		}
	}

	public void addAsObserver(ICommunicationObserver observer) {
		observers.add(observer);
	}

	public void removeAsObserver(ICommunicationObserver observer) {
		observers.remove(this);
	}

}
