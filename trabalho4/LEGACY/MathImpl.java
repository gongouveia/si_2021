package trabalho4;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MathImpl extends UnicastRemoteObject implements IMath
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1153438067291977046L;

	public MathImpl() throws RemoteException
	{
		// empty
	}

	public int add(int a, int b) throws RemoteException
	{
		System.out.println("A executar add...");
		return a + b;
	}

	public int mult(int a, int b) throws RemoteException
	{
		return a * b;
	}
}