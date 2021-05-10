package trabalho3.Trabalho_3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.Stack;
import java.util.HashMap;



public class Client { 																// CLIENTE

	public static void main(String args[]) throws IOException {
		
		
		
		boolean acabarJogo = false;

		
		Stack<Integer> StackOne = new Stack<Integer>();      //torre direita		
		Stack<Integer> StackTwo = new Stack<Integer>();      //stack intermedia
		Stack<Integer> StackThree = new Stack<Integer>();      //stack esquerda	
		int solve = 0;
		
		/*<integer = disco, representa o disco
		//, interger[] = 
		 * - numero de vezes que o disco foi escolhido
		 * - rondas para um disco
		 * - média
		 * - disco, representa o disco
		*/
		
		
		
		
		HashMap<Integer, int[]> dataScores = new HashMap<Integer, int[]>();
		//{number of times played,total score,average,number of disks} 
		
		HashMap<String, HashMap<Integer, int[]>> dataBase = new HashMap<String, HashMap<Integer, int[]>>();
		
		
		
		
		
		
		Scanner sc = new Scanner(System.in);
		
		
		
		clientUtil.welcome();
		int disk = 3;
		int initPin = 1;
		int endPin = 1;
		int counter = 1;
		
		String name = "bro";
		
		boolean closeClient = true;
		boolean connect = true;
		boolean reconnect = false;
		while (closeClient) {
			
			
			
			
			while(connect) {
				
				if(reconnect) {
					acabarJogo = true;
					closeClient = true;
					connect = false;
					reconnect = false;
					
				} else {
					System.out.println("Connect to server 'Torre de Hanoy' [Y/N]: ");
					String connectionOnline = sc.nextLine();
					
					if(connectionOnline.toUpperCase().equals("Y")) {					
						acabarJogo = true;
						closeClient = true;
						connect = false;
						
					} else if(connectionOnline.toUpperCase().equals("N")) {
						closeClient =  false;
						connect = false;
					} else {
						System.out.println("Insert a Y or N.");
				}
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
						name = sc.nextLine();
						dataOut.writeUTF(name);
						System.out.println("insert password: ");
						dataOut.writeUTF(sc.nextLine());
						
						break;
	
					case "VALID_CREDENTIAL":
						
						System.out.println("Valid Login!");
									
						
						if(dataBase.get(name) == null) {
							
							for (int i=3; i<11;i++){
								dataScores.put(i, new int[] {0, 0, i});
								
							}
							
							dataBase.put(name, dataScores);
						} else {							
							dataScores = dataBase.get(name);
						}
						
						
						
						break;
								
					case "INVALID_CREDENTIAL":
						System.out.println("Invalid Login");
						dataOut.writeUTF("OPTIONQ");
						dataIn.close();
						dataOut.close();
						in.close();
						out.close();
						socket.close();
						//para impedir voltar ao inicio do primeiro loop
						acabarJogo = false;
						
						System.out.println("Reconnect? [Y/N]");
						
						
						while(true) {
							String exitLogin = sc.nextLine();
							
							if(exitLogin.toUpperCase().equals("Y")) {
								
								reconnect = true;
								connect = true;
								break;
							} else if(exitLogin.toUpperCase().equals("N")) {
								
								closeClient =  false;
								connect = false;
								break;
								
							} else {
								System.out.println("Inser a Y or N");
								
							}
							
						}
						
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
								
								int[] replace2 = {dataScores.get(disk)[0]+1, dataScores.get(disk)[1],disk};
								dataScores.put(disk, replace2);
								
								solve = (int)Math.pow(2,disk)-1;
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
		
						System.out.println("* Insert initial pin: \n 1-Pin A \n 2-Pin B \n 3-Pin C ");
						String initialpin = sc.nextLine();
						dataOut.writeUTF(initialpin);
						
						System.out.println("* Insert initial pin: \n 1-Pin A \n 2-Pin B \n 3-Pin C ");
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
						int counterAdd = counter + dataScores.get(disk)[1];
						
						int[] replace2 = {dataScores.get(disk)[0],counterAdd,disk};
						dataScores.put(disk, replace2);
						
						System.out.println("------------\n You won!!!! \n------------\n");
						System.out.println("You finished the game with "+ counter + "steps!");
						System.out.println("The smallest possible number of steps is " + solve + ".");
						break;
						
					case "SCORE_CALC":
						
						
						
						int[] replace = {dataScores.get(disk)[0],dataScores.get(disk)[1], disk};
						dataScores.put(disk, replace);
						dataBase.put(name, dataScores);
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

								
						
								clientUtil.showResults(dataScores);
								
								
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
								connect = true;
								break;
							default:
								dataOut.writeUTF("OPTIOND");
								System.out.println("Select an accepted input. Select 1, 2 or Q.");
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
	
	
	






