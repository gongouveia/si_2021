package trabalho2;

import java.util.*;



public class main {
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);

	
		int numberPlayers = 4;



		banner.begin();
		System.out.println("------ Please, insert your name:");
		System.out.println("\n\n");
		String userName = sc.nextLine();
		
		Random rand = new Random();

		String[] names_aux = {userName, "bot_1", "bot_2", "bot_3",userName, "bot_1", "bot_2", "bot_3"};
		//String[] names_aux = {userName, "bot_1", userName, "bot_1"};

		

		


		int firstPlayer = rand.nextInt(numberPlayers);
		
		int nextDealer = 0;
		int gameCounter = 1;
		/*
		System.out.println("Dealer order: \n");



		for (int k=0; k< numberPlayers;k++ ) {

			System.out.println(k + 1 + ": "+ names_aux[firstDealer + k]);

		}

		System.out.println("\n".repeat(2));
		
		System.out.println("Game order: \n");

		

		for (int k=0; k<numberPlayers;k++ ) {

			System.out.println(k + 1 + ": "+ names_aux[firstPlayer + k]);

		}

		*/
//--------------------------------------------------------------------------------
		//declaração das 28 tiles
		Tiles[] tilesBook = new Tiles[28];

		int k = 0;
		                

		for (int i = 0; i < 7; i++) {

			for (int j = 0; j < 7; j++) {

				if (i >= j) {

					tilesBook[k] = new Tiles(i,j);
					k++;
					
				}
			}	
		}

