
package si_2021.trabalho4;
import java.util.*;
import java.rmi.registry.LocateRegistry; 
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Scanner;
import java.rmi.*;  
import java.rmi.server.*;  

public class clientside {
	
	public static void main(String[] args) {	
		int invalidrequest = 0;
		
		//variavel que controla se o cliente se mantem ligado
		boolean end_connection= false;
		
		boolean valid_login = false;
		
		boolean option_menu1 = true;
		
		boolean option_menu2 = false;
		
		boolean valid_acc = true;
		
		String currentUser = "";
		
		
		Scanner sc = new Scanner(System.in);
	
		//declaração de um objeto cliente que contem pubs
		Client user;
		
		System.out.println("***********");
		System.out.println("**WELCOME**");
		System.out.println("***********\n");
					
			
		

			try
			{	
				// Returns a reference to the remote object Registry on the specified host and port.
				Registry registry = LocateRegistry.getRegistry(1234);
				Interface interfaceServer = (Interface) registry.lookup("Implement");



				while(!end_connection) {
			
					while(option_menu1) {


						System.out.println("1-LOGIN");
						System.out.println("2-EXIT");
						System.out.println("3-Create Account\n");

						
						switch (sc.nextLine()) {
						//acaso utilizador queira fazer conta
						case "1": {

							System.out.println("insert user email");
							String user_login= sc.nextLine();
							System.out.println("insert password");
							String pass_login= sc.nextLine();
							//procura por todos os clientes da base de dados
							
							 option_menu1 = interfaceServer.loginVerify(user_login,pass_login);
							 //option_menu1 é TRUE se o login for invalido 
							 //option_menu1 é FALSE se o login for valido 
							 if (option_menu1) {
								 System.out.println("Invalid Log In\n");
								 }else {
									 System.out.println("Valid Log In\n");
								 }
							break;
							
						}
						case "2": {
							//cliente não quer fazer registo nem log in
							System.out.println("Disconnected \n");
							end_connection = true;
							option_menu1 = false;
							break;
						}
						//caso utilziadr queira fazer conta
						case "3": {

							//inserir os dados de registo de utilzador
							System.out.println("insert name");
							String newname= sc.nextLine();
							System.out.println("insert mail");
							String newmail= sc.nextLine();
							System.out.println("insert password");
							String newpassword= sc.nextLine();
							System.out.println("insert affiliation");
							String newaff= sc.nextLine();
							
							
							int option_logUp = interfaceServer.logUpRoutine(newname, newmail, newpassword, newaff);
							
							//no logUp existem 3 casos que podem acontecer
							//1-o email inserido pelo cliente é repetido e não é valido
							//2-a conta é criada com sucesso  eo cliente é adicionado ao ficheiro
							//3-o email inserido pelo ciente não tem @mail no nome, ou seja tem um formato não valido
							switch (option_logUp) {
							
							case 1:
								System.out.println("ERROR - Please insert a valid email");
								System.out.println("Email repeated\n");
								break;
							
							case 2:
								System.out.println("Acc created with success\n");
								break;
							case 3:
								System.out.println("ERROR - Please insert a valid email");
								System.out.println("Insert @mail for valid email\n");
								break;
							
							}
							


						}


						
					}
				

		
		}	
					System.out.println("You have loged in.");

					//user = interfaceServer.;
					//mainMenu(sc, option_menu2, user,  interfaceServer);
				
		}
				
				
				System.out.println("Exited with success");
				invalidrequest=0;
				sc.close();
			}
		catch (Exception e) // catching Exception means that we are handling all errors in the same block
		{          // usually it is advisable to use multiple catch blocks and perform different error handling actions


			invalidrequest++;

			System.err.println("An error has occured!");
			e.printStackTrace();

			if(invalidrequest<=10) {
				System.out.println("Trying to reconnect in 3 seconds. Attemp number:"+invalidrequest);
				try{
					Thread.sleep(3000);
				} catch (InterruptedException e2) {
					// Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}

	
	public static void mainMenu (Scanner sc, boolean optionMenu, Client user, Interface interfaceServer) {
		//variavel para receber inputs do utilizador
		String input;
		while(optionMenu) {
			
			//menu apresnetado
			System.out.println("\n\nMENU USER:");
			System.out.println("1-Minhas publicações");
			System.out.println("2-Introduzir publicações");
			System.out.println("3-Publicações candidatas");
			System.out.println("4-Remover publicações");
			System.out.println("5-My Performance");
			System.out.println("6-Exit\n");
			
			//É pedido ao utilizador o que deseja
			input = sc.nextLine();
			
			//o input é recebido e analisado
			 switch(input){
			 

			case "1":
				
				System.out.println("Listar publicações:\n 1 - Por ano\n 2 - por citações");
				input = sc.nextLine();
				
				switch(input) {
					case "1":
						interfaceServer.printPubs(user, true);
						printPublications(user.getPubs());
						
						break;
					case"2":
						interfaceServer.printPubs(user, false);
						printPublications(user.getPubs());
						break;
					default:
						System.out.println("Please select 1 or 2.");
						break;
				}
				
				//Print das pubs
				
				
			
				break;
	
			case "2":
				break;
	
	
			case "3":
				//pedido ao server de pubs candidatas
				
					interfaceServer.requestPubs(user);
					
					//impressão de pubs candidatas
					int counter = 1;
					
					//controlo de entradas corretas. Prende o utilizador até este 
					//escolher um input adequado
					boolean correctInput = false;
					
					//se existir pubs para adicionar
					if(!user.requestPubs.empty()) {
						//apresenta as pubs
						
						printPublications(user.requestPubs());
						System.out.println("Select pubs. Example: 1 2 3 12 45 334");
						System.out.println("Press X to get out.");
						correctInput = false;
					
					} else {
						System.out.println("No more publications to add.");
						correctInput = true;
					}
					
					
					
					while(!correctInput) {
						//input de pubs candidatas
						input = sc.nextLine();
						
						if(input.toUpperCase().equals("X")) {
							//sai do loop
							System.out.println("Pressed X");
							correctInput = true;
							
						} else {
							//separação dos números por espaco
							String[] numberString = input.split(" ");
							//conversão de texto para inteiro
							int textToNumber;
							//stack para enviar ao server
							Stack<Integer> numberInt = new Stack<Integer>();
							
							//contador para verificar se todos os numeros sao convertidos
							counter = 1;
							
							//cada texto é convertido num inteiro. 
							//É detetado o erro de inputs q nao pode ser convertidos
							for( String i : numberString) {
								//esperamos por erros
								try {
									//passagem de texto para int
									textToNumber = Integer.parseInt(i);
									//o numero e inserido numa stack
									numberInt.push(textToNumber);
								} catch (Exception e) {
										System.out.println("Please select a correct number.");
									break;
								}
								//o counter aumenta
								counter++;
							}
							
							Stack<Pub> auxPubs = new Stack<Pub>();
							
							//se todos os inputs tiverem sido convertidos
							if(counter == numberInt.size()) {
								for(int j : numberInt) {
									//entao os objetos pubs são adicionados numa stack auxiliar
									auxPubs.push(user.requestPubs().get(j));
								}
								//a stack do utilizador e atualizada
								user.userPubsUpdate(auxPubs);
								correctInput = true;
							}
							
						}
					}	

				
				break;
	
	
			case "4":
				break;
	
	
			case "5":
				interfaceServer.performance(user);
				System.out.println("Performance: " + user.citationScore);
				break;	
	
	
			case "6":
				
				break;
	
			default:
				System.out.println("Invalid option");
				break;
	
			}
			 
		}

	}
	
	
	public static void printPublications(Stack<Pub> pub) {
		int counter = 0;
		System.out.println("Publications: ");
		for(Pub i : pub) {
			System.out.println("Publication: " + counter);
			i.print();
			counter++;
		}
	}
	
}
