package trabalho23;

import java.util.*;



public class main {
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);

		//número de jogadores (para facilitaro debug)
		int numberPlayers = 4;


		//classe banner dá print do eventos mais importantes
		banner.begin();
		
		//pedido do nome do utilizador
		System.out.println("------ Please, insert your name:");
		System.out.println("\n\n");
		String userName = sc.nextLine();
		
		//um objeto rand é criado para criar numeros aleatorios para o primeiro dealer e o primeiro a jogar
		Random rand = new Random();
		
		//string com 8 nomes para ser mais fácil de iterar
		String[] names_aux = {userName, "bot_1", "bot_2", "bot_3",userName, "bot_1", "bot_2", "bot_3"};

		//o primeiro jogador é aleatório
		int firstPlayer = rand.nextInt(numberPlayers);
		
		//Um variavel para definir que é o dealer a cada ronda, por agora é inicializada com 0
		int nextDealer = 0;
		//contador do número de jogos, no jogo total
		int gameCounter = 1;
		
		
//--------------------------------------------------------------------------------
		//declaração das 28 tiles
		//A classe Tiles permite definir objetos dominos
		Tiles[] tilesBook = new Tiles[28];

		int k = 0;
		                
		//são necessárias 27 peças
		for (int i = 0; i < 7; i++) {

			for (int j = 0; j < 7; j++) {
				/* A peças existentes podem ser vistas como metade de uma matriz,
				em que i é o eixo do x  e j é o eixo do y.
				*/
				if (i >= j) {
					//Só queremos metade das peças, ou seja se x>y, então as peças não são contadas
					tilesBook[k] = new Tiles(i,j);
					//Quando uma peça é criada, é contada, para verificar se foram criadas 28 peças
					k++;
					
				}
			}	
		}

