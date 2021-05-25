package trabalho4;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Random;
import java.util.HashMap;
public class serverside
{
	public static void main(String[] args) throws IOException, ClassNotFoundException
	{
		System.out.println("System For Managing Author Publications - Server Side");
		System.out.println("=====================================================\n");

		//ArrayList<clients> credentialsList = ;
		//--------------------------------------------------------------------------------------------------------------------------------------
		System.out.println("Credentials Database");
		HashMap<String, Client> userDB = new HashMap<String, Client>();
		
		
		Client client1 =   new Client("user1","user1@mail","123","affiliation1");
		Client client2 =   new Client("user2","user2@mail","123","affiliation2");
		Client client3 =   new Client("user3","user3@mail","123","affiliation3");
		
		userDB.put(client1.getName(), client1);
		userDB.put(client2.getName(), client2);
		userDB.put(client3.getName(), client3);
		
		//--------------------------------------------------------------------------------------------------------------------------------------
	
		System.out.println("\nPublications Database");
		HashMap<String, Pub> pubDB = new HashMap<String, Pub>();
		
		
		//Pub(String title, int year, String[] authors, String journal,int volume, int page,int nmb_citations,int DOI)
		String[] authors1= {"",""};
		Pub pub1 =   new Pub("title1",2,authors1,"journal1",1,2,2,2);
		pubDB.put(pub1.getTitle(), pub1);
		
		
		System.out.println("=====================\n");
		
		
		try 
		{
			
			
			Registry registry = LocateRegistry.createRegistry(1234); 
			
			DB remoteObject = new DB(userDB,pubDB);
			registry.bind("DB", remoteObject);
			
			
		}	
		catch (Exception error)
		{
			System.err.println("An error has occured!");
			error.printStackTrace();
		}
	}
}