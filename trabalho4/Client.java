package si_2021.trabalho4;

import java.io.Serializable;
import java.util.Stack;


public class Client implements Serializable {
	
	
	
	//
	private String email, password,name,affiliation;
	
	int[] citationScore = new int[3];
	//stack de pubs candidatas
	Stack<Pub> requestPubs = new Stack<Pub>();
	//stack de pubs já aceites pelo o utilizador
	Stack<Pub> userPubs = new Stack<Pub>();
	
	boolean removedPub = false;
	//constructor 
	public Client(String name, String email, String password, String affiliation)
	{
		this.email = email;     //email----- chave primaria
		this.password = password;
		this.name= name;
		this.affiliation= affiliation;
	}
	
	
	
	public String getName()
	{
		return name;
	}
	public void setName(String name )
	{
		this.name = name;
	}
	
	
	
	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}
	
	

	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	

	public String getAffiliation()
	{
		return affiliation;
	}
	public void setAffiliation(String affiliation)
	{
		this.affiliation = affiliation;
	}

	//Pedir a stack de pubs
	public Stack<Pub> getPubs(){
		return this.userPubs;
	}
	
	//PEdir a stack de pubs candidatas
	public Stack<Pub> requestPubs() {
		return this.requestPubs;
	}
	
	//update da stack de pubs
	public void userPubsUpdate(Stack<Pub> stack) {
		for(Pub i : stack) {
			this.userPubs.push(i);
		}
	
	}
	//update da stack de pubs candidatas
	public void requestPubsUpdate(Stack<Pub> stack) {
		for(Pub i : stack) {
			i.print();
		}
		this.requestPubs = stack;
	}

}
	