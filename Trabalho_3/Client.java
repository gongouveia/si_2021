package trabalho3.Trabalho_3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.Stack;



public class Client { 																// CLIENTE

	public static void main(String args[]) throws IOException {
		
		
		
		boolean acabarJogo = false;

		
		Stack<Integer> StackOne = new Stack<Integer>();      //torre direita		
		Stack<Integer> StackTwo = new Stack<Integer>();      //stack intermedia
		Stack<Integer> StackThree = new Stack<Integer>();      //stack esquerda	
		
		Scanner sc = new Scanner(System.in);
		
		
		
		clientUtil.welcome();
		int disk = 3;
		int initPin = 1;
		int endPin = 1;
		int counter = 1;
		
		boolean closeClient = true;
		boolean connect = true;
		
		while (closeClient) {
			
			connect = true;
			
			
			while(connect) {
				System.out.println("Connect to server 'Torre de Hanoy' [Y/N]: ");
				String connectionOnline = sc.nextLine();
				
				if(connectionOnline.toUpperCase().equals("Y")) {					
					acabarJogo = true;
					connect = false;
					
				} else if(connectionOnline.toUpperCase().equals("N")) {
					closeClient =  false;
					connect = false;
				} else {
					System.out.println("Insert a Y or N.");
				}
			}
		
			
			
			if (closeClient) {
				Socket socket = new Socket("localhost", 1234);
				InputStream in = socket.getInputStream();
				DataInputStream dataIn=  new DataInputStream(in);
				OutputStream out = socket.getOutputStream();
				DataOutputStream dataOut = new DataOutputStream(out);
				
				while(acabarJogo) {
					
				
				
					// Ler a resposta do servidor
					String serverResponse = dataIn.readUTF();
	
								
	
					
					switch(serverResponse){
					
					case"LOGIN":
						System.out.println("insert username: ");
						dataOut.writeUTF(sc.nextLine());
						System.out.println("insert password: ");
						dataOut.writeUTF(sc.nextLine());
						
						break;
	
					case "VALID_CREDENTIAL":
						System.out.println("Valid Login!");
						break;
								
					case "INVALID_CREDENTIAL":
						System.out.println("Invalid Login - Try again [Y/N]: ");
						String option = sc.nextLine();
						dataOut.writeUTF(option);
						break;
						
					case "PROTOCOL_ERROR":
		
						System.out.println("Connection timed out");
						acabarJogo=false;
						break;

					
					case "DISK_NUMBER":
						System.out.println("* Insert number of disks between 3 and 10 to continue: ");
						String diskNumber = sc.nextLine();
						dataOut.writeUTF(diskNumber);
						
						switch(dataIn.readUTF()) {
							case "DISKS_ACCEPTED":
								System.out.println("You picked " + diskNumber + " disks");
								disk  = Integer.parseInt(diskNumber);
								break;
								
							case "DISKS_DECLINED":
								System.out.println("Please select a number between 3 and 10.");
								break;
								
							case "DISKS_DECLINED_ERROR":
								System.out.println("Please insert a number.");
								break;
						}
						break;
						
					case "PIN_VERIFIER":
		
						System.out.println("* Insert initial pin: ");
						String initialpin = sc.nextLine();
						dataOut.writeUTF(initialpin);
						
						System.out.println("* Insert final pin: ");
						String finalpin = sc.nextLine();
						dataOut.writeUTF(finalpin);
						
						switch(dataIn.readUTF()) {
						case "PIN_SELECTED":
				
							initPin = Integer.parseInt(initialpin);
							endPin = Integer.parseInt(finalpin);
							break;
							
						case "PIN_ERROR":
							System.out.println("Please select a number between 1 and 3.");
							break;
							
						case "PIN_INVALID_NUMBER":
							System.out.println("Please insert a number.");
							break;
					}
						
						break;
					case "GAME_STARTED":
						clientUtil.welcome();
						
						break;
						
					case "PIN_FILLER":
						
						clientUtil.pinFiller(disk,initPin, StackOne, StackTwo, StackThree);
						
						break;
						
					case "PLAY":
						clientUtil.playOptions();
						String play = sc.nextLine();
						dataOut.writeUTF(play);
						break;
						
					case "MOVE_DISK":
						String move = dataIn.readUTF();
						switch(move) {
							case"1":
								clientUtil.diskXange(StackOne, StackTwo);
								break;
							case"2":
								clientUtil.diskXange(StackOne, StackThree);
								break;
							case"3":
								clientUtil.diskXange(StackTwo, StackOne);
								break;
							case"4":
								clientUtil.diskXange(StackTwo, StackThree);
								break;
							case"5":
								clientUtil.diskXange(StackThree, StackOne);
								break;
							case"6":
								clientUtil.diskXange(StackThree, StackTwo);
								break;
							case "MOVE_ERROR":
								System.out.println("You moved is not allowed. Play again.");
								break;
							case "Y":
								System.out.println("You exited from the current game.");
								break;
							default:
								System.out.println("Play again. Select a number between 1 and 6.");
								break;
						}
						
						break;
						
					case "COUNTER_PRINT":
						System.out.println("Round: "  + counter);
						break;
						
					case "COUNTER_ADD":
						counter++;
						break;
					case "COUNTER_RESET":
						counter = 1;
						break;
					case "NO_TRY":
		
						System.out.println("Server doesn't want to connect");
						//invalid = false;
						break;
		
					case "DRAW":
						System.out.println("Final pin: " + endPin);
						clientUtil.draw(disk, StackOne, StackTwo, StackThree);    
						break;
		
						
					case "PIN_CLEAR":
						clientUtil.pinClear(StackOne, StackTwo, StackThree);;
						break;
						
					case "WIN":
						System.out.println("------------\n You won!!!! \n------------\n");
						break;
					case "MENU":
										
						clientUtil.displayMenu();
						
						String menuoption= sc.nextLine().toUpperCase();
						
						switch (menuoption){
						case "1":
							dataOut.writeUTF("OPTION1");
							break;
							
						case "2":
							dataOut.writeUTF("OPTION2");
							clientUtil.showResults(null);
							System.out.println("Press any key");
							
							break;
							
						case"Q":
							dataOut.writeUTF("OPTIONQ");
							dataIn.close();
							dataOut.close();
							in.close();
							out.close();
							socket.close();
							//para impedir voltar ao inicio do primeiro loop
							acabarJogo = false;
							break;
							
						
						}
					
						break;
						
					case"CLOSE_CLIENT":
						dataIn.close();
						dataOut.close();
						in.close();
						out.close();
						socket.close();
						closeClient = false;
						acabarJogo = false;
						break;
					default:
						break;
		
					}
				
				}
				
			}
			
		}
		sc.close();
	}

	
}
	
	
	






