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

	


		int disk = 0;    //número de discos usados durante o jogo 
		
		//int counter = 0; // regista o numero de passos até resolver o puzzle

		int size1 = 0;   //inicializa o tamanho de cada um dos stacks
		int size2 = 0;
		int size3 = 0;

		
		int finalTowerSize =0;
		
		
		HashMap<String, String> credentials = new HashMap<String, String>();
		
		Stack<Stack<Integer>> scores = new Stack<Stack<Integer>>();
		credentials.put("user","123");
		
		boolean waiting = true;
		boolean closeGame = false;


		//LOOP para perguntar ao cliente se quer conectar ao server ou nao
		while (waiting) {
			
			System.out.println("Waiting for client.");
			Socket s1 = s .accept();
			
			System.out.println("Server connected.");
			InputStream in = s1.getInputStream();
			OutputStream out = s1.getOutputStream();
			DataInputStream dataIn = new DataInputStream(in);
			DataOutputStream dataOut = new DataOutputStream(out);
			
			boolean endGame = false;
			
			dataOut.writeUTF("LOGIN");
			
			endGame = serverUtil.credentialValidator(s1,in, out, credentials,s,dataOut,dataIn, endGame);
			

			//pede ao cliente o número de discos
			
			int[] arrayGame = new int[4];
			
			if(!endGame) {
			
				arrayGame = serverUtil.newGame(disk, dataIn, dataOut, aux1, aux2, aux3);

			} else {
				arrayGame[0] = 0;
				arrayGame[1] = 0;
				arrayGame[2] = 0;
				arrayGame[3] = 0;
				
			}
			
			
			while(!endGame) {
				disk = arrayGame[2];
				int towerFinish =  arrayGame[1];
				
			
				
				//serverUtil.optionsMenu();
				dataOut.writeUTF("COUNTER_PRINT");
				dataOut.writeUTF("DRAW");
				dataOut.writeUTF("PLAY");
				String switchoption = dataIn.readUTF();
				
				
				dataOut.writeUTF("MOVE_DISK");

				switch (switchoption.toUpperCase()) {       //implementamos este switch para avaliar cada um dos inputs do ultilizador
				//além disso, um movimento so é contado, se existir movimento de discos entre pirâmides
				case "1":
					
					counter = serverUtil.diskXange(switchoption, dataOut, aux1,aux2, counter);    //1:A-->B
					
					break;

				case "2":
					counter = serverUtil.diskXange(switchoption, dataOut, aux1,aux3, counter);    //2:A-->C
					
					break;

				case "3":
					counter = serverUtil.diskXange(switchoption, dataOut, aux2,aux1, counter);    //3:B-->A
					
					break;

				case "4":
					counter = serverUtil.diskXange(switchoption, dataOut, aux2,aux3, counter);    //4:B-->C
					
					break;

				case "5":
					
					counter = serverUtil.diskXange(switchoption, dataOut, aux3,aux1, counter);    //5:C-->A
					
					break;

				case "6":
					counter = serverUtil.diskXange(switchoption, dataOut, aux3,aux2, counter);	 //6:C-->B

					break;

				case "":
					// movimento vazio é ignorado
					break;
				case "Y":
					// movimento vazio é ignorado
					dataOut.writeUTF("Y");
					System.out.println("Client requested to close current game.");
					closeGame = true;
					break;

				default:                      //qualquer outro input do 
					 			  //mensagem de erro
					System.out.println("Default error input.");
					dataOut.writeUTF("default");
					break;

				}
				
				
				
				Object[] auxx1 = aux1.toArray(); 
				size1 = auxx1.length;

				Object[] auxx2 = aux2.toArray(); 
				size2 = auxx2.length;


				Object[] auxx3 = aux3.toArray(); 
				size3 = auxx3.length;

				 //esta parte serve para verificar qual o tamanho do pin inicial e do pin final
				//importante para validar qunado o jogo acaba
				//ou seja quando o pin final tiver todas as peças e o pin inicial apenas uma
					

				switch(towerFinish) {

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



				if (finalTowerSize == disk+1 || closeGame)   {
					closeGame = false;
					
					if (finalTowerSize == disk+1) {
						dataOut.writeUTF("WIN");
					}
					
					System.out.println("Round Over.");
					
					
					endGame = serverUtil.menu(dataIn, dataOut, endGame);
					
					
					if(!endGame) {
						arrayGame = serverUtil.newGame(disk, dataIn, dataOut, aux1, aux2, aux3);
					} else {
						dataIn.close();
						dataOut.close();
						in.close();
						out.close();
						s1.close();
					}
				}



			} 


			counter++;
		}

	}

}


