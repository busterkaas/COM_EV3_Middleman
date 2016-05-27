package Behaviors;

import UI.FacadeManager;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.robotics.subsumption.Behavior;

public class ExitBehavior implements Behavior {

	boolean isSupressed = false;
	FacadeManager fm;
	
	public ExitBehavior(FacadeManager fm){
		this.fm = fm;
	}

	public boolean takeControl() {
		return (!isSupressed && Button.ESCAPE.isDown());
	}

	public void action() {
		Sound.beep();
		try {
			fm.navigate("quit");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
		}
		System.exit(0);

	}

	public void suppress() {
		isSupressed = true;

	}

}
