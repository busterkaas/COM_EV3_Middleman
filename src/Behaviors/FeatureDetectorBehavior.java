package Behaviors;

import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.Sound;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.RangeFinderAdapter;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.objectdetection.Feature;
import lejos.robotics.objectdetection.FeatureDetector;
import lejos.robotics.objectdetection.FeatureListener;
import lejos.robotics.objectdetection.RangeFeatureDetector;
import lejos.robotics.subsumption.Behavior;

public class FeatureDetectorBehavior implements Behavior, FeatureListener {

	private boolean featureDetected = false;
	private boolean isSupressed = false;
	private DifferentialPilot pilot;
	float dist = (float) 0.35;
	
	RangeFinderAdapter adapter;
	RangeFeatureDetector rangeDetector;

	public FeatureDetectorBehavior(DifferentialPilot pilot, EV3UltrasonicSensor sensor) {
		this.pilot = pilot;
		
		adapter = new RangeFinderAdapter(sensor);
		rangeDetector = new RangeFeatureDetector(adapter, dist, 1);
		rangeDetector.addListener(this);
	}

	public boolean takeControl() {
		return (!isSupressed && featureDetected);
	}

	public void action() {
		System.out.println("Back up, thing detected!!!! ");
		Sound.beepSequence();

		pilot.stop();
		pilot.rotate(45);

		featureDetected = false;
		Thread.yield();
	}

	public void suppress() {
		isSupressed = true;
	}

	public void featureDetected(Feature feature, FeatureDetector detector) {
		featureDetected = true;
	}

}
