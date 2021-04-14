package trabalho22;

import java.util.Stack;
//lol
public class Board {
	
	Stack<Tiles> tileOnTable = new Stack<Tiles>();
	Tiles topTile;      		//peça de domino na posição superior do tabuleiro
	Tiles botTile;				//peça de domino na posição superior do tabuleiro
	int playerTiles;			//variavel iteradora
	int humanPlayer = 0;        //variavel iteradora
	boolean tilePlaced;			//variavel que verifica se alguma peça foi colocada por um jogador
	boolean tileCheck;
	int numberPlayers = 4;       //quantos jogadores estão nesta partida, escalabilidade do codigo para mais jogadpres
	
	boolean tie;         //
	
	
	
	
	public Board() {
		System.out.println("Welcome!\n");
	}
	
	
	//metodo que coloca as peças no tabuleiro
		//se ainda não existir nenhuma peça no tabuleiro é emitido um aviso que o tabuleiro está vazio
	public boolean boardPlace (Tiles inTile, Player player, Board table, int indexTile, int nextPlayer) {
		
		tilePlaced = false;
					

		if (tileOnTable.size() != 0) 
		{
			
			//vai buscar a tile colocada no topo do tabuleiro
			topTile = tileOnTable.get(0);
			//vai buscar a tile colocada no fundo do tabuleiro
			botTile = tileOnTable.get(tileOnTable.size() - 1);
			
			//verificar se a peça colocada tem algum numero igual ao numero de cima da peça no topo do tabuleiro
			if ( inTile.checkIn(topTile.getTopNumber())) {
				
				//caso tenha, é comparado qual dos lados da peça tem o número
				//e a peça é rotacionada, caso seja necessário
				//se a peça for uma carroça, não interessa, o primeiro if é ativado
				if(inTile.getBotNumber() == topTile.getTopNumber()) 
					{
						tileOnTable.add(0, inTile);
						tilePlaced = true;
					
					} 
				else          //se a peça para ser colocada tem que ser rodada então entra dentro deste else
					{
						tileOnTable.add(0, inTile.rotateTile());
						tilePlaced = true;
					}
				
				
			} 
			else if (inTile.checkIn(botTile.getBotNumber())) 
			
				{
				//verificar se a peça colocada tem algum numero igual ao numero de baixo da peça no fundo do tabuleiro
				//caso tenha, é comparado qual dos lados da peça tem o número
				
					if(inTile.getTopNumber() == botTile.getBotNumber()) 
					{
						tileOnTable.push(inTile);
						tilePlaced = true;
					} 
					else 
					{
					//e a peça é rotacionada, caso seja necessário
					//se a peça for uma carroça, não interessa, o primeiro if é ativado
					
						tileOnTable.push(inTile.rotateTile());
						tilePlaced = true;
				
					}
				
			}  
			else         //se  apeça selecionada não for possivel ser jogada ou for uma entrada não aceite 
				{ 
				
				if (nextPlayer == humanPlayer) 
					{
						System.out.println("A peça selecionada não é permitida.");
					}
				//rechamada do metodo para jogar 
				
				player.playHand(table, player, nextPlayer);
				tilePlaced = false; 
				}
			
			} 
			else 
			{
			//se não existe peças no tabuleiro, a peça é adicionada sem restrições
			// perguntar ao jogador se quer rodar a peça
			tileOnTable.push(inTile);
			tilePlaced = true;
			}
		
		//remove a peça da mao do jogador se peça for aceite
			if (tilePlaced)
			{
			player.handPlayer.remove(indexTile);
			
			}
		//return booleano a dizer que a joagda doi realizada com sucesso
		return tilePlaced;
	}
//metodo q permite aos bots verificar se podem colocar uma peça, sem a colocar no tabuleiro
	public boolean checkBoardPlace (Tiles inTile) {
		
		tileCheck = false;
		
		if (tileOnTable.size() != 0) {
			
			//vai buscar a tile colocada no topo do tabuleiro
			topTile = tileOnTable.get(0);
			//vai buscar a tile colocada no fundo do tabuleiro
			botTile = tileOnTable.get(tileOnTable.size() - 1);
			
			//verificar se a peça colocada tem algum numero igual ao numero de cima da peça no topo do tabuleiro
			if ( inTile.checkIn(topTile.getTopNumber())) {
				
				//caso tenha, é comparado qual dos lados da peça tem o número
				//e a peça é rotacionada, caso seja necessário
				//se a peça for uma carroça, não interessa, o primeiro if é ativado
				if(inTile.getBotNumber() == topTile.getTopNumber()) {
					
					tileCheck = true;
				} else {
					tileCheck = true;
				}
				
				
			} else if (inTile.checkIn(botTile.getBotNumber())) {
				//verificar se a peça colocada tem algum numero igual ao numero de baixo da peça no fundo do tabuleiro
				//caso tenha, é comparado qual dos lados da peça tem o número
				
				if(inTile.getTopNumber() == botTile.getBotNumber()) {
					
					tileCheck = true;
					
				} else {
					//e a peça é rotacionada, caso seja necessário
					//se a peça for uma carroça, não interessa, o primeiro if é ativado
					
					tileCheck = true;
				}
				
			} else {
				//rechamada do metodo para jogar 
				tileCheck = false;
			}
			
		} else {
			//se não existe peças no tabuleiro, a peça é adicionada sem restrições
			
			
			tileCheck = true;
		}
		

		
		
		return tileCheck;
	}
	
	
//metodo para mostrar a peças no tabuleiro
	
 	public void boardShow () {
	
		if(tileOnTable.size() != 0)     //casos em que há peças me cima do tabuleiro
		{
			System.out.println("____________________");
			for (int i = 0; i < tileOnTable.size(); i++)   
			{
				//itera por todas as peças do tabuleiro e desenha as mesmas na consola
				System.out.println(tileOnTable.get(i).toString());
				
			}
			System.out.println("____________________");
		} 
		else    //se o tabuleiro estiver vazio
		{
			System.out.println("Not tiles on the board.\n");
		}
	}
	
	//verifica se alguem fica sem peças na mão
	public boolean checkWin(Player[] players) {
		
		boolean win = false;
		
		for(int i = 0; i < numberPlayers; i++) {
			
			if (players[i].handPlayer.size() == 0) {
				win = true;          //set win =true se algum jogador fica sem peças
				break;
			}
		}
		//dizer quem ganhou
		
		return win;
	}

	public boolean checkDraw(Player[] players, Board table) {
		//verifica se para cada jogador tem alguma peça que possa ser jogada
		
		tie = true;
		
		for(int j = 0; j < numberPlayers; j++) {       
			for(int i = 0; i < players[j].handPlayer.size(); i++) {
				
				tie = !table.checkBoardPlace(players[j].handPlayer.get(i)) && tie;
				
			}
		}
		return  (tie);
	}
	
	
	//depois de cada match o tabuleiro é limpo para ser jogado um novo match
	public void clearBoard() {
		tileOnTable.clear();
	}
}
