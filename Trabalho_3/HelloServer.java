package trabalho3.Trabalho_3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * A simple greeting server. More information about sockets at:
 * http://download.oracle.com/javase/tutorial/networking/sockets/index.html
 * and in the book "Head First Java" - Chapter 15.
 */
public class HelloServer
{
	// 'throws IOException' enables us to write the code without try/catch blocks
	// but it also keeps us from handling possible IO errors 
	// (when for instance there is a problem when connecting with the other party)
	public static void main(String args[]) throws IOException
	{
		// Register service on port 1234
		ServerSocket s = new ServerSocket(1234);
		Scanner scan = new Scanner(System.in);
		String[] user = new String[2];
		
		Stack<String[]> userData = new Stack<String[]>();
		
		while (true)
		{
			// Wait and accept a connection
			Socket s1 = s.accept();

			// Build DataStreams (input and output) to send and receive messages to/from the client
			OutputStream out = s1.getOutputStream();
			DataOutputStream dataOut = new DataOutputStream(out);

			InputStream in = s1.getInputStream();
			DataInputStream dataIn = new DataInputStream(in);

			// use the DataInputStream to read a String sent by the client
			String request = dataIn.readUTF();
			System.out.println("Client requested:" + request);

			// Send a reply to the client!
			dataOut.writeUTF("Goodbye!");

			// you can flush the stream to force writing
			dataOut.flush();

			System.out.println("I just answered a client!");

			// Cleanup operations, close the streams, the connection, but don't close the ServerSocket
			dataOut.close();
			dataIn.close();
			s1.close();
		}
	}
	
	public boolean logIn(Scanner scan, String[] user, Stack<String[]> userData) {

		
		System.out.println("Enter your username: ");
		String userName = scan.nextLine();
		
		String password = userData.get(0)[1];
		int userNumber = 0;
		boolean logCondition = false;
		
		boolean newName = true;
		
		for(int i = 0; i < userData.size(); i++) {
			if(userData.get(i)[0].equals(userName)) {
				newName = false;
				userNumber = i;
				password = userData.get(i)[1];
				break;
			}
		}
		
		if(newName) {
			System.out.println("You are a new user!\n Please, write a password");
			password = scan.nextLine();
			user[0] = userName;
			user[1] = password;
			userData.push(user);
			logCondition = true;
			
		} else {
			
			
			while(!userData.get(userNumber)[1].equals(password)){
				System.out.println("Please insert your password");
				password = scan.nextLine();
				
			}
			System.out.println("You are logged in");
			logCondition = true;
		}
		
		return logCondition;
	}
	
	


}