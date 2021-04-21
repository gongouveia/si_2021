package trabalho23;

import java.util.*;
public class banner {
	
	//toda esta classe tem como objetivo fazer o codigo na main mais limpo e facil de intrepertar
	//passando grande parte da parte visual para esta class, sendo mais facil de editar e localizar um excerto de codigo

	public static final void credits()
	{
		System.out.println( "***************Credits***************\n"+
				"*Francisco Relvão | 2018285965 |MIEF*\n*Gonçalo Gouveia  | 2018277419 |MIEF*\n"
				+"*************************************\n");
	}

	public static final void  begin() {
		System.out.println(
				"**************************************\n"+
				"*       Welcome to Domino - DEI      *\n" 
				+"**************************************\n");
	}

	 public static void gameover()       //chamado quando o jogo acaba
	{
		System.out.println("------------------- The game is over! -------------------");
	}


	public static void round(int i)     //recebe o numero da ronda 
	{
		
		System.out.println("-------------------- Match number " + i+ " ---------------------");
	}
	
	

	public static void acumlatedScore(int[] gameScores,String userName)     
	{
	
		System.out.println( "-------------- Players Accumulated Points ---------------\n" + 
		userName + ": " + gameScores[0] + "\nBot_1:   " + gameScores[1] + "\nBot_2:   " +
		gameScores[2] + "\nBot_3:   " + gameScores[3]);
	}
	
	
	

	

	public static void matchscore(int[] matchScores, String userName)
	{
		
		System.out.println("----------------- Players Match Points -----------------\n" + 
		userName + ": " + matchScores[0] + "\nBot_1:   " + matchScores[1] + "\nBot_2:   " +
		matchScores[2] + "\nBot_3:   " + matchScores[3]);
	}
			
	
	//no fim de cada jogo é avalidado quem é o vencedor e se há algum empate
	//em caso de empate é verificado quem empatou
	public static void winner_or_tie(ArrayList<String> winner)	
	{	
					
	if (winner.size() == 1)						  //se so existir um vencedor a array list apenas guarda um valor
		{	
			System.out.println("\n \n");
			System.out.println("The winner is"+ winner.get(0) + "!");	     
		}
	
	if (winner.size() > 1)	                      //se houver empate a array list guarda mais que um valor
		{			    		     
			System.out.println("Tie between:");
			for (int i = 0; i < winner.size(); i++)
			{	
					System.out.println(winner.get(i));		 //da print a todos os empatadores
			}
	
		}	
	}
	
	// este metodo da print na consola quem é o dealer da ronda
	
	public static void thisRoundDealer(int dealer_index,String userName)
	{
		String[] names_aux = {userName, "bot_1", "bot_2", "bot_3"};
		System.out.println("\n");
		System.out.println("This round Dealer: " + names_aux[dealer_index] +"\n" );
		
	}
	
	
	
	
	
	
}
	
	


