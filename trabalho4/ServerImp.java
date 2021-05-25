package si_2021.trabalho4;

import java.io.IOException;
import java.rmi.RemoteException; 
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class ServerImp extends UnicastRemoteObject implements Interface{

	private HashMap<Integer, Pub> pubDB = new HashMap<Integer, Pub>();
	private HashMap<String, Client> clientDB = new HashMap<String, Client>();
	private ReadWrite RWfile;
	
	String currentAuthor;
	Stack<Pub> authorPubs = new Stack<Pub>();
	Stack<Pub> pubsOrder = new Stack<Pub>();
	
	int[] performanceVar = new int[3];

	//construtor. InicializaÃ§Ã£o do objeto de escrita em ficheiros
	public ServerImp( ReadWrite RWfile ) throws RemoteException
	{
		this.RWfile= RWfile;
		
	}

	//routina para dar log in
	public boolean loginVerify(String mail, String password) throws RemoteException {

		clientDB = RWfile.clientDB_updatefromfile(clientDB);
		
		for (Client i : clientDB.values()) {
			//se algum dos clientes tema mesma password e mesmo email que os inseridos no login in
			//entÃ£o o log in Ã© valido 
			if (mail.equals(i.getEmail()) && password.equals(i.getPassword()) ) {
				System.out.println("Valid Login");

				return false;
			}

		}
		return true;
	}
	
	 //rotina de criar a a conta
	//para a conta ser validada tem que passar nos seguintes requesitos:
	//1- nÃ£o existir nenhum email igual ao inserido na base de dados
	//2- o email inserido conter @mail 
	public int logUpRoutine(String newname, String newmail,String newpassword, String newaff) throws RemoteException {

		//o email Ã© a chave unica de cada cliente, nÃ£o pode estar repetida na base de dados

			System.out.println("Loggin in");
			
			clientDB = RWfile.clientDB_updatefromfile(clientDB);
		
			System.out.println("client hashmap updated from file. Server confirms it.");
			
			if (clientDB.size() > 0) {
				
			for (Client i : clientDB.values()) {
				
				if (i.getEmail().equals(newmail) ) {
					//se jÃ¡ existir um cliente registado com o mesmo mail 
					System.out.println("email repeated\n");
					System.out.println("DEBUG3");
					return 1;
					
				}
				}
			}
			
		// It returns -1 if substring is not found  
		//se @mail estiver na string entra no ciclo
		//adiciona o cliente ao ficheiro 
			
		 if ((newmail.indexOf("@mail")!=-1)) {

			//Client newclient = new Client(newname,newmail,newpassword,newaff);	
			//escreve na data base
			RWfile.write_new_client(newname, newmail, newpassword, newaff);
			//a data base Ã© actualizada
			clientDB = RWfile.clientDB_updatefromfile(clientDB);

			System.out.println("Acc created");
			       //depende se queres que depois de fazer conta volte ao menu inicial ou nÃ£o
			return 2;

		}else {
			
			System.out.println("Please insert @mail");
			
			return 3;
		}
		}
	
	
	
	//inserindo o email de um cliente retorna o cliente com as suas caracterisitcas
	public Client whosClient(String email) throws RemoteException  {
	
		clientDB = RWfile.clientDB_updatefromfile(clientDB);
		
		Client isThisClient = null;
		
		//procura por todos os nomes na base de dados
		for (String i : clientDB.keySet()) {
			//obtem os dados do cliente alocado a um certo username
			// se o email do cliente for igual ao que insrermimos
			if (clientDB.get(i).getEmail().equals(email)) {
				//retorna o cliente
				isThisClient = clientDB.get(i);
			}
		}
		return isThisClient;
		
	}
	
	//funcao para adicionar pubs candidatas a um usuÃ¡rio 

	public Client requestPubs(Client user) throws RemoteException {
		//Dar update da stack geral que tem todas as pubs ao ler um ficheiro que contem todas as pubs.
		this.pubDB.clear();
		
		this.pubDB = RWfile.pubDB_updatefromfile(this.pubDB);
		/*
		for(Pub i : pubDB.values()) {
			i.print();
		}*/
		
		//limpar a stack auxiliar
		authorPubs.clear();
		
		//nome do usuÃ¡rio atual para verificar quais pubs sÃ£o candidatas
		currentAuthor = user.getName();
		//stack auxiliar para receber pubs com o nome do usuario
		
		System.out.println("Currente Author: " + currentAuthor);
		//Loop para atualizar a publicaÃ§Ãµes do autor
		//itera sobre cada objeto pub na base de dados geral
		for (Pub selectedPub : this.pubDB.values()) {
			//de seguida, itera sobre cada um dos autores da pub
			
			for(String author : selectedPub.getAuthors())
				
			
				//se a var author for nula, entao ignora o codigo
				// tem de ser dois if's separados, senao da erro
				// null.equals nao funciona
					
					if(author != null) {
					//System.out.println("Author: "+ author + " Pub: " + selectedPub.getTitle());
					//System.out.println("Same author? " + author.equals(currentAuthor));
						if(author.equals(currentAuthor) ) {
							
							//se a pub nao estiver nas pubs jÃ¡ aceites do utilizador
							boolean pubNotIn = true;
							for(Pub auxPub : user.getPubs()) {
								if(auxPub.getDOI() == selectedPub.getDOI()) {
									pubNotIn = false;
								}
								
							}
							if(pubNotIn){
								System.out.println("Adding pub to user." + "\n");
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
		return user;
		
	}

	
	//ver pubs
	public Client printPubs(Client user, boolean order) throws RemoteException {
		pubsOrder.clear();
		int size = user.getPubs().size();
		
		int min;
		int addDoi;
		int indexRemove;
		int max;
		authorPubs = user.getPubs();
		/*
		 * Apresentar as publicaÃ§oes ao utilizador:
		 * - Por ordem crescente de citaÃ§oes
		 * - Por ordem descrescente de ano
		*/
	
	
		/*1Ã‚Âº Caso: Ordenar por ano
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

	
			/*2Ã‚Âº Caso: Ordenar por citacao
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
		return user;
	}

	public Client performance(Client user, int in) throws RemoteException {
		for (int k = 0; k < performanceVar.length; k++) {
			performanceVar[k] = 0;
		}
		
		int pubScore = 0;
		for(Pub i : user.getPubs()) {
			pubScore = i.getCitations();
			//Total citations
			performanceVar[0] += pubScore;
			
			if(pubScore >= in) {
				performanceVar[1] += pubScore;
			}
			
			if(pubScore >= 10) {
				performanceVar[2] += pubScore;
			}
		}
		
		
		user.citationScore = performanceVar;
		
		return user;
	}
	
	
	public boolean addNewPub(String title, String journal, String[] authors, int[] numbers) throws RemoteException {
		
		return RWfile.add_pub(title, numbers[0], authors, journal, numbers[2], numbers[1], numbers[4], numbers[3]);
	}
	
	public Client removePub(Client user, int DOI) throws RemoteException, IOException{
		//indicador para que o cliente sabia se uma pub foi removida
		user.removedPub = false;
		//updata do hashmap com todas as pubs
		this.pubDB = RWfile.pubDB_updatefromfile(this.pubDB);
		
		
		
		//percorrer os autores da publicacao com o doi definido pelo o usuário
		for(String authorIndex : pubDB.get(DOI).getAuthors()) {
			//se o nome do usuario for igual a um dos autores
			System.out.println("\n\n\nChecking if there is any pub this author can remove.");
			
			if(user.getName().equals(authorIndex)) {
				
				System.out.println("It's the same author. Removing pub.");
				//remove-se a entry do ficheiro
				RWfile.removePub(DOI);
				//remove-se da base dados
				this.pubDB.remove(DOI);

				//Procura-se o index da publicacao na stack do usuario
				int removeIndex = 0;
				for(int m = 0; m < user.userPubs.size(); m++) {
					if(DOI == user.userPubs.get(m).getDOI()) {
						removeIndex = m;
					}
				}
				//retira-se a pub da stack do usuario
				// a stack de request é atualizada sempre que é chamada, não é necessário remover
				user.userPubs.remove(removeIndex);
				
				System.out.println("File_Updated. Pub removed.\n\n\n");
				user.removedPub = true;
				break;
			} 
			
		}
		
		if(!user.removedPub){
			
			System.out.println("File unchanged. No pub was removed.\n\n\n");
		}
		
		return user;
	}
	public boolean isThisClientRegistred(String userEmail) throws RemoteException, IOException {
		
		return RWfile.clientRegistred(userEmail);
	}
	
	

}
		
	

