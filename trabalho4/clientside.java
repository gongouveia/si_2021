
package trabalho4;
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
							 
							 
							break;
						}
						case "2": {
							//cliente não quer fazer registo nem log in
							System.out.println("disconnected \n");
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
							
							
							interfaceServer.logUpRoutine(newname, newmail, newpassword, newaff);
							


						}


						
					}
				
			
				
				

					

					
					

				
				

		
		}	
					System.out.println("DEBUG-saiu do primeiro MENU");

					//mainMenu(sc, option_menu2, currentUser);
				
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

}
