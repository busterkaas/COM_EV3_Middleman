package UI;

import Logic.EV3Server;

public class Main {

	static String ipAddress = "100.81.132.96";
	static int socketPort = 6000;

	public static void main(String[] args) {
		EV3Server server = new EV3Server(ipAddress, socketPort);
	}
}
