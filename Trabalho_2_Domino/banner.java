package trabalho2;

import java.util.ArrayList;
import java.util.Random;

public class banner {



	public static void credits()
	{
		System.out.println( "***************Credits***************\n"+
				"*Francisco Relvão | 2018285965 |MIEF*\n*Gonçalo Gouveia  | 2018277419 |MIEF*\n"
				+"*************************************\n");
	}

	public static void  begin() {
		System.out.println(
				"**************************************\n"+
				"*       Welcome to Domino - DEI      *\n" 
				+"**************************************\n");
	}

	 public static void gameover() 
	{
		System.out.println("------------------- The game is over! -------------------");
	}


	public static void round(int i)
	{
		
		System.out.println("-------------------- Match number " + i+ " ---------------------");
	}
	
	

	public static void gamescore(int[] gameScores,String userName) 
	{
				
		//criar uma variavel global qualquer para por ali o nome do jogador
		System.out.println( "-------------- Players Accumulated Points ---------------\n" + 
		userName + ":   " + gameScores[0] + "\nBot_1:   " + gameScores[1] + "\nBot_2:   " +
		gameScores[2] + "\nBot_3:   " + gameScores[3]);
	}
	
	
	

	

	public static void matchscore(int[] matchScores, String userName)
	{
		//criar uma variavel global qualquer para por ali o nome do jogador
		System.out.println("----------------- Players Match Points -----------------\n" + 
		userName + ":   " + matchScores[0] + "\nBot_1:   " + matchScores[1] + "\nBot_2:   " +
		matchScores[2] + "\nBot_3:   " + matchScores[3]);
	}
			
	
	
}
	
	


