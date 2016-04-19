package Logic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import lejos.hardware.lcd.LCD;

public class EV3Server {

	EV3Client client;

	public EV3Server(String serverIpAddress, int serverSocketPort) {
		try {
			client = new EV3Client(serverIpAddress);
			ServerSocket serverSocket = new ServerSocket(serverSocketPort);

			LCD.clear();
			LCD.drawString("Waiting for client connection...", 0, 0);

			Socket socket = serverSocket.accept();

			LCD.clear();
			LCD.drawString("Client connected", 0, 0);

			DataInputStream dis = new DataInputStream(socket.getInputStream());
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

			boolean done = false;
			while (!done) {
				String message = dis.readUTF();
				LCD.clear();
				LCD.drawString("Client: " + message, 0, 2);
				client.sendMessage(message);

				dos.writeUTF(message.toUpperCase());
				dos.flush();
				if (message.equalsIgnoreCase("quit")) {
					done = true;
				}
			}
			LCD.clear();
			LCD.drawString("EV3 terminating", 0, 1);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}