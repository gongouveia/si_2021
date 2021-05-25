package si_2021.trabalho4;
 
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface Interface extends Remote{

	public boolean loginVerify(String mail, String password) throws RemoteException;
	
	public int logUpRoutine(String newname, String newmail,String newpassword, String newaff) throws RemoteException;
	
	public Client whosClient(String passowrd) throws RemoteException ;
		
	public Client requestPubs(Client user) throws RemoteException;
	
	public Client printPubs(Client user, boolean order) throws RemoteException;
	
	public Client performance(Client user, int in) throws RemoteException;
	
	public boolean addNewPub(String title, String journal, String[] authors, int[] numbers) throws RemoteException;
	
	public void removePub(int DOI) throws IOException;
	
}
