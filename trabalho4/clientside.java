package trabalho4;
import java.util.*;
import java.rmi.registry.LocateRegistry; 
import java.rmi.registry.Registry;

import java.util.Scanner;
import java.io.IOException;
//import java.io.IOException;
import java.rmi.*;  


public class clientside {

	public static void main(String[] args) {	



		int invalidRequest = 0;

		//variavel que controla se o cliente se mantem ligado
		boolean end_connection= false;



		boolean option_menu1 = true;
		int option_logUp;
		boolean option_menu2 = false;

		Interface interfaceServer;

		String user_login = "";


		Scanner sc = new Scanner(System.in);

		//declaraÃ§Ã£o de um objeto cliente que contem pubs
		Client user;

		System.out.println("***********");
		System.out.println("**WELCOME**");
		System.out.println("***********\n");




		try
		{	
			// Returns a reference to the remote object Registry on the specified host and port.
			Registry registry = LocateRegistry.getRegistry(1234);
			interfaceServer = (Interface) registry.lookup("Implement");



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
						boolean isItValid;
						
						
						while(true) {
						try { // tenta obter as informações do User

							//procura por todos os clientes da base de dados
							 option_menu1 = interfaceServer.loginVerify(user_login,pass_login);
							 //option_menu1 ÃƒÂ© TRUE se o login for invalido 
							 //option_menu1 ÃƒÂ© FALSE se o login for valido 
							 isItValid=interfaceServer.isThisClientRegistred(user_login);
							 

										invalidRequest = 0;
										break; // se for bem sucedido sai do while()

													
												} catch (IOException e) { // se houver uma falha na conexão trata dessa falha

													System.out.println("> Connection lost, trying again in 3 seconds");
													invalidRequest++; // incrementa em 1 as tentativas de conexão
													Thread.sleep(3000); // espera 3s para tentar outra vez

													try {
														interfaceServer = (Interface) registry.lookup("Implement");														// registry

													} catch (Exception d) { // caso não encontre o Registry
														System.out.println("> Failed to establish connection");
													}

												} catch (Exception idk) { // apanha outros possiveis erros
													System.out.print("> An error has occurred: ");
													idk.printStackTrace();
												}

												if (invalidRequest == 10) { // quando tenta conectar-se 10x sem sucesso, desliga o programa
													System.out.println("> Program terminated. Cause: Lost Connection with Server");
													System.exit(0);
												}

							}

						 
						 
						 if (option_menu1) {
							 if(isItValid) {
								 System.out.println("Your email is valid. Password incorrect");
							 }else {
								 System.out.println("Your email is not yet registred in database. Sgn Up");
							 }
							 
							 
							 
							 System.out.println("Invalid Log In\n");
							 }else {
								 System.out.println("Valid Log In\n");
								 option_menu2 = true;
								 option_menu1 = false;
							 }
					}
						break;
					
					


					case "2": {
						//cliente nÃ£o quer fazer registo nem log in
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


						while(true) {
							try { // tenta obter as informações do User


								option_logUp = interfaceServer.logUpRoutine(newname, newmail, newpassword, newaff);
								invalidRequest = 0;
								break; // se for bem sucedido sai do while()


							} catch (IOException e) { // se houver uma falha na conexão trata dessa falha

								System.out.println("> Connection lost, trying again in 3 seconds");
								invalidRequest++; // incrementa em 1 as tentativas de conexão
								Thread.sleep(3000); // espera 3s para tentar outra vez

								try {
									interfaceServer = (Interface) registry.lookup("Implement");														// registry

								} catch (Exception d) { // caso não encontre o Registry
									System.out.println("> Failed to establish connection");
								}

							} catch (Exception idk) { // apanha outros possiveis erros
								System.out.print("> An error has occurred: ");
								idk.printStackTrace();
							}

							if (invalidRequest == 10) { // quando tenta conectar-se 10x sem sucesso, desliga o programa
								System.out.println("> Program terminated. Cause: Lost Connection with Server");
								System.exit(0);
							}

						}
					}
					//no logUp existem 3 casos que podem acontecer
					//1-o email inserido pelo cliente Ã© repetido e nÃ£o Ã© valido
					//2-a conta Ã© criada com sucesso  eo cliente Ã© adicionado ao ficheiro
					//3-o email inserido pelo ciente nÃ£o tem @mail no nome, ou seja tem um formato nÃ£o valido
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
				
			if(option_menu2) {
				
				
				System.out.println("You have logged in.");	
				while(true) {
					try { // tenta obter as informações do User

						user = interfaceServer.whosClient(user_login);

						System.out.println("Current user: " + user.getName());

						option_menu1 = mainMenu(sc, option_menu2, user,  interfaceServer, user_login);
						invalidRequest = 0;
						break; // se for bem sucedido sai do while()


					} catch (IOException e) { // se houver uma falha na conexão trata dessa falha

						System.out.println("> Connection lost, trying again in 3 seconds");
						invalidRequest++; // incrementa em 1 as tentativas de conexão
						Thread.sleep(3000); // espera 3s para tentar outra vez

						try {
							interfaceServer = (Interface) registry.lookup("Implement");														// registry

						} catch (Exception d) { // caso não encontre o Registry
							System.out.println("> Failed to establish connection");
						}

					} catch (Exception idk) { // apanha outros possiveis erros
						System.out.print("> An error has occurred: ");
						idk.printStackTrace();
					}

					if (invalidRequest == 10) { // quando tenta conectar-se 10x sem sucesso, desliga o programa
						System.out.println("> Program terminated. Cause: Lost Connection with Server");
						System.exit(0);
					}

				}
			}		

			System.out.println("Exited with success");
			invalidRequest=0;
			sc.close();
			}
		}
		catch (Exception e) // catching Exception means that we are handling all errors in the same block
		{          // usually it is advisable to use multiple catch blocks and perform different error handling actions
			invalidRequest = 0;
			while(true) {
				
			
			invalidRequest++;
			
			System.err.println("An error has occured!");
			
			e.printStackTrace();
			if(invalidRequest<=10) {
				System.out.println("Trying to reconnect in 3 seconds. Attemp number:"+invalidRequest);
				try{
					Thread.sleep(3000);
				} 
				catch (InterruptedException e2) {
					// Auto-generated catch block
					e.printStackTrace();
					
				}
			}
		}
	}
}