//--------------------------------------------------------------------------


		Player Bot_one = new Player();
		Player Bot_two = new Player();
		Player Bot_three = new Player();
		Player Human = new Player();
		
		Player Players_aux[] = new Player[] {Human, Bot_one, Bot_two, Bot_three, Human, Bot_one, Bot_two, Bot_three};
		
		Board table = new Board();
		
		Player Players_aux2[] = new Player[] {Human, Bot_one, Bot_two, Bot_three};
		
		boolean running = true;
		
		while(running) {
			System.out.println("-----New Game------\n" + "-----Game: "+ gameCounter + "-------\n" + "-----New Game------\n");
				//adicionei a partir de aqui e em mais um sitio um pouco mais acima que marquei onde foi
	
			
			nextDealer =  dealerRoutine(gameCounter, Players_aux, deckShuffle(), tilesBook, nextDealer, numberPlayers, names_aux);
			
			
			firstPlayer = gameRoutine(userName, names_aux, numberPlayers, firstPlayer, Players_aux2, table);
			
			//verificar se alguem jáa chegou aos 50 pontos
			running = GameOver(Players_aux, userName);
			
			//reset routine
			for(int g = 0; g < numberPlayers; g++) {
				Players_aux2[g].resetPlayer();
			}
			
			table.clearBoard();
			gameCounter++;
		}
		
		


			

			sc.close();
	}
	
	
	
	
	public static int[] getMin(int[] array){ 
	    int aux = array[0]; 
	  
	    int arrayReturn[] = new int[2];
	    
	    for(int i=1;i<array.length;i++){ 
	      if(array[i] < aux){ 
	    	  
	    	  aux = array[i];
		      arrayReturn[0] = array[i]; 
		      arrayReturn[1] = i;
	      } 
	    } 
	    return arrayReturn; 
	  } 
	
	public static int gameRoutine(String userName, String[] names_aux, int numberPlayers, int firstPlayer, Player[] Players_aux2, Board table) {
		
		int lastWinner = firstPlayer;
		
		int roundStarter;
		int roundShow = 1;
		
		boolean gameOnline = true;
		int round = 1;
		
		System.out.println("Game order: \n");

		for (int k=0; k<numberPlayers;k++ ) {

			System.out.println(k + 1 + ": "+ names_aux[firstPlayer + k]);

		}
		System.out.println("\n".repeat(2));
		
		
		System.out.println("Round: " + roundShow + " ---------------------\n");
		
			while(gameOnline) {
					
					
					if(round != 1) {
						roundStarter = 0;
					} else {
						roundStarter = lastWinner;
					}
					//itera sobre todos os jogadores, um a um
					
					for(int j = roundStarter; j < numberPlayers; j++) {
						
						//contagem das rondas
						if(j == lastWinner && round != 1) {
							roundShow++;
							System.out.println("Round: " + roundShow + " ---------------------");
							
						}
						
						humanOrBot(j,Players_aux2, table);
						
						//na primeira ronda ninguem ganha ou perde
						if(round != 1) {
							
							if(table.checkWin(Players_aux2)) {
								System.out.println("It's a win!");
								gameOnline = false;
								
								break;
							}
							
							if(table.checkDraw(Players_aux2, table)) {
								System.out.println("All players have passed! None can play!");
								gameOnline = false;
								break;
							}
							
							
						}
					
						
						
						round++;
					}
					
					
					
			}
			
			hand_score(Players_aux2, numberPlayers);
			
			int roundWinCheck[] = new int[numberPlayers]; 
			Stack<Integer> roundTieCheck = new Stack<Integer>();
			int totalPoints[] = new int[numberPlayers];
			
			for(int h = 0; h < numberPlayers; h++) {
				roundWinCheck[h] = Players_aux2[h].roundPoints;
				totalPoints[h] = Players_aux2[h].totalPoints;
				//System.out.println("Pontos: " + roundWinCheck[h]);
				
			}
			
			lastWinner = getMin(roundWinCheck)[1];
			
			//check tie
			
			
			for(int m = 0; m < numberPlayers; m++) {
				
				if(roundWinCheck[m] == getMin(roundWinCheck)[0]) {
					roundTieCheck.add(roundWinCheck[1]);
					
				}
			}
			
			if(roundTieCheck.size() != 1) {
				System.out.println("It's a tie between: ");
				for(int b = 0; b < roundTieCheck.size(); b++) {
					System.out.println(names_aux[roundTieCheck.get(b)] + "\n");
				}
				
				System.out.println("But, someone must win. The tie is decided by seat place.");
			}
			
			
			System.out.println("Round Winner: " + names_aux[lastWinner] + " with " + Players_aux2[lastWinner].roundPoints + " points.\n");
			
			banner.matchscore(roundWinCheck, userName);
			banner.gamescore(totalPoints, userName);
			
			
			return firstPlayer = lastWinner;
		
	}
		

	//verifica se é um bot ou um jogador
	public static void humanOrBot (int nextPlayer, Player[] players, Board table) {
		int humanPlayer = 0;
		
		if(nextPlayer == humanPlayer) {
			players[humanPlayer].handShow();
			table.boardShow();
			players[humanPlayer].playHand(table, players[humanPlayer], nextPlayer);
			
		} else {
			//players[nextPlayer].handShow();
			players[nextPlayer].playHand(table, players[nextPlayer], nextPlayer);
			
		}
		System.out.println("Board after this play: ");
		table.boardShow();
	}
	
	//soma da mão de um player no final da ronda
	
	public static void hand_score(Player[] player, int numberPlayers) {     // entram o gamescore anterior{
		int soma;
		
		System.out.println("-----Points in this Round--------");
		
		for(int l = 0; l < numberPlayers; l++) {
			soma = 0; 
			
			if(l == 0) {
				System.out.println("Tiles in your hand: ");
				
			} else {
				System.out.println("Tiles in the hand of Bot" + l +": ");
				
			}
			
			for(int k = 0; k < player[l].handPlayer.size(); k++) {
				
				System.out.println(player[l].handPlayer.get(k));
				
				soma += player[l].handPlayer.get(k).sumTile(player[l]);
				
			}		
			
			player[l].totalPoints += soma;
			player[l].roundPoints = soma;
			
			if(soma == 0) {
				System.out.println("Empty hand");
			}
			
			if(l == 0) {
				System.out.println("You - Points: " + soma);
				
			} else {
				System.out.println("Bot" + l + " - Points: " + soma);
				
			}
		}
		
		
	}




	  
	
	
	
	
	public static boolean GameOver( Player Players_aux[], String userName)
		{	
		String[] names = {userName, "bot_1", "bot_2", "bot_3"};
		int maxpoints = 50;
		ArrayList<String> winner = new ArrayList<String>();
		int[] gameScores = {Players_aux[0].totalPoints,Players_aux[1].totalPoints,Players_aux[2].totalPoints,Players_aux[3].totalPoints};
		
		
		
		if ( Players_aux[0].totalPoints >= maxpoints || Players_aux[1].totalPoints >= maxpoints 
				|| Players_aux[2].totalPoints >= maxpoints || Players_aux[3].totalPoints >= maxpoints)       
		{		
			
			banner.gameover();     
	
																							//array com os pontos de cada gajo
			for (int i = 0; i < 4; i++)						     
			{	
				if (Players_aux[i].totalPoints == getMin(gameScores)[0])			    		 //procura qual o mano vencedor
				{
					winner.add(names[i]);				     
				}
			}
				
			if (winner.size() == 1)						            	 					//se so existir um vencedor
			{						    		     	
				System.out.println("The winner is "+ winner.get(0) + "!");	     
			}
	
			if (winner.size() > 1)	
			{			    		     
				System.out.println("Tie between:");
				for (int i = 0; i < winner.size(); i++)
				{	
					System.out.println(winner.get(i));			    	 	 //da print a todos os empatadores
				}
	
			}						    		     
	
					
					
			
			
			
			return false;
		}
		
		return true;
			     
	}
	
	// baralha
	public static int[] deckShuffle()
	{	
		Random r = new Random();
		int[] shuffle_aux = new int[28]; 

		for (int i = 0; i < 28; i++) {                           // cria os indices de cada Tile
			shuffle_aux[i] = i;	
		}
		
		for (int i = 27; i > 0; i--)      				 //baralha   
		{                             
			
			// Pick a random index from 0 to i
			int j = r.nextInt(i+1);

			// Swap arr[i] with the element at random index
			int temp = shuffle_aux[i];
			shuffle_aux[i] = shuffle_aux[j];
			shuffle_aux[j] = temp;
		}
	return shuffle_aux;
	
}
	
	
	
	public static int dealerRoutine(int round,Player Players_aux[], int[] shuffle_aux, Tiles[] tilesBook, int Dealer, int numberPlayers, String[] names_aux)   
	{
		int Dealer_index = Dealer;
		int t = 0;
		Random rand = new Random();
		
		System.out.println("Dealer order: \n");
	
		
		
		if (round == 1) {
			Dealer_index = rand.nextInt(4);
			
		}
		
		for (int k=0; k< numberPlayers;k++ ) {
			
			System.out.println(k + 1 + ": "+ names_aux[Dealer_index + k]);
	
		}
		
		System.out.println("\n".repeat(2));
		
			for (int j=1 ; j<5 ; j++) 
				{
				for (int i=0 ; i<7 ; i++) 
					{
						Players_aux[Dealer_index+j].handInit(tilesBook[shuffle_aux[t]]);
						t++;
						if (i == 7 && t !=27) 
						{
							i=0;
							
							break;
							
						}	 
					}
				}
			
			
		
			
		Dealer_index++;
		
		if(Dealer_index > 3){
			Dealer_index = 0;
		}
		
		
		return Dealer_index;
		
	}


}


	
