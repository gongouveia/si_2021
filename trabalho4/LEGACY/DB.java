package trabalho4;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;


import java.util.HashMap;


public class DB extends UnicastRemoteObject implements Interface
{
	

	HashMap<String, Client> clientDB;
	HashMap<String, Pub> pubDB;
	//private ArrayList<Pub> pubDB;

	public DB( HashMap<String, Client> clientDB,HashMap<String, Pub> pubDB ) throws RemoteException
	{
		this.clientDB = clientDB;
		this.pubDB = pubDB;
	}
	
	
	
	public HashMap<String, Client>  getClientDB() throws RemoteException
	{
		return clientDB;
	}
	
	
	public HashMap<String, Pub>  getPubDB() throws RemoteException
	{
		return pubDB;
	}
	
	
	
}