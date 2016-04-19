package Logic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class EV3Client {

	DataInputStream dis;
	DataOutputStream dos;
	boolean done;
	int socketPort = 5000;

	public EV3Client(String serverIpAddress) {
		try {
			System.out.println("Connecting to EV3...");
			Socket socket = new Socket(serverIpAddress, socketPort);

			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());

			done = false;

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public void sendMessage(String message) {
		try {
			dos.writeUTF(message);
			dos.flush();

			try {
				if (dis.available() > 0) {
					String ev3Message = dis.readUTF();
					System.out.println("ev3 response: " + ev3Message);
				}
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}

			String ev3Message = dis.readUTF();
			System.out.println("EV3 says: " + ev3Message);

			if (message.equalsIgnoreCase("quit")) {
				done = true;
			}
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}