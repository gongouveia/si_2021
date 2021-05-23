package trabalho4;

import java.rmi.RemoteException; 
import java.rmi.server.UnicastRemoteObject;
import trabalho4.Interface;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.lang.Integer;


public class ServerImp extends UnicastRemoteObject implements Interface{

	private HashMap<String, Pub> pubDB = new HashMap<String, Pub>();
	private HashMap<String, Client> clientDB = new HashMap<String, Client>();
	private ReadWrite RWfile;

	
	
	
	public ServerImp( ReadWrite RWfile ) throws RemoteException
	{
		this.RWfile= RWfile;
		
	}

	public boolean loginVerify(String mail, String password) throws RemoteException {

		clientDB = RWfile.clientDB_updatefromfile(clientDB);
		
		for (Client i : clientDB.values()) {
			//se algum dos clientes tema mesma password e mesmo email que os inseridos no login in
			//então o log in é valido 
			if (mail.equals(i.getEmail()) && password.equals(i.getPassword()) ) {
				System.out.println("Valid Login");

				return false;
			}

		}
		return true;
	}
	
	
	

	public void logUpRoutine(String newname, String newmail,String newpassword, String newaff) throws RemoteException {

		//o email é a chave unica de cada cliente, não pode estar repetida na base de dados
			boolean writeClient = true;
			
			System.out.println("DEBUG1");
			
			clientDB = RWfile.clientDB_updatefromfile(clientDB);
		
			System.out.println("DEBUG23");
			
			if (clientDB.size() > 0) {
				
			for (Client i : clientDB.values()) {
				
				System.out.println("DEBU2");
				if (i.getEmail().equals(newmail) ) {
					//se já existir um cliente registado com o mesmo mail 
					System.out.println("email repeated\n");
					writeClient = false;
					System.out.println("DEBUG3");
					
				}
				}
			}
			System.out.println("DEBUG4");
		// It returns -1 if substring is not found  
		//se @mail estiver na string entra no ciclo
		//adiciona o cliente ao ficheiro 
			
		 if ((newmail.indexOf("@mail")!=-1) && writeClient) {

			Client newclient = new Client(newname,newmail,newpassword,newaff);

			
			//escreve na data base
			RWfile.write_new_client(newname, newmail, newpassword, newaff);
			//a data base é actualizada
			clientDB = RWfile.clientDB_updatefromfile(clientDB);

			System.out.println("Acc created");
			       //depende se queres que depois de fazer conta volte ao menu inicial ou não

		}else {
			System.out.println("Please insert @mail");
		}
		}
		 
}
		
	

