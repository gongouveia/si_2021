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
			//pede ao cliente para inserir as credenciais de log in
			dataOut.writeUTF("LOGIN");
			
			//verifica se as credenciais do utilizador são validas ou não
			//caso as credenciais sejam validas o jogo pode prosseguir
			//caso as credenciais sejam invalidas o cliente é desconectado
			endGame = serverUtil.credentialValidator(s1,in, out, credentials,s,dataOut,dataIn, endGame);
			

			//pede ao cliente o número de discos
			//sendo o solve o valor minimo de jogadas em que é possivel reoslver o jogo
			int[] arrayGame = new int[4];
			
			if(!endGame) {
				
				arrayGame = serverUtil.newGame(disk, dataIn, dataOut, aux1, aux2, aux3);
				//{pin inciial, pin final,numero de discos, nuemro de passos minimos para resolver o jogo}

			} else {
				//para inicializar a array
				arrayGame[0] = 0;
				arrayGame[1] = 0;
				arrayGame[2] = 0;
				arrayGame[3] = 0;
				
			}
			
			//
			while(!endGame) {
				//numero de discos de jogo
				disk = arrayGame[2];  
				//torre final
				int towerFinish =  arrayGame[1];
				
			
				
				//serverUtil.optionsMenu();
				dataOut.writeUTF("COUNTER_PRINT");
				//envia ao cliente que para dar print do tabuleiro
				dataOut.writeUTF("DRAW");
				//mostra ao cliente as opções de jogo e pede um input valido
				dataOut.writeUTF("PLAY");
				//opção de jogo inserida pelo cliente
				String switchoption = dataIn.readUTF();
				
				dataOut.writeUTF("MOVE_DISK");
				
				switch (switchoption.toUpperCase()) {     
						//implementamos este switch para avaliar cada um dos inputs do ultilizador
				//além disso, um movimento so é contado, se existir movimento de discos entre pirâmides
				case "1":
					//1:A-->B
					counter = serverUtil.diskXange(switchoption, dataOut, aux1,aux2, counter);    
					
					break;

				case "2":
					//2:A-->C
					counter = serverUtil.diskXange(switchoption, dataOut, aux1,aux3, counter);   
					
					break;

				case "3": 
					//3:B-->A
					counter = serverUtil.diskXange(switchoption, dataOut, aux2,aux1, counter);   
					
					break;

				case "4": 
					//4:B-->C
					counter = serverUtil.diskXange(switchoption, dataOut, aux2,aux3, counter);   
					
					break;

				case "5":
					//5:C-->A
					counter = serverUtil.diskXange(switchoption, dataOut, aux3,aux1, counter);   
					
					break;

				case "6":
					//6:C-->B
					counter = serverUtil.diskXange(switchoption, dataOut, aux3,aux2, counter);	 

					break;

				case "":
					// movimento vazio é ignorado
					break;
				case "Y":
					// caso o jogador insira Y o jogo acaba 
					dataOut.writeUTF("Y");
					System.out.println("Client requested to close current game.");
					closeGame = true;
					break;

				default:   
					//qualquer outro input do cliente mostra mensagem de erro e pede um novo input ao cliente
					System.out.println("Default error input.");
					dataOut.writeUTF("default");
					break;

				}
				
				 //esta parte serve para verificar qual o ttamanho do pin final
				//importante para validar qunado o jogo acaba
				//ou seja quando o pin final tiver todas as peças
					
				
				Object[] auxx1 = aux1.toArray(); 
				size1 = auxx1.length;

				Object[] auxx2 = aux2.toArray(); 
				size2 = auxx2.length;


				Object[] auxx3 = aux3.toArray(); 
				size3 = auxx3.length;

				
				//verifica se o tprre do pino final tem todos os discos
				//quando isto acontece o é porque o jogo está completo
				
				switch(towerFinish) {

					case 1:
						//caso em que a torre final é a torre esquerda
						finalTowerSize=size1;
						break;
					case 2:
						//caso em que a torre final é a torre do meio
						finalTowerSize=size2;
						break;
					case 3:
						//caso em que a torre final é a torre direita
						finalTowerSize=size3;
						break;
				}	

				//quando a torre final tiver todos os discos o jogo acaba
				//finalTowerSize == disk+1 pois tenho que adicionar um para adicionar o valor guardado como base da stack
				if (finalTowerSize == disk+1 || closeGame)   {
					closeGame = false;
					counter = 0;
					
					if (finalTowerSize == disk+1) {
						//envia ao cliente que ganhou
						dataOut.writeUTF("WIN");
						dataOut.writeUTF("SCORE_CALC");
					}
					
					System.out.println("Round Over.");
					
					//é mostrado o menu ao cliente 
					//caso o cliente opte por fazer QUIT do jogo endGame=true
					//caso o cliente veja as estatisticas e volte a querer jogar endGame=false e recomeça um novo jogo
					endGame = serverUtil.menu(dataIn, dataOut, endGame);
					
					//quando o jogo acaba
					if(!endGame) {
						//o cliente quiser jogar mais um jogo
						// as avariaveis de jogo dão reset para começar um novo jogo
						//as stacks dão reset
						//e o número do disco tambem leva reset
						arrayGame = serverUtil.newGame(disk, dataIn, dataOut, aux1, aux2, aux3);
					} else {
						//caso o cliente não queira jogar mais 
						//o cliente é desconectado
						dataIn.close();
						dataOut.close();
						in.close();
						out.close();
						s1.close();
					}
				}
				//ao fim de cada jogada incrementa o counter
				counter++;

			} 


			
			System.out.println("Client Disconnected.");
		}

	}

}


