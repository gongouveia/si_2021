package trabalho3.Trabalho_3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Stack;

public class serverUtil {


	//este metodo verifica se as credenciais inseridas pelo utilizador são validas
	//para as credenciais serem validas o user e a sua password têm que existir na dataBase do servidor
	public static boolean credentialValidator(Socket s1, InputStream in, OutputStream out,HashMap<String, String> credentials,ServerSocket s, DataOutputStream dataOut,DataInputStream dataIn,boolean endGame) throws IOException {
			
			boolean validate = false;
			
			while(!validate) {
				String name = dataIn.readUTF();	//username inserida pelo cliente
				String pass = dataIn.readUTF(); //password inserida pelo cliente
			
				//itera pelos username da dataBase 
				for (String i : credentials.keySet())
				{
					// se as credenciais inseridas pelo utilizador estiverem na dataBase então as credenciais são validadas
					if (name.equals(i) & pass.equals(credentials.get(i))) {			
						validate=true;
						break;
					}
				}
				
				System.out.println("Credentials validation: " + validate);
				
				if (validate) {
					//quando as credenciais são validadas
					System.out.println("Login Valid.");
					dataOut.writeUTF("VALID_CREDENTIAL");
					endGame = false;
					
				} else {
					
					// no caso em que as credenciais são invalidas o utilizador é desconectado.
					// O cliente termina a sua execução.
					System.out.println("Login Invalid.");
					dataOut.writeUTF("INVALID_CREDENTIAL");
					endGame = true;
					validate = true;
					dataOut.close();
					dataIn.close();
					in.close();
					out.close();
					s1.close();
	
					}
					
				}
			
			return endGame;
	}
	//é no servidor que é validada a opção de jogo do cliente
		//se a opção do cliente não foi aceite pelo clente o servidor manda mensagem ao cliente a avisar e pedir uma nova opção
	
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
			dataOut.writeUTF("COUNTER_ADD");
		}
		return movement; // o counter é igualado ao movement 
	
	
	}
	

	
	public static int[] newGame(int disk, DataInputStream dataIn, DataOutputStream dataOut, Stack<Integer> aux1,Stack<Integer> aux2,Stack<Integer> aux3)throws IOException {
		
		dataOut.writeUTF("PIN_CLEAR");
		dataOut.writeUTF("GAME_STARTED");
		pinClear(aux1, aux2, aux3);

		//é chamado o metodo que pede e em que avalia se a opção do cliente é valida para o nuemro de discos de jogo
		disk = diskNumberPick(dataIn, dataOut, disk);
		//array de dois elementos em que o lemento [0] é numero do pin inicial e o elemento [1] é o pino final
				//o metodo intPinVerifier avalia se a opçaõ de pin inicial e final do utiliador é possivel
		int[] pinArray = intPinVerifier(dataIn, dataOut);
		
		dataOut.writeUTF("PIN_FILLER");
		//se todas as opções anteriores forem validas então os discos são colocados no pin inicial
		pinFiller(disk, pinArray[0], aux1, aux2, aux3);
		
		int solve = (int)Math.pow(2,disk)-1; 
		dataOut.writeUTF("COUNTER_RESET");
		return new int [] {pinArray[0], pinArray[1], disk, solve};
	}
	
	
	//quando o jogo acaba o menu final é apresentado, neste menu o cliente tem 3 opções que envia ao servidor para validar
	/*
	1-Voltar a jogar
	2-estatisticas
	3-Quit          
	*/
	public static boolean menu(DataInputStream dataIn, DataOutputStream dataOut, boolean endGame)  throws IOException {
		boolean menuOut = false;
		
		while(!menuOut) {
			dataOut.writeUTF("MENU");
			//lê a opção inserida pelo cliente
			String displayaux = dataIn.readUTF();
			
						
			switch (displayaux) {
	
				case "OPTION1" :
					System.out.println("New game starting.");
					//sai do menu e começa um novo jogo
					menuOut = true;
					endGame = false;
					break;
	
				case "OPTION2":
					System.out.println("Stats showing.");
					//volta a parecer o menu pois ainda não saiu do ciclo
					break;
		
				case "OPTIONQ":
					System.out.println("Exit from game.");
					//sai do menu e o cleinte é desconectado
					menuOut = true;
					//caso o cliente queira sair do jogo é retornado o booleano endGame para acabar o ciclo de jogo
					endGame = true;
					break;
				
					
				//caso em que a opção inserida pelo cliente não é nenhuma das opções validas do menu
				case "OPTIOND":
					System.out.println("Forbidden input - Menu");
					//volta a aparecer o menu ao cliente
					break;
	
						
			}
		}
		return endGame;
	}
	public static void pinFiller(int disk,int initialpin,Stack<Integer> aux1,Stack<Integer> aux2,Stack<Integer> aux3) {
	//passar o void para Stack<Integer>
	//Stack<Integer> aux = aux1;  //neste momento todas as stacks são iguais 
		//valores da base da STACK, permite a stack nunca ficar vazia 
		//desta maneira nunca da para jogar o ultimo valor de um pino para o outro pois o jogo não permite inserir valores maiores que o ultimo guardado na stack do pino final
		aux1.push(1000);
		aux2.push(1000);
		aux3.push(1000);
		
		//depois de ser indicado qual o pino inicial de jogo
		//os discos são colocados pela ordem correta no pin inicial (do maio para o mais pequeno)
		for (int j = disk; j >= 1; j--) {
			
			switch (initialpin) {
			//caso o pin inicial seja o que está mais a esquerda, os discos são colocados nesta stak aux1
			case 1 :
				
				aux1.push(j); 
				break;
			//caso o pin inicial seja o que está  no meio, os discos são colocados nesta stak aux2
			case 2:
				
				aux2.push(j); 
				break;
			//caso o pin inicial seja o que está no meio, os discos são colocados nesta stak aux3
			case 3:
				
				aux3.push(j); 
				break;
			}
		
		}
	//return aux;		
	}
	
	//quando queremos fazer um novo jogo as stack dão reset (Stacks vazias)
	public static void pinClear(Stack<Integer> aux1,Stack<Integer> aux2,Stack<Integer> aux3) {
		aux1.clear();
		aux2.clear();
		aux3.clear();
		
	}
	/*
	//espera que algum cliente se queira conectar a socket do servidor
	public static Boolean waitRoutine(DataInputStream dataIn, DataOutputStream dataOut) throws IOException{
		
		boolean waiting = true;	
		
		System.out.println("Waiting for client");
		// Usa o DataInputStream para ler a string enviada pelo cliente
		String request = dataIn.readUTF(); 
		System.out.println("O cliente escolheu:" + request);
		//quando o cliente se quer conectar
		if (request.equalsIgnoreCase("Y")) 
		{
			dataOut.writeUTF("PLAY");
			System.out.println("client to play");
			waiting = false;
		} 
		//quando o cliente não se quer conectar
		else if (request.equalsIgnoreCase("N"))
		{
			dataOut.writeUTF("NO_TRY");
			System.out.println("client doesn't want to play");
			waiting=true;
		} 
		//qualquer outro input inserido pelo cliente
		else 
		{
			dataOut.writeUTF("INVALID_COMAND");
		}
	
		dataOut.flush();
		System.out.println("client answered");
		
		return waiting;
	}
	*/
	
	
