package trabalho2;

import java.util.Stack;


public class Board {
	
	Stack<Tiles> tileOnTable = new Stack<Tiles>();
	Tiles topTile;
	Tiles botTile;
	int playerTiles;
	int humanPlayer = 0;
	boolean tilePlaced;
	boolean tileCheck;
	int numberPlayers = 4;
	
	boolean humanTie;
	
	public Board() {
		System.out.println("Welcome!\n");
	}
	
	public boolean boardPlace (Tiles inTile, Player player, Board table, int indexTile, int nextPlayer) {
		
		tilePlaced = false;
		
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
					tileOnTable.add(0, inTile);
					tilePlaced = true;
					
				} else {
					tileOnTable.add(0, inTile.rotateTile());
					tilePlaced = true;
				}
				
				
			} else if (inTile.checkIn(botTile.getBotNumber())) {
				//verificar se a peça colocada tem algum numero igual ao numero de baixo da peça no fundo do tabuleiro
				//caso tenha, é comparado qual dos lados da peça tem o número
				
				if(inTile.getTopNumber() == botTile.getBotNumber()) {
					tileOnTable.push(inTile);
					tilePlaced = true;
					
				} else {
					//e a peça é rotacionada, caso seja necessário
					//se a peça for uma carroça, não interessa, o primeiro if é ativado
					tileOnTable.push(inTile.rotateTile());
					tilePlaced = true;
				}
				
			} else {
				
				if (nextPlayer == humanPlayer) {
					System.out.println("A peça selecionada não é permitida.");
				}
				//rechamada do metodo para jogar 
				player.playHand(table, player, nextPlayer);
				tilePlaced = false;
			}
			
		} else {
			//se não existe peças no tabuleiro, a peça é adicionada sem restrições
			// perguntar ao jogador se quer rodar a peça
			tileOnTable.push(inTile);
			tilePlaced = true;
		}
		
		//remove a peça da mao do jogador se peça for aceite
		if (tilePlaced) {
			player.handPlayer.remove(indexTile);
			
		}
		
		return tilePlaced;
	}

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
	
	
	
	
 	public void boardShow () {
	
		if(tileOnTable.size() != 0) {
			System.out.println("____________________");
			for (int i = 0; i < tileOnTable.size(); i++) {
				System.out.println(tileOnTable.get(i).toString());
			}
			System.out.println("____________________");
		} else {
			System.out.println("Not tiles on the board.\n");
		}
	}
	
	//verifica se alguem fica sem peças na mão
	public boolean checkWin(Player[] players) {
		
		boolean win = false;
		
		for(int i = 0; i < numberPlayers; i++) {
			if (players[i].handPlayer.size() == 0) {
				win = true;
				break;
			}
		}
		//dizer quem ganhou
		
		return win;
	}

	public boolean checkDraw(Player[] players, Board table) {
		
		
		humanTie = true;
		
		for(int j = 0; j < numberPlayers; j++) {
			for(int i = 0; i < players[j].handPlayer.size(); i++) {
				humanTie = !table.checkBoardPlace(players[j].handPlayer.get(i)) && humanTie;
			}
		}
		return  (humanTie);
	}

	public void clearBoard() {
		tileOnTable.clear();
	}
}
