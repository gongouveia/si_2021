package trabalho4;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;





public class serverside
{
	public static void main(String[] args) throws IOException, ClassNotFoundException
	{
		System.out.println("System For Managing Author Publications - Server Side");
		System.out.println("=====================================================\n");


		//--------------------------------------------------------------------------------------------------------------------------------------
		
		//--------------------------------------------------------------------------------------------------------------------------------------
		try 
		{
					
			Registry registry = LocateRegistry.createRegistry(1234); 
			
			System.out.println("CHECKPOINT1 new client");
			
			ReadWrite RWfile = new ReadWrite();
			ServerImp remoteObject = new ServerImp(RWfile);
			
			registry.bind("Implement", remoteObject);
				
			
			
			
		
			
			
			
			
		}	
		catch (Exception error)
		{
			System.err.println("An error has occured!");
			error.printStackTrace();
		}
	}
}