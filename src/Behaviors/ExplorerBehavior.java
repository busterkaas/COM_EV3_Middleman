package Behaviors;

import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class ExplorerBehavior implements Behavior {

	private DifferentialPilot pilot;
	boolean isSupressed = false;

	public ExplorerBehavior(DifferentialPilot pilot) {
		this.pilot = pilot;
	}

	public boolean takeControl() {
		return true;
	}

	public void action() {
		System.out.println("Exploring...");
		isSupressed = false;
		pilot.forward();
		while (!isSupressed) {
			Thread.yield();
		}
		pilot.stop();
	}

	public void suppress() {
		isSupressed = true;
	}

}
