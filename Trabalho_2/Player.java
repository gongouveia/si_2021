package trabalho22;

import java.util.*;


public class Player {
	
	//inicilaizar variaveis 
	int roundPoints;	  //numero de pontas da ronda
	int totalPoints;	  //nunero de pontos totais do jogo	
	int tileIndex;	      //variavel auxiliar 
	int humanPlayer = 0;  //index do jogador humano
	int passCount = 0;    //variavel para verificar se uma bot passa
	
	
	String option;      
	// as peças que um player tem são um atributo
	Stack<Tiles> handPlayer = new Stack<Tiles>();
	
	Scanner sc = new Scanner(System.in);
	Random randPlay = new Random();

	//constructor
	public Player() {
		this.roundPoints = 0;
		this.totalPoints = 0;
	}
	
	
	//metodo que mostra as peças que o jogador pode jogar
	//as peças que pode jogar são as que foram atribuidas pelo dealer depois de serem baralhadas
	public void handShow() {
		System.out.println("Your hand:");
		for(int i = 0; i < handPlayer.size(); i++ ) 
		{
			
			System.out.println(i + "):");
			System.out.println(handPlayer.get(i).toString());
			
			
		}
	}
	
	//
	//
	public void playHand(Board table, Player player, int nextPlayer) 
	{
		
		//o argumennto nextPlayer indentfica se o jogador é um bot ou um humano
		
		
		
		//se o proximo jogador for o humano, então pede se inputs
		if (nextPlayer == humanPlayer) 
		{
			System.out.println("Please, pick a tile:");
			System.out.println("Pass - Press X");
			//try catch para impedir introudção de valores q nao inteiros
			option = sc.nextLine().toUpperCase();
			
			//se o input nao for um pass
			if(!option.equals("X")) 
			{
				
				//apanhar erros de inputs q nao sao inteiros
				try 
				{
					
					
					//se o jogador nao passar a ronda
					
						//o input é convertido para um numero
						tileIndex = Integer.parseInt(option);
						
						//se o numero introduzido for menor que o numero maximo de cartas na mao do jogador
						if (tileIndex < handPlayer.size()) {
							//chamar o método da table para meter o domino na mesa
							table.boardPlace(handPlayer.get(tileIndex), player, table, tileIndex, nextPlayer);
							//System.out.println(handPlayer.size());
							
						} 
						else 
						{
							System.out.println("Por favor, introduza um dos números apresentados.");
							player.playHand(table, player, nextPlayer);
							
						}
					
					
				} catch (Exception e) {
					System.out.println("Por favor, introduza um número.");
					player.playHand(table, player, nextPlayer);
				}
				
			} else {
				System.out.println("You have passed.");
			}
		
		} 
		else 
		{
			

			passCount = 0;
			System.out.println("Bot " + nextPlayer + ": Im playing!!!!");
			
			for(int j = 0; j <handPlayer.size(); j++ ) 
			{
				tileIndex = j;
				//for loop iterar sobre as peças
				
				
					//se a peça for colocada, o loop é breakcado
					
					if (table.checkBoardPlace(handPlayer.get(tileIndex))) 
					{
						table.boardPlace(handPlayer.get(tileIndex), player, table, tileIndex, nextPlayer);
						System.out.println("Bot " + nextPlayer +  ": Tile placed.");
						break;
					}
						//a peça nao for aceite, o bot passa à proxima peça
						
						//se o bot tiver percorrido todas as peças
					
					if(passCount == (handPlayer.size() - 1)) 
					{
						System.out.println("Bot " + nextPlayer +  ": I pass.");
						//ele fica preso na passa condition
						
						//este codigo nunca mais corre
						
							
					}
					
					passCount++;
						
				}
				
				
				
			
		
		}
 		
	}
		

	//atribui peças de domino a um jogador uma de cada vez, colocando na Array
	public void handInit(Tiles tileBlock) 
	{
		handPlayer.push(tileBlock);
	}
	
	//Este metodo permite dar reset a variaveis ao fim de cada jogo
	//as peças são retiradas da mesa e as peças que os jogadores são retiradas
	
	public void resetPlayer()
	{
		roundPoints = 0;
		handPlayer.clear();
		humanPlayer = 0;
		passCount = 0;
		
	}
}