//verifica se o pin inicial e final que o cliente introduziu são validos
//para o pin inicial e final serem validados pelo cliente têm que obdecer as seguintes regras
	/*
	
	*pin inicial é um interio entre 1 e 3
	*pin inicial é um interio entre 1 e 3
	*o pin final é diferente do pin incial

	*/
//caso as opções sejam invalidadas pelo servidor, o cliente e pedido para inserir novos inputs de pin incial e final
public static int[] intPinVerifier(DataInputStream dataIn, DataOutputStream dataOut) throws IOException {  //verifica se o ultmimo é igual ao inicial
		
		dataOut.writeUTF("PIN_VERIFIER");
		
		int pinmax=3;  
		int pinmin=1;	
		int initialpin=0;
		int finalpin = 0;
		//array criada {pin inicial, pin final} que vai ser retornada
		int[] pinArray = new int[2];
		boolean pinCheck = false;

		while(!pinCheck ) {
			//pede o pin inicial 
			String pinIn = dataIn.readUTF();
			//pede o pin final
			String pinOut = dataIn.readUTF();
			System.out.println("Initial pin:" +  pinIn + " Final pin:" + pinOut);
			try {
				initialpin = Integer.parseInt(pinIn);
				finalpin = Integer.parseInt(pinOut);
					//pininicial>=1 
					//initialpin <= 3
					//pinfinal>=1 
					//pinfinal <= 3
					//pin inicial ser diferente do pin final
					// se isto acontecer o servidor envia a verificação ao cliente
				if(initialpin >= pinmin && initialpin <= pinmax && finalpin >= pinmin && finalpin <= pinmax && finalpin != initialpin) {
					//todas as regras são cumpridas logo o pin incial e final são validados
					pinCheck = true;
					dataOut.writeUTF("PIN_SELECTED");
					pinArray[0] = initialpin;
					pinArray[1] = finalpin;

				} else {
					//caso uma das regras não seja cumprida é enviada uma mensagem de erro ao cliente a pedir novos valores de pin inicial e final
					dataOut.writeUTF("PIN_ERROR");
					System.out.println("Invalid pin number.");
					dataOut.writeUTF("PIN_VERIFIER");
				}
				
			} catch(Exception e) {
				//caso o cliente tenha feito um dos inputs uma String
				//é apanhado neste catch e o pin inicial e final é invalido 
				//é pedido ao clinente para inserir novos inputs de pin incial e final
				dataOut.writeUTF("PIN_INVALID_NUMBER");
				System.out.println("Invalid input number for pin.");
				dataOut.writeUTF("PIN_VERIFIER");
			}
			
		}

	//retorna a array {pin inicial, pin final}
	return pinArray;  
	
	}

	//numero de discos de jogo
	// o numero de discos tem que ser maior ou igual a 3 e menos que 10
	//enquanto o cliente inserir opções erradas o cliente fica preso neste loop
	public static int diskNumberPick(DataInputStream dataIn, DataOutputStream dataOut, int disk) throws IOException {
		boolean diskCheck = false;
		int diskMin = 3; //número minimo de discos permitidos
		int diskMax = 10;//número máximo de discos permitidos
		
		while(!diskCheck ) {
			dataOut.writeUTF("DISK_NUMBER");
			//recebe input do clienet
			String diskString = dataIn.readUTF();
			
			try { 	
				// é verificado se o input do cliente é um inteiro
				disk = Integer.parseInt(diskString);
				
				if(disk >= diskMin && disk <= diskMax) {
					//no caso em que o input do cliente é um ineteiro menor ou igual a 10  emaior ou igual a 3
					//a opção do cliente é validada
					diskCheck = true;
					dataOut.writeUTF("DISKS_ACCEPTED");
				} else {
					//no caso em que a input do cliente não vai de encontro com as regras de seleção 
					//a sua escolha é invalida e é pedido um novo input ao cliente
					dataOut.writeUTF("DISKS_DECLINED");
					dataOut.writeUTF("DISK_NUMBER");
				}
				
				
			}catch(Exception e) {
				//no caso em que o cliente tenha feito como input uma String
				//é panahado nenste catch e é pedido ao cliente que insira um novo numero de discos
				dataOut.writeUTF("DISKS_DECLINED_ERROR");
				dataOut.writeUTF("DISK_NUMBER");
			}
			
		}
		System.out.println("Number of disks: " + disk);
		//retorna o numero de discos quando validado
		return disk;
	}
	

	
    
	
}