//--------------------------------------------------------------------------

		//São necessários 4 jogadores, 1 humano e 3 bots
		Player Bot_one = new Player();
		Player Bot_two = new Player();
		Player Bot_three = new Player();
		Player Human = new Player();
		
		//Array para iterar sobre os jogadores, para evitar alguns loops extensos
		Player Players_aux[] = new Player[] {Human, Bot_one, Bot_two, Bot_three, Human, Bot_one, Bot_two, Bot_three};
		
		//É necessário uma mesa
		Board table = new Board();
		
		//Array com todos os jogadores que necessita de loops complexos para ser iterado, dado que todos os jogadores têm de ser percorridos
		Player Players_aux2[] = new Player[] {Human, Bot_one, Bot_two, Bot_three};
		
		//boolean para verificar se o jogo total é terminado ou não, ou seja quando alguem passa os 50 pontos
		boolean running = true;
		
		while(running) {
			//Contador de jogos
			System.out.println("-----New Game------\n" + "-----Game: "+ gameCounter + "-------\n" + "-----New Game------\n");
				
	
			//o dealer é chamado para baralhar e o próximo dealer é definido
			nextDealer =  dealerRoutine(gameCounter, Players_aux, deckShuffle(), tilesBook, nextDealer, numberPlayers, names_aux);
			
			//Começa então o jogo. Quando o jogo termina, o jogador que ganha é o primeiro a jogar no proximo jogo
			firstPlayer = gameRoutine(userName, names_aux, numberPlayers, firstPlayer, Players_aux2, table);
			
			//verifica se alguem jáa chegou aos 50 pontos e termina o jogo caso necessário
			running = GameOver(Players_aux, userName);
			
			//reset routine dos jogadores: limpas os dominos das maos e os pontos por ronda
			for(int g = 0; g < numberPlayers; g++) {
				Players_aux2[g].resetPlayer();
			}
			//os dominos em cima da mesa são limpos
			table.clearBoard();
			
			//counter do jogo é incrementado se o jogo total nao for terminado
			gameCounter++;
		}
		
		


			

			sc.close(); //scan é fechado para evitar erros mesmo após o codigo terminar
	}
	
	
	
	//função que permite encontrar o valor minimo de um array, assim como o seu index
	public static int[] getMin(int[] array){ 
	    int aux = array[0]; 
	  
	    int arrayReturn[] = new int[2];
	    arrayReturn[0] = aux; 
	    arrayReturn[1] = 0;
	    
	    for(int i=1; i<array.length; i++){ 
	      if(array[i] < aux){ 
	    	  
	    	  aux = array[i];
		      arrayReturn[0] = array[i]; 
		      arrayReturn[1] = i;
	      } 
	    } 
	    return arrayReturn; //é retornado o valor minimo e o index
	  } 
	
	//Rotina para um único jogo
	public static int gameRoutine(String userName, String[] names_aux, int numberPlayers, int firstPlayer, Player[] Players_aux2, Board table) {
		
		int lastWinner = firstPlayer; //O primeiro jogador a jogar é definido recursivamente
		
		int roundStarter;
		int roundShow = 1;//Todos os jogos começam na primeira ronda, este valor é usado nos prints
		
		boolean gameOnline = true;//boolean que mantem o jogo em pé
		int round = 1; //contador de rondas interno, uma ronda so é contada quando é todos o jogadores jogam, não quando o index de um array é 0
		
		//é apresentada a ordem de jogada
		System.out.println("Game order: \n");

		for (int k=0; k<numberPlayers;k++ ) {

			System.out.println(k + 1 + ": "+ names_aux[firstPlayer + k]);

		}
		System.out.println("\n".repeat(2));
		
		//Ronda inicial
		System.out.println("Round: " + roundShow + " ---------------------\n");
		
			while(gameOnline) {
					
					//Na primeira ronda, quem começa é definido aleatoriamente
					if(round != 1) {
						roundStarter = 0;
					} else {
						//mas na outras rondas, queremos percorrer todos os jogadores
						roundStarter = lastWinner;
						//por isso é q o roundShow é um contador diferente
						//para o jogo, não interessa quando uma ronda acaba ou começa
					}
				
					//itera sobre todos os jogadores, um a um
					
					for(int j = roundStarter; j < numberPlayers; j++) {
						
						//contagem das rondas e print da ronda atual
						if(j == lastWinner && round != 1) {
							roundShow++;
							System.out.println("Round: " + roundShow + " ---------------------");
							
						}
						//metodo para decidir se deve printar ou nao a mao de um jogador e peder inputs
						humanOrBot(j,Players_aux2, table);
						
						//na primeira ronda ninguem ganha ou perde
						if(round != 1) {
							//o tabuleiro verifica se alguem fica com a mao vazia
							if(table.checkWin(Players_aux2)) {
								System.out.println("It's a win!");
								//o jogo é terminado
								gameOnline = false;
								
								break;
							}
							//o tabuleiro tem omnipotencia e consegue ver se os jogadores só conseguem passar
							if(table.checkDraw(Players_aux2, table)) {
								System.out.println("All players have passed! None can play!");
								//o jogo é terminado
								gameOnline = false;
								break;
							}
							
							
						}
					
						
						//passamos para a proxima ronda
						round++;
					}
					
					
					
			}
			//no final do jogo, são calculados os pontos
			hand_score(Players_aux2, numberPlayers);
			
			int roundWinCheck[] = new int[numberPlayers]; //array para que tem os pontos de todos os jogadores numa ronda
			Stack<Integer> roundTieCheck = new Stack<Integer>();//array para verificare empates
			int totalPoints[] = new int[numberPlayers];//array com os pontos acumulados
			
			for(int h = 0; h < numberPlayers; h++) {//os pontos são atualizados a cada ronda
				roundWinCheck[h] = Players_aux2[h].roundPoints;
				totalPoints[h] = Players_aux2[h].totalPoints;
				//System.out.println("Pontos: " + roundWinCheck[h]);
				
			}
			//quem ganha tem menos pontos
			
			lastWinner = getMin(roundWinCheck)[1];//index de quem ganha
			
			
			//verifação de empates
		
			for(int m = 0; m < numberPlayers; m++) {
				
				if(roundWinCheck[m] == getMin(roundWinCheck)[0]) {
					//verifica se existem mais jogadores com os mesmo pontos de quem ganha
					roundTieCheck.push(m);
					
				}
			}
			
			
			//se o array dos empates não tiver só um elemento, então é porque houve empate
			if(roundTieCheck.size() != 1) {
				System.out.println("It's a tie between " + roundTieCheck.size() + " players.");
				
				//print dos jogadores q empataram
				for(int b = 0; b < roundTieCheck.size(); b++) {
					System.out.println(names_aux[roundTieCheck.get(b)]);
				}
				//a desforra é feita pelo o lugar.
				System.out.println("But, someone must win. The tie is decided by seat place order.");
				lastWinner = roundTieCheck.get(0);
			}
			
			//É apresentado quem ganhou
			System.out.println("Round Winner: " + names_aux[lastWinner] + " with " + Players_aux2[lastWinner].roundPoints + " points.\n");
			//é apresentado os pontos deste jogo
			banner.matchscore(roundWinCheck, userName);
			//é apresentado os pontos totais acumulados
			banner.acumlatedScore(totalPoints, userName);
			
			// quem ganhou é o próximo a jogar
			return firstPlayer = lastWinner;
		
	}
		

	//verifica se é um bot ou um jogador
	public static void humanOrBot (int nextPlayer, Player[] players, Board table) {
		//o jogador humano está sempre no index 0 de qualquer array
		int humanPlayer = 0;
		
		if(nextPlayer == humanPlayer) {//para o ser humano, tem de ser apresentada a suas peças
			players[humanPlayer].handShow();
			table.boardShow(); //o tabuleiro antes da jogada
			players[humanPlayer].playHand(table, players[humanPlayer], nextPlayer);//é pedido ao pplayer para jogar
			
		} else {
			//players[nextPlayer].handShow();
			players[nextPlayer].playHand(table, players[nextPlayer], nextPlayer);//os bots simplesmente jogam
			
		}
		System.out.println("Board after this play: ");
		table.boardShow(); //é mostrada a mesa depois de uma jogada
	}
	
	
	//soma da mão de um player no final da ronda
	
	public static void hand_score(Player[] player, int numberPlayers) {     // entram o gamescore anterior{
		int soma;
		
		System.out.println("-----Points in this Round--------");
		//são iterados todos os players
		for(int l = 0; l < numberPlayers; l++) {
			soma = 0; //reset da soma
			
			if(l == 0) {
				System.out.println("Tiles in your hand: ");//o jogador é humano
				
			} else {
				System.out.println("Tiles in the hand of Bot" + l +": ");//o jogador é um bot
				
			}
			//São apresentadas as peças na mao de cada jogador
			for(int k = 0; k < player[l].handPlayer.size(); k++) {
				
				System.out.println(player[l].handPlayer.get(k));
				
				soma += player[l].handPlayer.get(k).sumTile(player[l]);
				
			}		
			
			player[l].totalPoints += soma;//cada jogador tem os seus pontos acumulados
			player[l].roundPoints = soma;// os pontos de rondas são atualizados
			
			if(soma == 0) {
				System.out.println("Empty hand");
				//se o jogador não tiver pontos, é pq tem a mao vazia. A peça 0|0 vale 10
			}
			
			if(l == 0) {
				System.out.println("You - Points: " + soma);//É apresentado os pontos do humano
				
			} else {
				System.out.println("Bot" + l + " - Points: " + soma);//os bots são apresentados em 3 pessoa do singular
				
			}
		}
		
		
	}
	
	//Funçao que verifica se alguem chega aos 50 pontos e termina o jogo, caso necessario
	public static boolean GameOver( Player Players_aux[], String userName)
		{	
		String[] names = {userName, "bot_1", "bot_2", "bot_3"};
		int maxpoints = 50;
		ArrayList<String> winner = new ArrayList<String>();
		int[] gameScores = {Players_aux[0].totalPoints,Players_aux[1].totalPoints,Players_aux[2].totalPoints,Players_aux[3].totalPoints};
		
		
		//Se alguem chegar aos 50 pontos
		if ( Players_aux[0].totalPoints >= maxpoints || Players_aux[1].totalPoints >= maxpoints 
				|| Players_aux[2].totalPoints >= maxpoints || Players_aux[3].totalPoints >= maxpoints)       
		{		
			//então é gameOver
			banner.gameover();     
	
			//é verificado quem tem menos pontos acumulados																			//array com os pontos de cada gajo
			for (int i = 0; i < 4; i++)						     
			{	
				if (Players_aux[i].totalPoints == getMin(gameScores)[0])			    		 //procura qual o mano vencedor
				{
					winner.add(names[i]);				     
				}
			}
				
			if (winner.size() == 1)	//caso exista só um jogador, é uma vitoria					            	 					//se so existir um vencedor
			{						    		     	
				System.out.println("The winner is "+ winner.get(0) + "!");	     
			}
	
			if (winner.size() > 1)	
			{			    	//se existirem vários jogadores, é empate	     
				System.out.println("Tie between:");
				for (int i = 0; i < winner.size(); i++)
				{	
					System.out.println(winner.get(i)); //da print a todos os empatadores
				}
	
			}						    		     
	
					
					
			
			
			
			return false;//o jogo é terminado
		}
		
		return true;//se ninguem tiver chegado aos 50 pontos, o jogo pode continuar
			     
	}
	
	// baralha os dominos, após serem criados
	public static int[] deckShuffle()
	{	
		Random r = new Random();
		int[] shuffle_aux = new int[28]; //array auxiliar com as 28 peças

		for (int i = 0; i < 28; i++) {   // cria os indices de cada Tile
			shuffle_aux[i] = i;	
		}
		
		for (int i = 27; i > 0; i--)      				 //baralha   
		{                             
			
			
			int j = r.nextInt(i+1);

			
			int temp = shuffle_aux[i];
			shuffle_aux[i] = shuffle_aux[j];
			shuffle_aux[j] = temp;
		}
	return shuffle_aux;
	
}
	
	
	//distribuição das peças baralhadas
	public static int dealerRoutine(int round,Player Players_aux[], int[] shuffle_aux, Tiles[] tilesBook, int Dealer, int numberPlayers, String[] names_aux)   
	{
		int Dealer_index = Dealer;
		int t = 0;
		Random rand = new Random();
		
		System.out.println("Dealer order: \n");
	
		
		
		if (round == 1) {
			Dealer_index = rand.nextInt(4);//o dealer é aleatorio na primeira ronda
			
		}
		
		for (int k=0; k< numberPlayers;k++ ) {//print da ordem dos dealers
			
			System.out.println(k + 1 + ": "+ names_aux[Dealer_index + k]);
	
		}
		
		System.out.println("\n".repeat(2));
		
			for (int j=1 ; j<5 ; j++) //as peças são distribuidas por cada um dos players
				{
				for (int i=0 ; i<7 ; i++) //cada jogador recebe 7 peças
					{
						Players_aux[Dealer_index+j].handInit(tilesBook[shuffle_aux[t]]);
						t++;//o dealer é ultimo a ficar com peças 
						if (i == 7 && t !=27) //a cada sete peças dadas, a entrega passa para outro jogador
						{
							i=0;
							
							break;
							
						}	 
					}
				}
			
			
		
			
		Dealer_index++;//o proximo dealer é quem está sentado à esquerda
		
		if(Dealer_index > 3){//reset do counter do dealer
			Dealer_index = 0;
		}
		
		
		return Dealer_index;//é retornado um valor para o dealer da proxima ronda
		
	}


}
