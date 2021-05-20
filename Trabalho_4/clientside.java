
package trabalho4;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Scanner;

public class clientside
{
	public static void main(String[] args) 
	{	
		int invalidrequest = 0;
		boolean end_connection= false;

		try
		{	

			System.out.println("tried initialize");


			// Returns a reference to the remote object Registry on the specified host and port.
			//Registry registry = LocateRegistry.getRegistry( 1234);
			Scanner sc = new Scanner(System.in);
			System.out.println("registred");
			// 'lookup' returns the remote reference bound to the specified name in this registry.




			//DB remoteObject = (DB) registry.lookup("DB");


			//HashMap<String, Client> clientDB= remoteObject.getClientDB();
			//HashMap<String, Pub> pubDB= remoteObject.getPubDB();


			HashMap<String, Client> clientDB = new HashMap<String, Client>();


			Client client1 =   new Client("user1","user1@mail","123","affiliation1");
			Client client2 =   new Client("user2","user2@mail","123","affiliation2");
			Client client3 =   new Client("user3","user3@mail","123","affiliation3");

			clientDB.put(client1.getName(), client1);
			clientDB.put(client2.getName(), client2);
			clientDB.put(client3.getName(), client3);

			System.out.println("***********");
			System.out.println("**WELCOME**");
			System.out.println("***********\n");
			while(!end_connection) {
				boolean valid_login=false;
				boolean option_menu1=true;

				while(option_menu1) {


					System.out.println("1-LOGIN");
					System.out.println("2-EXIT");
					System.out.println("3-Create Account\n");


					option_menu1=true;
					switch (sc.nextLine()) {
					//acaso utilizador queira fazer conta
					case "1": {
						
						System.out.println("insert user email");
						String user_login= sc.nextLine();
						System.out.println("insert password");
						String pass_login= sc.nextLine();
						//procura por todos os clientes da base de dados
						for (Client i : clientDB.values()) {
							//se algum dos clientes tema mesma password e mesmo email que os inseridos no login in
							//então o log in é valido 
							if (user_login.equals(i.getEmail()) & pass_login.equals(i.getPassword()) ) {
								option_menu1=false;
								valid_login=true;
								System.out.println("Valid Login");
							}

						}
						if (!valid_login) {
							//caso o email  erespetiva password não estejam na base de dados
							System.out.println("Invalid Login");
						}

						break;
					}
					case "2": {
						//cliente não quer fazer registo nem log in
						System.out.println("disconnected \n");
						end_connection=true;
						option_menu1=false;
						break;
					}
					//caso utilziadr queira fazer conta
					case "3": {
						boolean valid_acc= true;
						//inserir os dados de registo de utilzador
						System.out.println("insert name");
						String newname= sc.nextLine();
						System.out.println("insert mail");
						String newmail= sc.nextLine();
						System.out.println("insert password");
						String newpassword= sc.nextLine();
						System.out.println("insert affiliation");
						String newaff= sc.nextLine();


						for (Client i : clientDB.values()) {
							//o email é a chave unica de cada cliente, não pode estar repetida na base de dados
							if (i.getEmail().equals(newmail) ) {
								valid_acc=false;
								System.out.println("\n email repeated\n");
							}
						}

						if (valid_acc) {
							Client newclient = new Client(newname,newmail,newpassword,newaff);
							//adiciona o user a base de dados
							clientDB.put(newclient.getName(), newclient);
							option_menu1=true;       //depende se queres que depois de fazer conta volte ao menu inicial ou não
							System.out.println("acc created");
							break;
						}
						else {
							//caso já exista alguma conta com o mesmo email que o inserido
							System.out.println("acc already exists");
						}
						break;
					}
					default:
						//caso utilizador insira qualquer opção invalida
						System.out.println("Invalid option");  
						break;


					}


				}

				System.out.println("DEBUG-saiu do primeiro MENU");
				boolean option_menu2=true;
				boolean log_out=false;
				if (!option_menu1 && option_menu2 && !log_out) {

					while(option_menu2) {

					System.out.println("\n\nMENU USER:");
					System.out.println("1-Minhas publicações");
					System.out.println("2-Introduzir publicações");
					System.out.println("3-Publicações candidatas");
					System.out.println("4-Remover publicações");
					System.out.println("5-My Performance");
					System.out.println("6-Exit\n");
					sc.nextLine();
					/*
					switch(sc.nextLine()){
		
					case "1":

						break;
					
					case "2":
						break;
						
						
					case "3":
						break;
						
						
					case "4":
						break;
						
						
					case "5":
						
						break;	
					
					
					case "6":
						System.out.println("disconnected \n");
						log_out=true;
						option_menu2=false;
						break;
					
					default:
						System.out.println("Invalid option");
						break;
					
					}
					*/
					}

				}
				System.out.println("DEBUG-chechpoint1");

			}
			System.out.println("Exited with success");
			invalidrequest=0;
			sc.close();

		}

		catch (Exception e) // catching Exception means that we are handling all errors in the same block
		{          // usually it is advisable to use multiple catch blocks and perform different error handling actions

			do {
				invalidrequest++;
				System.err.println("An error has occured!");
				e.printStackTrace();

				System.out.println("Trying to reconnect in 3 seconds. Attemp number:"+invalidrequest);
				try{
					Thread.sleep(3000);
				} catch (InterruptedException e2) {
					// Auto-generated catch block
					e.printStackTrace();
				}

			}while(invalidrequest<=9);
			System.out.println("conection timed out");

		}
	}
}