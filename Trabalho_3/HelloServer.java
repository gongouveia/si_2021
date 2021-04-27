package trabalho3.Trabalho_3;

import java.util.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
public class HelloServer
{
	public static void main(String args[]) throws IOException 
	{
		// Register service on port 1234
		ServerSocket s = new ServerSocket(1234);

		
		int counter = 0; // regista o numero de passos até resolver o puzzle

		HashMap<Integer, Integer[]> dataScores = new HashMap<Integer, Integer[]>();

		for (int i=3; i<11;i++){
		dataScores.put(i, new Integer[] {0, 0, i});
		}
		
		
		
		
		
		
		
		
		Scanner sc = new Scanner(System.in);
		
		//Recepção ao jogador
		System.out.println("*".repeat(25));
		System.out.println("* Welcome to Hanoi Tower *");
		System.out.println("*".repeat(25));
		
		/*
		* Stacks vazios são arrays que permitem alterações aos seus elementos
		* decidimos realizar este exercicio por Stacks pois para algoritmos 
		* recursivos é uma otima ferramenta.
		*/
		
		// criação de uma stack vazia para cada torre
		Stack<Integer> aux1 = new Stack<Integer>();      //torre direita		
		Stack<Integer> aux2 = new Stack<Integer>();      //stack intermedia
		Stack<Integer> aux3 = new Stack<Integer>();      //stack esquerda

		int disk = 0;    //número de discos usados durante o jogo 
		int diskMin = 3; //número minimo de discos permitidos
		int diskMax = 10;//número máximo de discos permitidos
		//int counter = 0; // regista o numero de passos até resolver o puzzle
		
		int solve;       //regista o numero de tentaivas minimo para resolver o jogo
		int size1 = 0;   //inicializa o tamanho de cada um dos stacks
		int size2 = 0;
		int size3 = 0;
		
		String option; // Input de jogadas ao longo do program
		
		int initialTowerSize =0;
		int finalTowerSize =0;
		
		
		
		
		
		
		
		
		

		while (true)
		{
			Socket s1 = s.accept(); 									// Wait and accept a connection
			// Get a stream associated with the socket
			OutputStream out = s1.getOutputStream();
			DataOutputStream dataOut = new DataOutputStream(out);
			InputStream in = s1.getInputStream(); 						// Fornece um input stream para este socket
			DataInputStream dataIn = new DataInputStream(in);

			HashMap<String, String> credentials = new HashMap<String, String>();
			boolean incorrectcredentials = true;
			boolean waiting = true;


			

			//base de dados
			credentials.put("Paiva","1234");
			credentials.put("Relvas", "1234");
			//base de dados password encriptada

			//credentials.put("Paiva","03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4");  
			//credentials.put("Relvas","03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4");



			while(waiting) 
			{
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
					dataOut.writeUTF("PROTOCOL_ERROR");
					System.out.println("client doesn't want to play");
					waiting=false;
				} 
				else 
				{
					dataOut.writeUTF("VALID_COMAND");

				}

				dataOut.flush();
				System.out.println("client answered");

			}




			String login = dataIn.readUTF();
			String pass = dataIn.readUTF();



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


			while (incorrectcredentials = true) 
			{

				dataOut.writeUTF("INCORRECT ");
				System.out.println("\nIncorrect Login");
				String resposta = dataIn.readUTF();
				if (resposta.equalsIgnoreCase("Y")) 
				{
					dataOut.writeUTF("TRY_AGAIN");
					System.out.println("Try again?!");
					login = dataIn.readUTF();                                                 //
					pass = dataIn.readUTF();												  //
					for (String i : credentials.keySet()) {
						if ((login.equalsIgnoreCase(i) && pass.equalsIgnoreCase(credentials.get(i))))
						{
							// JOGAR
							dataOut.writeUTF("VALID_CREDENTIAL");
							System.out.println("Valid credentials");
							incorrectcredentials = false;
						}
						else
						{
							dataOut.writeUTF("INVALID_CREDENTIAL");
							System.out.println("invalid credentials");
						}
					}	 
				} 
				else if (resposta.equalsIgnoreCase("n")) 
				{
					dataOut.writeUTF("NO_TRY");
					System.out.println("client doens´t want to play");
					incorrectcredentials = false;
				}



			}
			
			
			
			int initialpin = intPinVerifier(0,"initial");
			int finalpin = intPinVerifier(initialpin,"final");
			
			
			
			
			
			
		}
		

	}
	
	
	public static int intPinVerifier(int verifier,String mod) {  //verifica se o ultmimo é igual ao inicial
		
		Scanner sc = new Scanner(System.in);
		int initialpin=0;
		
		int pinmax=3;
		int pinmin=1;
		String requestIN;
		String requestOUT;
		
		
		switch(mod) {
		
		case "initial":
			do {
				
			System.out.println("digite o piroco inicial 1-A 2-B 3-C");
			if(sc.hasNextInt()){      //seleciona apenas inputs inteiros
				initialpin = sc.nextInt();
				sc.nextLine();     // tratar do caso especial do /n quando se insere o inteiro
				} 
			else{initialpin = 0;   //valor para dar erro e voltar a pedir um input
				}
	
			}while (initialpin  < pinmin | initialpin  > pinmax );
			break;
		case "final":
			do {
				System.out.println("digite o piroco final 1-A 2-B 3-C");
				if(sc.hasNextInt()){      //seleciona apenas inputs inteiros
					initialpin = sc.nextInt();
					sc.nextLine();     // tratar do caso especial do /n quando se insere o inteiro
				}					
				else{
					initialpin = 0;   //valor para dar erro e voltar a pedir um input
					}
				
				if (initialpin ==verifier) {
					initialpin =0;
				}
				}while (initialpin  < pinmin | initialpin  > pinmax );
			
			break;
	}
		
		
	//requestOUT =dataIn.writeUTF();
	return initialpin;
	
	}
	
	
	
	
	
	
	
	public void displayMenu(){

		System.out.println("---------Select and Option----------\n");
		System.out.println("1-Play");
		System.out.println("2-See stats");
		System.out.println("Q-Stop");

		}

	public void displayStat(int nmbOfRods,int totalScore,int numOfTimes){
		double average = 1.0*totalScore/numOfTimes;
		System.out.println("For "+nmbOfRods+" rods you have in average "+average+" moves and you played "+numOfTimes+" times");

	}
	// Closers de conexão

	public void showResults(HashMap<Integer, Integer[]> dataScores){
	for (int i=3; i<11;i++){
		if (dataScores.get(i)[1]>=0){
		displayStat(dataScores.get(i)[0],dataScores.get(i)[1],i);  //{number of times played,total score,number of disks} 
		}	
	}
}

	
	

}
