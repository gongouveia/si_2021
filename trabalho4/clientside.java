
package si_2021.trabalho4;
import java.util.*;
import java.rmi.registry.LocateRegistry; 
import java.rmi.registry.Registry;

import java.util.Scanner;
import java.rmi.*;  


public class clientside {
	
	public static void main(String[] args) {	
		int invalidrequest = 0;
		
		//variavel que controla se o cliente se mantem ligado
		boolean end_connection= false;
		

		
		boolean option_menu1 = true;
		
		boolean option_menu2 = false;
		

		
		String user_login = "";
		
		
		Scanner sc = new Scanner(System.in);
	
		//declara√ß√£o de um objeto cliente que contem pubs
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
							user_login = sc.nextLine();
							System.out.println("insert password");
							String pass_login= sc.nextLine();
							//procura por todos os clientes da base de dados
							
							 option_menu1 = interfaceServer.loginVerify(user_login,pass_login);
							 //option_menu1 √© TRUE se o login for invalido 
							 //option_menu1 √© FALSE se o login for valido 
							 if (option_menu1) {
								 System.out.println("Invalid Log In\n");
								 }else {
									 System.out.println("Valid Log In\n");
									 
									 option_menu2 = true;
								 }
							break;
							
						}
						case "2": {
							//cliente n√£o quer fazer registo nem log in
							System.out.println("Disconnected \n");
							end_connection = true;
							option_menu1 = false;
							option_menu2 = false;
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
							//1-o email inserido pelo cliente √© repetido e n√£o √© valido
							//2-a conta √© criada com sucesso  eo cliente √© adicionado ao ficheiro
							//3-o email inserido pelo ciente n√£o tem @mail no nome, ou seja tem um formato n√£o valido
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
					if(option_menu2) {
						System.out.println("You have logged in.");
	
						user = interfaceServer.whosClient(user_login);
						System.out.println("Current user: " + user.getName());
						option_menu1 = mainMenu(sc, option_menu2, user,  interfaceServer);
					}
					
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

	
	public static boolean mainMenu (Scanner sc, boolean optionMenu, Client user, Interface interfaceServer) throws RemoteException {
		//variavel para receber inputs do utilizador
		String input;
		while(optionMenu) {
			
			//menu apresnetado
			System.out.println("\n\nMENU USER:");
			System.out.println("1-Minhas publicaÁıes");
			System.out.println("2-Introduzir publicaÁıes");
			System.out.println("3-PublicaÁıes candidatas");
			System.out.println("4-Remover publicaÁıes");
			System.out.println("5-My Performance");
			System.out.println("6-Exit\n");
			
			//√â pedido ao utilizador o que deseja
			input = sc.nextLine();
			
			//o input √© recebido e analisado
			 switch(input){
			 

			case "1":
				
				System.out.println("Listar publicaÁıes:\n 1 - Por ano\n 2 - por citaÁıes");
				input = sc.nextLine();
				
				switch(input) {
					case "1":
						user = interfaceServer.printPubs(user, true);
						printPublications(user.getPubs());
						
						
						break;
					case"2":
						user = interfaceServer.printPubs(user, false);
						printPublications(user.getPubs());
						
						break;
					default:
						System.out.println("Please select 1 or 2.");
						break;
				}
				
				//Print das pubs
				
				
			
				break;
	
			case "2":
				int[] addNewPubNumbers = new int[5];
				
				
				System.out.println("Add a new publication: ");
				System.out.println("Title: ");
				String title = sc.nextLine();
				System.out.println("Authors \n Example: Albert Einstein / John Mayer/JoeBiden\n");
				String[] authors = sc.nextLine().split("/");
				System.out.println("Year:");
				String year = sc.nextLine();
				System.out.println("Pages:");
				String page = sc.nextLine();
				System.out.println("Journal: ");
				String journal = sc.nextLine();
				System.out.println("Volume:");
				String volume = sc.nextLine();
				System.out.println("DOI:");
				String DOi = sc.nextLine();
				System.out.println("Citations:");
				String citationsNumb = sc.nextLine();
				
				try {
					addNewPubNumbers[0] = Integer.parseInt(year);
					addNewPubNumbers[1] = Integer.parseInt(page);
					addNewPubNumbers[2] = Integer.parseInt(volume);
					addNewPubNumbers[3] = Integer.parseInt(DOi);
					addNewPubNumbers[4] = Integer.parseInt(citationsNumb);
					interfaceServer.addNewPub(title, journal, authors, addNewPubNumbers);
					
				} catch (Exception e) {
					System.out.println("Your input is invalid. Use numbers for year, page, volume, DOi and citationsNumb");
					System.out.println("");
				}
				
				break;
	
	
			case "3":
				//pedido ao server de pubs candidatas
				
					user = interfaceServer.requestPubs(user);
					
					
					
					//impress√£o de pubs candidatas
					int counter = 1;
					
					//controlo de entradas corretas. Prende o utilizador at√© este 
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
							//separa√ß√£o dos n√∫meros por espaco
							String[] numberString = input.split(" ");
							//convers√£o de texto para inteiro
							int textToNumber;
							//stack para enviar ao server
							Stack<Integer> numberInt = new Stack<Integer>();
							
							//contador para verificar se todos os numeros sao convertidos
							counter = 0;
							
							//cada texto √© convertido num inteiro. 
							//√â detetado o erro de inputs q nao pode ser convertidos
							for( String i : numberString) {
								//esperamos por erros
								
								try {
									//passagem de texto para int, o utilizador comeca por 1 e nao em 0
									textToNumber = Integer.parseInt(i) - 1;
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
									//entao os objetos pubs s√£o adicionados numa stack auxiliar
									user.requestPubs().get(j).print();
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
				optionMenu = false;
				
				
				
				return true;
			default:
				System.out.println("Invalid option");
				break;
	
			}
			 
		}
		
		return false;

	}
	
	
	public static void printPublications(Stack<Pub> pub) {
		int counter = 1;
		System.out.println("Publications: ");
		
		if(pub.size() > 0) {
			for(Pub i : pub) {
				System.out.println("Publication: " + counter);
				i.print();
				counter++;
			} 
		} else {
			System.out.println("No publications to show. Add a few.");
		}
		
	}
	
}
