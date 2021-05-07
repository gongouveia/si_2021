package trabalho3.Trabalho_3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class serverUtil {


	
	
	

	public static int diskXange (String move, DataOutputStream dataOut, Stack<Integer> a ,Stack<Integer> b, int movement) throws IOException {

		
		int var;
		// se o ultimo disco da torre para que vai o disco seclecionado for maior então não é permitido
		if (a.lastElement() >= b.lastElement() )   
		{
			dataOut.writeUTF("MOVE_ERROR");
			System.out.println("Forbidden move.");
		}

		else {  //se possivel executar passar os discos da torre, o ultimo disco é extraido da stack1 e colocado na stack 2
			var = a.pop(); b.push(var);
			movement ++; //o counter so é incrementado, se existir movimento de discos
			dataOut.writeUTF(move);
			System.out.println("Accepted move.");
		}
		return movement; // o counter é igualado ao movement 
	
	
	}
	

	
	public static int[] newGame(int disk, DataInputStream dataIn, DataOutputStream dataOut, Stack<Integer> aux1,Stack<Integer> aux2,Stack<Integer> aux3)throws IOException {
		
		dataOut.writeUTF("PIN_CLEAR");
		dataOut.writeUTF("GAME_STARTED");
		disk = diskNumberPick(dataIn, dataOut, disk);
		int[] pinArray = intPinVerifier(dataIn, dataOut);
		
		dataOut.writeUTF("PIN_FILLER");
		pinFiller(disk, pinArray[0], aux1, aux2, aux3);
		
		int solve = (int)Math.pow(2,disk)-1; 
		
		return new int [] {pinArray[0], pinArray[1], disk, solve};
	}
	
	public static boolean menu(DataInputStream dataIn, DataOutputStream dataOut, boolean endGame)  throws IOException {
		
		dataOut.writeUTF("MENU");
		String displayaux = dataIn.readUTF();


		switch (displayaux) {

			case "OPTION1" :
				System.out.println("New game starting.");
				
				
				endGame = false;
				break;

			case "OPTION2":
				System.out.println("Stats showing.");
				endGame = false;
				break;

			case "OPTIONQ":
				System.out.println("Exit from game.");
				endGame = true;
				break;

					
		}
		return endGame;
	}
	public static void pinFiller(int disk,int initialpin,Stack<Integer> aux1,Stack<Integer> aux2,Stack<Integer> aux3) {
	//passar o void para Stack<Integer>
	//Stack<Integer> aux = aux1;  //neste momento todas as stacks são iguais 
		
		aux1.push(1000);
		aux2.push(1000);
		aux3.push(1000);
		for (int j = disk; j >= 1; j--) {
			
			switch (initialpin) {
		
			case 1 :
				
				aux1.push(j); 
				break;
		
			case 2:
				
				aux2.push(j); 
				break;
		
			case 3:
				
				aux3.push(j); 
				break;
			}
		
		}
	//return aux;		
	}
	public static void pinClear(Stack<Integer> aux1,Stack<Integer> aux2,Stack<Integer> aux3) {
		aux1.clear();
		aux2.clear();
		aux3.clear();
		
	}
	
	
	public static Boolean waitRoutine(DataInputStream dataIn, DataOutputStream dataOut) throws IOException{
		
		boolean waiting = true;	
		
		System.out.println("Waiting for client");
		String request = dataIn.readUTF(); // Usa o DataInputStream para ler a string enviada pelo cliente
		System.out.println("O cliente escolheu:" + request);
	
		if (request.equalsIgnoreCase("Y")) 
		{
			dataOut.writeUTF("PLAY");
			System.out.println("client to play");
			waiting = false;
		} 
		else if (request.equalsIgnoreCase("N"))
		{
			dataOut.writeUTF("NO_TRY");
			System.out.println("client doesn't want to play");
			waiting=true;
		} 
		else 
		{
			dataOut.writeUTF("INVALID_COMAND");
		}
	
		dataOut.flush();
		System.out.println("client answered");
		
		return waiting;
	}
	
	
	public static boolean credentialValidator(HashMap<String, String> credentials, String login, String pass) throws IOException {
		
		boolean incorrectcredentials = false;
		ServerSocket s = new ServerSocket(1234);
		Socket s1 = s.accept(); 									// Wait and accept a connection
		// Get a stream associated with the socket
		OutputStream out = s1.getOutputStream();
		DataOutputStream dataOut = new DataOutputStream(out);

		
		
		for (String i : credentials.keySet())
		{
			if ((login.equalsIgnoreCase(i) && pass.equalsIgnoreCase(credentials.get(i)))) 
			{
				// JOGAR
				dataOut.writeUTF("Incorrect Login");
				System.out.println("\nIncorrect Login");
				incorrectcredentials = false;
			}
		}	
		
		return incorrectcredentials;
		
		
	}

public static int[] intPinVerifier(DataInputStream dataIn, DataOutputStream dataOut) throws IOException {  //verifica se o ultmimo é igual ao inicial
		
		dataOut.writeUTF("PIN_VERIFIER");
		
		int pinmax=3;
		int pinmin=1;
		int initialpin=0;
		int finalpin = 0;
		int[] pinArray = new int[2];
		boolean pinCheck = false;

		while(!pinCheck ) {
			
			String pinIn = dataIn.readUTF();
			String pinOut = dataIn.readUTF();
			System.out.println("Initial pin:" +  pinIn + " Final pin:" + pinOut);
			try {
				initialpin = Integer.parseInt(pinIn);
				finalpin = Integer.parseInt(pinOut);

				if(initialpin >= pinmin && initialpin <= pinmax && finalpin >= pinmin && finalpin <= pinmax && finalpin != initialpin) {
					pinCheck = true;
					dataOut.writeUTF("PIN_SELECTED");
					pinArray[0] = initialpin;
					pinArray[1] = finalpin;

				} else {
					dataOut.writeUTF("PIN_ERROR");
					System.out.println("Invalid pin number.");
					dataOut.writeUTF("PIN_VERIFIER");
				}
				
			} catch(Exception e) {
				dataOut.writeUTF("PIN_INVALID_NUMBER");
				System.out.println("Invalid input number for pin.");
				dataOut.writeUTF("PIN_VERIFIER");
			}
			
		}


	return pinArray;
	
	}


	public static int diskNumberPick(DataInputStream dataIn, DataOutputStream dataOut, int disk) throws IOException {
		boolean diskCheck = false;
		int diskMin = 3; //número minimo de discos permitidos
		int diskMax = 10;//número máximo de discos permitidos
		
		while(!diskCheck ) {
			dataOut.writeUTF("DISK_NUMBER");
			
			String diskString = dataIn.readUTF();
			
			try {
				disk = Integer.parseInt(diskString);
				
				if(disk >= diskMin && disk <= diskMax) {
					diskCheck = true;
					dataOut.writeUTF("DISKS_ACCEPTED");
				} else {
					dataOut.writeUTF("DISKS_DECLINED");
					dataOut.writeUTF("DISK_NUMBER");
				}
				
				
			}catch(Exception e) {
				dataOut.writeUTF("DISKS_DECLINED_ERROR");
				dataOut.writeUTF("DISK_NUMBER");
			}
			
		}
		System.out.println("Number of disks: " + disk);
		return disk;
	}
	
    
	// Closers de conexão

	
    
	
}
