package trabalho3.Trabalho_3;

import java.util.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
public class server
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


		/*
		 * Stacks vazios são arrays que permitem alterações aos seus elementos
		 * decidimos realizar este exercicio por Stacks pois para algoritmos 
		 * recursivos é uma otima ferramenta.*/
		// criação de uma stack vazia para cada torre
		Stack<Integer> aux1 = new Stack<Integer>();      //torre direita		
		Stack<Integer> aux2 = new Stack<Integer>();      //stack intermedia
		Stack<Integer> aux3 = new Stack<Integer>();      //stack esquerda		
		//base de cada torre. Com este desenhamos um base da stack pois nunca pode haver troca 
		//de valores da base entre as bases. Com este metodo conseguimos com que as Stacks nunca fiquem vazias

		aux1.push(1000);
		aux2.push(1000);
		aux3.push(1000);


		int disk = 0;    //número de discos usados durante o jogo 
		
		//int counter = 0; // regista o numero de passos até resolver o puzzle

		int solve;       //regista o numero de tentaivas minimo para resolver o jogo
		int size1 = 0;   //inicializa o tamanho de cada um dos stacks
		int size2 = 0;
		int size3 = 0;

		String option; // Input de jogadas ao longo do program
		String displayaux;
		int initialTowerSize =0;
		int finalTowerSize =0;
		
		
		HashMap<String, String> credentials = new HashMap<String, String>();
		boolean incorrectcredentials = true;
		boolean waiting = true;
		
		
		
		
		System.out.println("Waiting for client.");
		Socket s1 = s.accept(); 
		System.out.println("Server connected.");// Wait and accept a connection
		OutputStream out = s1.getOutputStream();
		DataOutputStream dataOut = new DataOutputStream(out);
		InputStream in = s1.getInputStream(); 						// Fornece um input stream para este socket
		DataInputStream dataIn = new DataInputStream(in);



		
		//LOOP para perguntar ao cliente se quer conectar ao server ou nao
		while (waiting) {
			
			// Get a stream associated with the socket
			dataOut.writeUTF("INIT");
			
			String request = dataIn.readUTF(); // Usa o DataInputStream para ler a string enviada pelo cliente
			
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
				
			} 
			else 
			{
				dataOut.writeUTF("INVALID_COMAND");
			}
		
			dataOut.flush();
			System.out.println("client answered");
			s.close();
		}

			




			



			//String login = dataIn.readUTF();
			//String pass = dataIn.readUTF();


			//serverUtil.credentialValidator(credentials, login,  pass);  //true se for invalido

			

			int initialpin = serverUtil.intPinVerifier(0,"initial");
			int finalpin = serverUtil.intPinVerifier(initialpin,"final");
			
			//pede ao cliente o número de discos
			disk = serverUtil.diskNumberPick(dataIn, dataOut, disk);
			
			solve = (int)Math.pow(2,disk)-1; 

			option = sc.nextLine().toUpperCase();        /*usamos .toUpperCase(); para que o jogo possa
	 		parar se o jogador seleccioanr y/Y */
			
			

			boolean endGame= false;

			while(!endGame) {


				//serverUtil.optionsMenu();
				dataOut.writeUTF("GAME_STARTED");
				String switchoption = dataIn.readUTF();


				switch (switchoption) {       //implementamos este switch para avaliar cada um dos inputs do ultilizador
				//além disso, um movimento so é contado, se existir movimento de discos entre pirâmides
				case "1":
					counter = serverUtil.diskXange(aux1,aux2, counter);    //1:A-->B
					dataOut.writeUTF("AB");
					break;

				case "2":
					counter = serverUtil.diskXange(aux1,aux3, counter);    //2:A-->C
					dataOut.writeUTF("AC");
					break;

				case "3":
					counter = serverUtil.diskXange(aux2,aux1, counter);    //3:B-->A
					dataOut.writeUTF("BA");
					break;

				case "4":
					counter = serverUtil.diskXange(aux2,aux3, counter);    //4:B-->C
					dataOut.writeUTF("BC");
					break;

				case "5":
					counter = serverUtil.diskXange(aux3,aux1, counter);    //5:C-->A
					dataOut.writeUTF("CA");
					break;

				case "6":
					counter = serverUtil.diskXange(aux3,aux2, counter);	 //6:C-->B
					dataOut.writeUTF("CB");
					break;

				case "":
					// movimento vazio é ignorado
					break;

				default:                      //qualquer outro input do 
					//serverUtil.errorDraw();   			  //mensagem de erro
					dataOut.writeUTF("ERROR");
					break;

				}

				Object[] auxx1 = aux1.toArray(); 
				size1 = auxx1.length;

				Object[] auxx2 = aux2.toArray(); 
				size2 = auxx2.length;


				Object[] auxx3 = aux3.toArray(); 
				size3 = auxx3.length;

				switch(initialpin) {                               //esta parte serve para verificar qual o tamanho do pin inicial e do pin final
																//importante para validar qunado o jogo acaba
																//ou seja quando o pin final tiver todas as peças e o pin inicial apenas uma
					case 1:
						initialTowerSize=size1;
						break;
					case 2:
						initialTowerSize=size2;
						break;
					case 3:
						initialTowerSize=size3;
						break;
				}

				switch(finalpin) {

					case 1:
						finalTowerSize=size1;
						break;
					case 2:
						finalTowerSize=size2;
						break;
					case 3:
						finalTowerSize=size3;
						break;
				}	



				if (initialTowerSize==1 && finalTowerSize ==disk+1)   {
					System.out.println("roundOver");

					dataOut.writeUTF("END_GAME");
					dataOut.writeUTF(" Game solved with sucess *\nProblem solved in "+ counter + " attemps\nCould be solved in " + solve +" attemps");


					serverUtil.displayMenu();
					displayaux = dataIn.readUTF();

					switch (displayaux) {

					case "OPTION1" :
						System.out.println("optio1selected");
						endGame = false;
						break;

					case "OPTION2":
						System.out.println("optio2selected");
						//System.out.println(serverUtil.showResults(dataScores));
						endGame = false;
						break;

					case "OPTIONQ":
						System.out.println("optioQselected");
						endGame = true;
						break;

					default:  //adicionar uma merda qq

						break;


					}
				}



			} 


			counter++;
		}

	}




