package trabalho23;

public class Tiles {
	
	private int top, bot;
	private int sum;
	
	//Construtor
	public Tiles(int top, int bot)
	{
		
		this.top = top;
		this.bot = bot;
	}
	
	
	//metodo que permite obter o valor superior da peça de domino
	public int getTopNumber() 
	{
		return top;
	}
	
	//metodo que permite obter o valor inferior da peça de domino
	public int getBotNumber() 
	{
		return bot;
	}
	
	//metodo que permite atribuir um valor superior à peça de domino
	//tem com entrada o valor que se vai atribuir
	public void setTopNumber(int top) 
	{
			this.top = top;
	}
	
	//metodo que permite atribuir um valor inferior à peça de domino
	//tem com entrada o valor que se vai atribuir
	public void setBotNumber(int bot) 
	{
		this.bot = bot;
	}
	
	//metodo que rodar uma peça de domino, 
	//util quando se quer jogar uma peça em cima da mesa e é necessario a rodar 
	public Tiles rotateTile() 
	{
		return new Tiles(this.bot, this.top);
	}
	
	
	
	//metodo que soma os valores de cada peça 
	//tem com entrada os valores superior e inferior de cada peça e o jogador que tem a peça 
	// se |i|j| i = j = 0 então a peça tem 10 pontos se for a unica na mão do jogador
	// em outros casos a peça tem i +j pontos
	
	
	public int sumTile(Player player) 
	{      //número de pontos de um azulejo

		if(this.top == 0 && this.bot == 0)
		{		   										// se |i|j| i = j = 0 então a peça tem 10 pontos
														//apenas se for a unica na mão do jogador
			if (player.handPlayer.size() == 1) 
			{

				sum = 10;
			} 
			else 
			{
				sum = 0;		// se |i|j| i = j = 0 e não for a unica peça do tabuleiro então a soma é 0 pontos				
											
			}

		} 
		else
		{
			// em outros casos a peça tem soma = i +j pontos
			sum = this.bot + this.top;
			
		}
		return sum;
	}
	
	//metódo que desenha a peça no tabuleiro
	//
	// (caso 1)  i!=j          (caso 2) i = j 
	//
	//     |j|                    |i|j|
	//     ---
	//	   |i|
	//
	
	
	public String toString()
	{
		if (this.top == this.bot)         //caso 1 
		{
			return "\n" + "|" + this.top + "|" + this.top + "|" + "\n";        
		}
		else							  //caso 2
		{
			return "\n" + "|" + this.top + "|" + "\n" + "---" + "\n" + "|" + this.bot+ "|" + "\n";
		}
		
	}

	//método para verificar se um número existe numa peça
	//tem como valor de entrada o valor que queremos verificar s eexiste na peça de domino
	
	public boolean checkIn(int number) 
	{
		
		if (number == this.top) 
		{    								 // verifica se o numero que queremos verificar existe na posição superior da peça 
			return true;					 // se for verdade retorna true
		} 
		else if ( number == this.bot)
		{   								 // verifica se o numero que queremos verificar existe na posição inferior da peça 
			return true;					 // se for verdade retorna true
		} 
		else 								// caso o numero que procuramos não esteja em nenhum posição do azulejo 
		{									//é retornado um false
			return false;
		}
	}
}

