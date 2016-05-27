package UI;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import BE.MyRobot;
import Behaviors.ExitBehavior;
import Behaviors.ExplorerBehavior;
import Behaviors.FeatureDetectorBehavior;
import Behaviors.UserControlBehavior;

public class EV3Server {

	DataInputStream dis;
	DataOutputStream dos;
	Socket socket;

	Brick brick;
	MyRobot robot;
	EV3UltrasonicSensor ultrasonicSensor;

	FacadeManager myNavigator;
	ServerSocket serverSocket;
	Arbitrator arbitrator;

	EV3Client client;

	public EV3Server(int serverSocketNumber) {

		brick = BrickFinder.getDefault();
		robot = new MyRobot(brick);
		// Create my slave (Ev3 client)
		client = new EV3Client("100.81.141.167", robot);
		myNavigator = new FacadeManager(robot, client);
		ultrasonicSensor = new EV3UltrasonicSensor(brick.getPort("S3"));

		// Connect to PCClient
		try {
			serverSocket = new ServerSocket(serverSocketNumber);
			writeToLCD("Waiting for client connection...");

			socket = serverSocket.accept();
			writeToLCD("Client connected");

			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());

			// If connection, setup the arbitrator
			setupArbitrator();
			LCD.clear();
		} catch (Exception e) {
			responseToPCClient(e.getMessage());
		}

	}

	private void setupArbitrator() {
		// Instantiate behaviors and add them to a list of behaviors IN Correct
		// order!
		Behavior b1 = new ExplorerBehavior(robot.getPilot());
		Behavior b2 = new FeatureDetectorBehavior(robot.getPilot(),
				ultrasonicSensor);
		Behavior b3 = new UserControlBehavior(myNavigator, dis);
		Behavior b4 = new ExitBehavior(myNavigator);

		Behavior[] behaviorList = { b1, b2, b3, b4 };

		// Instantiate our arbitrator incliding the list of behaviors
		arbitrator = new Arbitrator(behaviorList);
		arbitrator.go();
	}

	private void writeToLCD(String message) {
		LCD.clear();
		LCD.drawString(message, 0, 0);
	}

	public void responseToPCClient(String message) {
		try {
			dos.writeUTF("EV3: " + message.toUpperCase());
			dos.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void close() {

		try {
			socket.close();
			serverSocket.close();
			dis.close();
			dos.close();
			ultrasonicSensor.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.exit(0);
	}

}