public static boolean mainMenu (Scanner sc, boolean optionMenu, Client user, Interface interfaceServer, String user_login) throws InterruptedException, IOException {
		//variavel para receber inputs do utilizador 
		String input;
		int invalidRequest=0;
		while(optionMenu) {
			
			//menu apresnetado
			System.out.println("\n\nMENU USER:");
			System.out.println("1-Minhas publicações");
			System.out.println("2-Introduzir publicações");
			System.out.println("3-Publicações candidatas");
			System.out.println("4-Remover publicações");
			System.out.println("5-My Performance");
			System.out.println("6-Exit\n");
			
			//Ã‰ pedido ao utilizador o que deseja
			input = sc.nextLine();
			
			Registry registry = null;
			//o input Ã© recebido e analisado
			 switch(input){
			 

			case "1":
				
				System.out.println("Listar publicações:\n 1 - Por ano\n 2 - por citações");
				input = sc.nextLine();
				
				switch(input) {
					case "1":
						while(true) {
							try { // tenta obter as informações do User


											
											user = interfaceServer.printPubs(user, true);
											invalidRequest = 0;
											break; // se for bem sucedido sai do while()

														
													} catch (IOException e) { // se houver uma falha na conexão trata dessa falha

														System.out.println("> Connection lost, trying again in 3 seconds");
														invalidRequest++; // incrementa em 1 as tentativas de conexão
														Thread.sleep(3000); // espera 3s para tentar outra vez

														try {
															interfaceServer = (Interface) registry.lookup("Implement");														// registry

														} catch (Exception d) { // caso não encontre o Registry
															System.out.println("> Failed to establish connection");
														}

													} catch (Exception idk) { // apanha outros possiveis erros
														System.out.print("> An error has occurred: ");
														idk.printStackTrace();
													}

													if (invalidRequest == 10) { // quando tenta conectar-se 10x sem sucesso, desliga o programa
														System.out.println("> Program terminated. Cause: Lost Connection with Server");
														System.exit(0);
													}

								}

						
						
						
						printPublications(user.getPubs());
						
						
						break;
					case"2":
						
						while(true) {
						try { // tenta obter as informações do User


										user = interfaceServer.printPubs(user, false);				
										invalidRequest = 0;
										break; // se for bem sucedido sai do while()

													
												} catch (IOException e) { // se houver uma falha na conexão trata dessa falha

													System.out.println("> Connection lost, trying again in 3 seconds");
													invalidRequest++; // incrementa em 1 as tentativas de conexão
													Thread.sleep(3000); // espera 3s para tentar outra vez

													try {
														interfaceServer = (Interface) registry.lookup("Implement");														// registry

													} catch (Exception d) { // caso não encontre o Registry
														System.out.println("> Failed to establish connection");
													}

												} catch (Exception idk) { // apanha outros possiveis erros
													System.out.print("> An error has occurred: ");
													idk.printStackTrace();
												}

												if (invalidRequest == 10) { // quando tenta conectar-se 10x sem sucesso, desliga o programa
													System.out.println("> Program terminated. Cause: Lost Connection with Server");
													System.exit(0);
												}

							}

						
						while(true) {
						try { // tenta obter as informações do User


										
										user = interfaceServer.printPubs(user, false);				
										invalidRequest = 0;
										break; // se for bem sucedido sai do while()

													
												} catch (IOException e) { // se houver uma falha na conexão trata dessa falha

													System.out.println("> Connection lost, trying again in 3 seconds");
													invalidRequest++; // incrementa em 1 as tentativas de conexão
													Thread.sleep(3000); // espera 3s para tentar outra vez

													try {
														interfaceServer = (Interface) registry.lookup("Implement");														// registry

													} catch (Exception d) { // caso não encontre o Registry
														System.out.println("> Failed to establish connection");
													}

												} catch (Exception idk) { // apanha outros possiveis erros
													System.out.print("> An error has occurred: ");
													idk.printStackTrace();
												}

												if (invalidRequest == 10) { // quando tenta conectar-se 10x sem sucesso, desliga o programa
													System.out.println("> Program terminated. Cause: Lost Connection with Server");
													System.exit(0);
												}

							}
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
				boolean pubAdd = false;
				try {
					addNewPubNumbers[0] = Integer.parseInt(year);
					addNewPubNumbers[1] = Integer.parseInt(page);
					addNewPubNumbers[2] = Integer.parseInt(volume);
					addNewPubNumbers[3] = Integer.parseInt(DOi);
					addNewPubNumbers[4] = Integer.parseInt(citationsNumb);
					
					
					//itera por todos os autore da publicação
					for (String i: authors) {
						System.out.println("0"+i.trim()+"0"+" o user logado é:"+user_login) ;
						
		
						//caso um dos autores da publicação seja o cliente logado no momento pode adicionar  apublicação
						
						while(true) {
						try { // tenta obter as informações do User


							if  (interfaceServer.whosClient(user_login).getName().equals(i.trim())) {
								//é possivel adicionar uma publicação se:
								//-> caso o DOI não exista já na base de dados (publicação repetida 

								while(true) {
								try { // tenta obter as informações do User


												
												pubAdd = interfaceServer.addNewPub(title, journal, authors, addNewPubNumbers);			
												invalidRequest = 0;
												break; // se for bem sucedido sai do while()

															
														} catch (IOException e) { // se houver uma falha na conexão trata dessa falha

															System.out.println("> Connection lost, trying again in 3 seconds");
															invalidRequest++; // incrementa em 1 as tentativas de conexão
															Thread.sleep(3000); // espera 3s para tentar outra vez

															try {
																interfaceServer = (Interface) registry.lookup("Implement");														// registry

															} catch (Exception d) { // caso não encontre o Registry
																System.out.println("> Failed to establish connection");
															}

														} catch (Exception idk) { // apanha outros possiveis erros
															System.out.print("> An error has occurred: ");
															idk.printStackTrace();
														}

														if (invalidRequest == 10) { // quando tenta conectar-se 10x sem sucesso, desliga o programa
															System.out.println("> Program terminated. Cause: Lost Connection with Server");
															System.exit(0);
														}

									}

								
								
								
								break;
								}else {
									System.out.println("ERROR This publication is not yours!\n");
								}			
										invalidRequest = 0;
										break; // se for bem sucedido sai do while()

													
												} catch (IOException e) { // se houver uma falha na conexão trata dessa falha

													System.out.println("> Connection lost, trying again in 3 seconds");
													invalidRequest++; // incrementa em 1 as tentativas de conexão
													Thread.sleep(3000); // espera 3s para tentar outra vez

													try {
														interfaceServer = (Interface) registry.lookup("Implement");														// registry

													} catch (Exception d) { // caso não encontre o Registry
														System.out.println("> Failed to establish connection");
													}

												} catch (Exception idk) { // apanha outros possiveis erros
													System.out.print("> An error has occurred: ");
													idk.printStackTrace();
												}

												if (invalidRequest == 10) { // quando tenta conectar-se 10x sem sucesso, desliga o programa
													System.out.println("> Program terminated. Cause: Lost Connection with Server");
													System.exit(0);
												}

							}

					}

				} catch (Exception e) {
					System.out.println("Your input is invalid. Use numbers for year, page, volume, DOi and citationsNumb");
					System.out.println("");
				}
				
				if(pubAdd) {
					System.out.println("Publication added.");
					
					Pub pub = new Pub(title,addNewPubNumbers[0],authors,journal, addNewPubNumbers[2] , addNewPubNumbers[1] ,addNewPubNumbers[4] , addNewPubNumbers[3] );
					interfaceServer.addAuthortoPubDB(title,addNewPubNumbers[0],authors,journal, addNewPubNumbers[2] , addNewPubNumbers[1] ,addNewPubNumbers[4] , addNewPubNumbers[3],interfaceServer.whosClient(user_login).getName());
					
				} else {
					System.out.println("Use a different DOI number and your name.");
				}
				
				break;
	
	
			case "3":
				//pedido ao server de pubs candidatas
					
				
				while(true) {
				try { // tenta obter as informações do User


								
								user = interfaceServer.requestPubs(user);				
								invalidRequest = 0;
								break; // se for bem sucedido sai do while()

											
										} catch (IOException e) { // se houver uma falha na conexão trata dessa falha

											System.out.println("> Connection lost, trying again in 3 seconds");
											invalidRequest++; // incrementa em 1 as tentativas de conexão
											Thread.sleep(3000); // espera 3s para tentar outra vez

											try {
												interfaceServer = (Interface) registry.lookup("Implement");														// registry

											} catch (Exception d) { // caso não encontre o Registry
												System.out.println("> Failed to establish connection");
											}

										} catch (Exception idk) { // apanha outros possiveis erros
											System.out.print("> An error has occurred: ");
											idk.printStackTrace();
										}

										if (invalidRequest == 10) { // quando tenta conectar-se 10x sem sucesso, desliga o programa
											System.out.println("> Program terminated. Cause: Lost Connection with Server");
											System.exit(0);
										}

					}					
					//impressÃ£o de pubs candidatas
					int counter = 1;
					
					//controlo de entradas corretas. Prende o utilizador atÃ© este 
					//escolher um input adequado
					boolean correctInput = false;
					
					//se existir pubs para adicionar
					
					while(true) {
						if(!user.requestPubs.empty()) {
							//apresenta as pubs
							printPublications(user.requestPubs());
							System.out.println("Select publications you want to add.\nExample: 1 2 3 12 45 334");
							System.out.println("Press X to get out.");
							correctInput = false;
						
						} else {
							System.out.println("No more publications to add.");
							correctInput = true;
						}			
									invalidRequest = 0;
									break; // se for bem sucedido sai do while()		
						}

					
					
					
					while(!correctInput) {
						//input de pubs candidatas
						input = sc.nextLine();
						
						if(input.toUpperCase().equals("X")) {
							//sai do loop
							System.out.println("Pressed X");
							correctInput = true;
							
						} else {
							//separaÃ§Ã£o dos nÃºmeros por espaco
							String[] numberString = input.split(" ");
							//conversÃ£o de texto para inteiro
							int textToNumber;
							//stack para enviar ao server
							Stack<Integer> numberInt = new Stack<Integer>();
							
							//contador para verificar se todos os numeros sao convertidos
							counter = 0;
							
							//cada texto Ã© convertido num inteiro. 
							//Ã‰ detetado o erro de inputs q nao pode ser convertidos
							for( String i : numberString) {
								//esperamos por erros
								
								try {
									//passagem de texto para int, o utilizador comeca por 1 e nao em 0
									textToNumber = Integer.parseInt(i) - 1;
									//o numero e inserido numa stack
									if(textToNumber < 1 || textToNumber > user.requestPubs.size() - 1) {
										System.out.println("Pick a number between 0 and " + user.requestPubs.size() + ".");
										break;
									}
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
									//entao os objetos pubs sÃ£o adicionados numa stack auxiliar
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
				System.out.println("Remove a publication\n You can only remove publications that you added with your name.\n");
				printPublications(user.userPubs);
				
				System.out.println("Pick a DOI number or Press X to get out: ");
				
				
				while(true) {
					try {
						input = sc.nextLine();
						if(input.toUpperCase().equals("X")) {
							break;
						}
						
						int doi = Integer.parseInt(input);
						user = interfaceServer.removePub(user, doi);
						
					} catch (Exception e) {
					
						System.out.println("Select a correct DOI number.");
					} 
					

					if(user.removedPub) {
						System.out.println("Your publication was removed.");
						break;
					} else {
						System.out.println("Pick a correct DOI number. You must be the author of the pub.");
						
					}
					
				}
				
				
				
				break;
	
	
			case "5":
				
				System.out.println("Perfomance metric");
				System.out.println("Pick your H metric number: ");
				int H = 0;
				
				while(true) {
					try {
						H = Integer.parseInt(sc.nextLine());
						break;
					} catch (Exception e) {
						System.out.println("Use integer numbers for the H metric.");
					}
				}
				
				
				while(true) {
				try { // tenta obter as informações do User


								user = interfaceServer.performance(user, H);	
								invalidRequest = 0;
								break; // se for bem sucedido sai do while()

											
										} catch (IOException e) { // se houver uma falha na conexão trata dessa falha

											System.out.println("> Connection lost, trying again in 3 seconds");
											invalidRequest++; // incrementa em 1 as tentativas de conexão
											Thread.sleep(3000); // espera 3s para tentar outra vez

											try {
												interfaceServer = (Interface) registry.lookup("Implement");														// registry

											} catch (Exception d) { // caso não encontre o Registry
												System.out.println("> Failed to establish connection");
											}

										} catch (Exception idk) { // apanha outros possiveis erros
											System.out.print("> An error has occurred: ");
											idk.printStackTrace();
										

										if (invalidRequest == 10) { // quando tenta conectar-se 10x sem sucesso, desliga o programa
											System.out.println("> Program terminated. Cause: Lost Connection with Server");
											System.exit(0);
										}

					}
				}

					
				System.out.println("Total citations:" + user.citationScore[0]);
				System.out.println("H - metric:" + user.citationScore[1]);
				System.out.println("Hyundai - i10 - metric:" + user.citationScore[2]);
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
			//
			for(Pub i : pub) {
				// iterando por todas as publicações
				System.out.println("Publication: " + counter);
				//da print da publicação
				i.print();
				counter++;
			} 
		} else {
			//se não existires publicaçóes na base de dados
			System.out.println("No publications to show. Add a few.");
		}



	}

}
