package BE;

import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Pose;
import lejos.utility.Delay;

public class MyRobot {

	private Pose pose;

	private DifferentialPilot pilot;
	private final double wheelDiameterInches = 1.70;
	private final int roboSpeed = 3;

	private EV3LargeRegulatedMotor motorLeft, motorRight;

	public MyRobot(Brick brick) {
		
		
		this.motorLeft = new EV3LargeRegulatedMotor(brick.getPort("B"));
		this.motorRight = new EV3LargeRegulatedMotor(brick.getPort("C"));
		
		pilot = new DifferentialPilot(2.2F, 11.25F, motorLeft, motorRight);
		
		pilot.setLinearSpeed(roboSpeed);
		//pilot.setLinearAcceleration(8);	
	}

	public void goForward() {
		pilot.forward();
	}
	public void goBackward() {
		pilot.backward();
	}

	/*public void arcTurn(String direction, int turnWeight) throws Exception {

		if (direction == "left") {
			motorLeft.setSpeed(roboSpeed);
			motorRight.setSpeed(roboSpeed + turnWeight);
		} else if (direction == "right") {
			motorRight.setSpeed(roboSpeed);
			motorLeft.setSpeed(roboSpeed + turnWeight);
		} else {
			throw new Exception(
					"turnMe exception: probably the parameters that are invalid!!!");
		}
	}*/

	public boolean isForwarding() {
		if (motorLeft.getSpeed() == motorRight.getSpeed()) {
			return true;
		} else {
			return false;
		}
	}

	

	

	

	

	public void close() {
		motorLeft.stop();
		motorRight.stop();
		motorLeft.close();
		motorRight.close();
	}

	public DifferentialPilot getPilot() {
		return pilot;
	}


}