package trabalho4;
import java.rmi.*;  
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Hashtable;
import java.rmi.server.*;  

public interface Interface extends Remote{

	public boolean loginVerify(String mail, String password) throws RemoteException;
	
	public void logUpRoutine(String newname, String newmail,String newpassword, String newaff) throws RemoteException;
		
}
