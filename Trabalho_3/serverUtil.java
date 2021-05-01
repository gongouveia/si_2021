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


	
	
	

	public static int diskXange (Stack<Integer> a ,Stack<Integer> b, int movement) {

		
		int var;
		// se o ultimo disco da torre para que vai o disco seclecionado for maior então não é permitido
		if (a.lastElement() >= b.lastElement() )   
		{
			//errorDraw();   //chama a função a aviar que este paço náo é possivel
			
		}

		else {  //se possivel executar passar os discos da torre, o ultimo disco é extraido da stack1 e colocado na stack 2
			var = a.pop(); b.push(var);
			movement ++; //o counter so é incrementado, se existir movimento de discos
			}
		return movement; // o counter é igualado ao movement 
	
	
	}
	

	

	

	public void pinFiller(int disk,int initialpin,Stack<Integer> aux1,Stack<Integer> aux2,Stack<Integer> aux3) {
	//passar o void para Stack<Integer>
	//Stack<Integer> aux = aux1;  //neste momento todas as stacks são iguais 
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
	
	public static Boolean waitRoutine() throws IOException{
		
		ServerSocket s = new ServerSocket(1234);
		Socket s1 = s.accept(); 									// Wait and accept a connection
		// Get a stream associated with the socket
		OutputStream out = s1.getOutputStream();
		DataOutputStream dataOut = new DataOutputStream(out);
		InputStream in = s1.getInputStream(); 						// Fornece um input stream para este socket
		DataInputStream dataIn = new DataInputStream(in);	
		
		
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
		s.close();
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

    
	public static int intPinVerifier(int verifier,String mod) throws IOException {  //verifica se o ultmimo é igual ao inicial
		
		
		int initialpin=0;
		
		int pinmax=3;
		int pinmin=1;
		
		
		
		switch(mod) {
		
		case "initial":
			do {
				
			System.out.println("digite o piroco inicial 1-A 2-B 3-C");
			if(sc.hasNextInt()){      //seleciona apenas inputs inteiros
				initialpin = Integer.parseInt(dataIn.readUTF());;
				//initialpin = sc.nextInt();
				//sc.nextLine();     // tratar do caso especial do /n quando se insere o inteiro
				} 
			else{initialpin = 0;   //valor para dar erro e voltar a pedir um input
				}
	
			}while (initialpin  < pinmin | initialpin  > pinmax );
			dataOut.writeUTF("INITIAL_PIN");
			dataOut.writeUTF("initial pin selected :"+initialpin);
			
			break;
		case "final":
			do {
				System.out.println("digite o piroco final 1-A 2-B 3-C");
				if(sc.hasNextInt()){      //seleciona apenas inputs inteiros
					initialpin = Integer.parseInt(dataIn.readUTF());
					sc.nextLine();     // tratar do caso especial do /n quando se insere o inteiro
				}					
				else{
					initialpin = 0;   //valor para dar erro e voltar a pedir um input
					}
				
				if (initialpin ==verifier) {
					initialpin =0;
				}
				}while (initialpin  < pinmin | initialpin  > pinmax );
			dataOut.writeUTF("FINAL_PIN");
			dataOut.writeUTF("initial pin selected :"+initialpin);
			break;
	}
		
		
	//requestOUT =dataIn.writeUTF();
	return initialpin;
	
	}

	public static int diskNumberPick(DataInputStream dataIn, DataOutputStream dataOut, int disk) throws IOException {
		boolean diskCheck = false;
		int diskMin = 3; //número minimo de discos permitidos
		int diskMax = 10;//número máximo de discos permitidos
		
		while(!diskCheck ) {
			dataOut.writeUTF("DISK_NUMBER");
			dataOut.writeUTF("* Insert number of disks between 3 and 10 to continue: ");
			String diskString = dataIn.readUTF();
			
			try {
				disk = Integer.parseInt(diskString);
				if(disk >= diskMin && disk <= diskMax) {
					diskCheck = true;
					dataOut.writeUTF("You picked " + disk + " disks");
				} else {
					dataOut.writeUTF("Please intsert a number between 3 and 10: ");
				}
				
				
			}catch(Exception e) {
				dataOut.writeUTF("Please intsert a number: ");
			}
			
		}
		
		return disk;
	}
	
    
	// Closers de conexão

	
    
	
}
