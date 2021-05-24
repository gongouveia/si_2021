package si_2021.trabalho4;

import java.rmi.RemoteException; 
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class ServerImp extends UnicastRemoteObject implements Interface{

	private HashMap<String, Pub> pubDB = new HashMap<String, Pub>();
	private HashMap<String, Client> clientDB = new HashMap<String, Client>();
	private ReadWrite RWfile;
	
	String currentAuthor;
	Stack<Pub> authorPubs = new Stack<Pub>();
	Stack<Pub> pubsOrder = new Stack<Pub>();
	
	int performanceVar;

	//construtor. Inicialização do objeto de escrita em ficheiros
	public ServerImp( ReadWrite RWfile ) throws RemoteException
	{
		this.RWfile= RWfile;
		
	}

	//routina para dar log in
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
	
	//Routina para dar Sign up
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
	
	//funcao para adicionar pubs candidatas a um usuário
	public void requestPubs(Client user) {
		//Dar update da stack geral que tem todas as pubs ao ler um ficheiro que contem todas as pubs.
		pubDB = RWfile.pubDB_updatefromfile(pubDB);
		//limpar a stack auxiliar
		authorPubs.clear();
		
		//nome do usuário atual para verificar quais pubs são candidatas
		currentAuthor = user.getName();
		//stack auxiliar para receber pubs com o nome do usuario
		
		System.out.println("Currente Author: " + currentAuthor);
		//Loop para atualizar a publicações do autor
		//itera sobre cada objeto pub na base de dados geral
		for (Pub selectedPub : pubDB.values()) {
			//de seguida, itera sobre cada um dos autores da pub
			
			for(String author : selectedPub.getAuthors())
				//se a var author for nula, entao ignora o codigo
				// tem de ser dois if's separados, senao da erro
				// null.equals nao funciona
					
					if(author != null) {
						if(author.equals(currentAuthor) ) {
							//se a pub nao estiver nas pubs já aceites do utilizador
							boolean pubNotIn = true;
							for(Pub auxPub : user.getPubs()) {
								if(auxPub.getDOI() == selectedPub.getDOI()) {
									pubNotIn = false;
								}
								
							}
							if(pubNotIn){
								// se um deles for o autor que atualmente esta em sessao
								//entao o array auxiliar ao atualizado
								authorPubs.push(selectedPub);
							}
						
						}
					}
		
		}
		
		//update da stack do user
		user.requestPubs.clear();
		user.requestPubsUpdate(authorPubs);
		
		
	}

	//ver pubs
	public void printPubs(Client user, boolean order) {
		pubsOrder.clear();
		int size = user.getPubs().size();
		
		int min;
		int addDoi;
		int indexRemove;
		int max;
		authorPubs = user.getPubs();
		/*
		 * Apresentar as publicaçoes ao utilizador:
		 * - Por ordem crescente de citaçoes
		 * - Por ordem descrescente de ano
		*/
	
	
		/*1Âº Caso: Ordenar por ano
		 *
		 *ALgoritmo para ordenar por ano.
		 *Primeiro escolhe se uma pub e depois verifica-se a 
		 *que tem o ano mais pequeno entre todas. A pub com o ano
		 *mais pequeno e adicionado a uma stack vazia. A pub e 
		 *removida da stack das pubs do autor
		 *Reinicia-se o loop com uma pub a menos.
		 */
				
		if(order)	{
				while(pubsOrder.size() != size) {
					min = authorPubs.get(0).getYear();
					addDoi = authorPubs.get(0).getDOI();
					indexRemove = 0;
					
					for(int j = 0; j < authorPubs.size(); j++) {
						Pub pub = authorPubs.get(j);
						
						if(pub.getYear() < min) {
							min = pub.getYear();
							addDoi = pub.getDOI();
							indexRemove = j;
						}
						
					}
					pubsOrder.push(pubDB.get(addDoi));
					authorPubs.remove(indexRemove);
					
				}
			
				
		} else {

	
			/*2Âº Caso: Ordenar por citacao
			 *
			 *ALgoritmo para ordenar por citacao.
			 *Primeiro escolhe se uma pub e depois verifica-se a 
			 *que tem o numero de citacoes maior entre todas. A pub com o numero 
			 *de citacoes maior e adicionada a uma stack vazia.
			 *A pub e removida da stack das pubs do autor
			 *Reinicia-se o loop com uma pub a menos.
			 */
			
			/*Nao da para por em funcao, pq este loop descobre um
			 * maximo e depende de metodos de uma classe, nao e iterativos
			*/
			while(pubsOrder.size() != size) {
				
				max = authorPubs.get(0).getCitations();
				addDoi = authorPubs.get(0).getDOI();
				indexRemove = 0;
				
				for(int j = 0; j < authorPubs.size(); j++) {
					Pub pub = authorPubs.get(j);
					
					if(pub.getCitations() > max) {
						max = pub.getCitations();
						addDoi = pub.getDOI();
						indexRemove = j;
					}
					
				}
				pubsOrder.push(pubDB.get(addDoi));
				authorPubs.remove(indexRemove);
				
			}
		
		}
		
		//update nas pubs do user
		user.userPubsUpdate(pubsOrder);
			
	}

	public void performance(Client user) {
		performanceVar = 0;
		
		for(Pub i : user.getPubs()) {
			performanceVar += i.getCitations();
		}
		
		user.citationScore = performanceVar;
	}
	

}
		
	

