package trabalho3.Trabalho_3;

import java.util.HashMap;
import java.util.Stack;

public class clientUtil {
	public static void main(String args[]) {


	}
	
	public static void welcome() {
		System.out.println("*".repeat(25));
		System.out.println("* Welcome to Hanoi Tower *");
		System.out.println("*".repeat(25));	
	}
	
	public static void errorDraw() {         // implementamos esta função para avisas qnd um input do utilizador é invalido
		System.out.println("\n");
		System.out.println(" "+"*".repeat(35));
		System.out.println("****** Try again, not possible. ******");
		System.out.println("****** Please, insert a number. ******");
		System.out.println(" "+"*".repeat(35));
		System.out.println("\n");  
	}

	public static void playOptions() {

		System.out.println("1:A-->B");                        
		System.out.println("2:A-->C");
		System.out.println("3:B-->A");
		System.out.println("4:B-->C");
		System.out.println("5:C-->A");
		System.out.println("6:C-->B");
		System.out.println("Press Y to end game.");

		//return "1:A-->B\n2:A-->C\n3:B-->A\n4:B-->C\n5:C-->A\n6:C-->B\nPress Y to Exit.";

	}

	public static void diskXange (Stack<Integer> a ,Stack<Integer> b) {

		int var;
		var = a.pop(); 
		b.push(var);
			
	}
	
	public static void pinFiller(int disk,int initialpin,Stack<Integer> aux1,Stack<Integer> aux2,Stack<Integer> aux3) {
		//passar o void para Stack<Integer>
		//Stack<Integer> aux = aux1;  //neste momento todas as stacks são iguais 
		aux1.push(1000);
		aux2.push(1000);
		aux3.push(1000);
		for (int j = disk; j >= 1; j--) {
				
				switch (initialpin) {
			
				case 1 :
					
					aux1.push(j); 
					break;
			
				case 2:
					
					aux2.push(j); 
					break;
			
				case 3:
					
					aux3.push(j); 
					break;
				}
			
			}
		//return aux;		
		}

	public static void pinClear(Stack<Integer> aux1,Stack<Integer> aux2,Stack<Integer> aux3) {
		aux1.clear();
		aux2.clear();
		aux3.clear();
		
	}

	public static void draw(int disk, Stack<Integer> towerOne, Stack<Integer> towerTwo, Stack<Integer> towerThree) {


		/*   
		 * ------------------------- FORMULAÇÂO MATEMÁTICA --------------------------------
		 * 
		 *   Considere-se a seguinte pirâmide e a sua comparação com uma stack:
		 *   
		 *   Na vertical, o index da Stack. Na horizontal, o index da coluna a
		 *   escrever. Um pixel é considerado um ponto desta matriz.
		 *   
		 *   index Stack
		 *     . . . | . . .  
		 *	 3 . . * * * . .     Stack:  [ 1 - diskNumber at index 3
		 *	 2 . * * * * * .               2 
		 *	 1 * * * * * * *               3
		 *	 0 0 1 2 3 4 5 6              100]
		 *	 
		 *
		 * 	 O número máximo de pixeis por torre é definido pelo o numero de 
		 *	 peças escolhidas no inicio do jogo. Isto porque a maior peça  
		 *	 define este numero máximo de pixeis.
		 * 	 
		 *	 Logo, se L for o numero máximo de pixeis numa linha e uma torre, então
		 *	 L é igual a 2*disk, sendo o disk o número peças em jogo.
		 *	 
		 *	 É necessário definir o centro geométrico das torres. 
		 *	 O centro geométrico é sempre halfL = disk e metade do tamanho total, L.
		 *	 
		 *	 Por fim, cada disco é desenhado com simetria em relação ao eixo
		 *	 da base de sustento. Logo, o pixeis com (halfL, y) fazem sempre
		 * 	 parte do desenho de um disco. 
		 *   A partir deste píxel central, pode se adicionar uma quantidade de 
		 *   asteriscos para a esquerda e para a direita do pixel referido. 
		 *   Seja W a quantidade de asteriscos a adicionar de cada lado. 
		 *   W é uma série em função do número de disco.
		 *   (diskNumber - valor num determinado index da stack). 
		 *   
		 *   Resumindo:
		 *   L (número de colunas) = 2*disk, disk - discos em jogo
		 *   halfL (centro geométrico de uma torre) = disk
		 *   Intervalo de desenho de um disco - [halfL - diskNumber , halfL + diskNumber]
		 *	 
		 *-------------------------------------------------------------------------------*/

		String cursor = " "; //cursor de escrita de um pixel, inicializado com um espaço

		int L = 2*disk; // número máximo de pixeis horizontais por torre
		int halfL = disk; // centro geometrico de cada torre

		int towerSize = towerOne.size(); //quantidade de discos sobrepostos numa torre



		/*
		 * O próximo loop funciona com se fosse um tubo de raios catódicos.
		 * Primeiro escolhe uma linha da matriz apresentada, (row). 
		 * De seguida. é chamada uma rotina de desenho, sendo que a rotina deseha prinheiro
		 * os espaços vazios e os espaços que correspondem a um disco em coluna.
		 * Após acabar uma torre, o cursor de desenho mantem-se na mesma linha,
		 * mas muda de coluna e torre, fazendo isto sucessivamente até todas as 
		 * torres estarem representadas. 
		 * No final, a linha de desenho é fechada e o cursor de desenho passa 
		 * a uma nova linha, (row).
		 * A quantidade de linhas é diretamente proporcional ao número de peças
		 * em jogo.
		 *
		 */

		for (int row = disk + 1; row > 0; row--) {

			//É chamada a rotina de desenho para uma linha de cada torre
			drawRoutine(cursor, row, L, disk, towerOne, halfL, towerSize);
			drawRoutine(cursor, row, L, disk, towerTwo, halfL, towerSize);
			drawRoutine(cursor, row, L, disk, towerThree, halfL, towerSize);

			//A linha é fechada
			System.out.println(" ");

			//No final do loop, o cursor muda de linha

		}

		System.out.println("########".repeat(disk)); //tabuleiro que sustenta as três torres
	}
	
	public static void drawRoutine (String cursor, int row, int L, int disk, Stack<Integer> tower, int halfL, int towerSize) {

		/* 
		 * Na rotina de desenho, verifica-se qual o tamanho de uma torre e
		 * qual o número que identifica o tamanho de um disco.
		 * 
		 */
		int diskNumber; //o número dos disco está relacionado com o seu tamanho visual 


		/*
		 * Se linha a onde estamos a desenhar é menor que o número de peças 
		 * numa torre, então certamente que tem de ser desenhado um disco.
		 * O tamanho do disco e a sua posição é definido pelo método towerDraw.
		 * Se o tamanho da torre é menor que a linha de desenho, então estamos 
		 * perante uma torre vazia ou parcialmente vazia. Se o cursor estiver 
		 * no centro geométrico da torre, então este desenha um caracter "|". Caso
		 * contrário, desenha um " ".
		 */

		if (row < tower.size()) {

			diskNumber = tower.get(row); //número no index de valor row da stack selecionada
			towerSize = tower.size(); //tamanho atual da torre, que muda a cada jogada

			/* Método towerDraw desenha um caracter na coluna escolhida, tomando em consideração
			 * o tamanho do disco e a posição deste. Desenha também os pixeis "vazios"
			 * em torno dos disco.
			 */
			cursor = towerDraw(diskNumber, halfL, cursor, disk, towerSize, L);	

			//O método towerDraw devolve a linha, já com todas as colunas desenhadas de uma torre
			System.out.print(cursor);

		} else {

			cursor = " "; //reset no cursor. Acaba também uma barreira de sepração de torres

			/* Estamos perante o caso quando a linha é menor que o tamanho atual da
			 * torre. Logo, só serão desenhados os caracteres "|" e " ".
			 * O for loop percorre todas as colunas de uma única torre.
			 */

			for (int col = 0; col < L + 1 ; col++) {

				if (col == halfL) {

					//caso a coluna esteja no centro geométrico da torre, desenha-se "|"
					cursor = cursor +  "|";

				} else {

					//caso contrário, desenha-se um " ".
					cursor = cursor +  " ";

				}
			}

			/* Por fim, o cursor atual é printado na consola, mas sem ser mudada a linha.
			 * Isto porque ainda falta desenhar as outras torres, a não ser que esta seja
			 * a última.
			 */
			System.out.print(cursor);

		}

	}


	public static String towerDraw (int diskNumber, int halfL, String cursor, int disk, int towerSize, int L) {



		/*
		 * A função towerDraw desenha os pixeis coluna a coluna de uma única torre.
		 * Começa no pixel mais à esquerda (0) e acaba no mais à direita (L);
		 * Neste método, o número que identifica o disk na stack é usado para definir
		 *  o intervalo de desenho do disco.
		 */


		int farRight_DiskBorder; //fronteira mais à direta do intervalo de desenho do disco
		int farLeft_DiskBorder; //fronteira mais à esquerda do intervalo de desenho do disco

		/* Como dito anteriormente: 
		 * Intervalo de desenho de um disco - [halfL - diskNumber , halfL + diskNumber]
		 * Exemplo:
		 *     . . . | . . .               
		 *	 3 . . * * * . .     Stack:  [ 1 - diskNumber at index 3
		 *	 2 . * * * * * .               2 
		 *	 1 * * * * * * *               3
		 *	 0 0 1 2 3 4 5 6              100]
		 *
		 * O menor disco disco tem um intervalo de desenho de [3 - 1, 3 + 1] = [ 2, 4].
		 * O segundo menor disco tem um intervalo de desenho de [3 - 2, 3 + 2] = [ 1, 5].
		 * cada disco tem 2*n+1 "*"
		 */

		//intervalos de desenho
		farRight_DiskBorder = halfL + diskNumber; 
		farLeft_DiskBorder = halfL - diskNumber;

		//Variáveis que definem quais os valores que podem ser desenhados nos pixeis.
		String emptyPixel = " ";
		String diskPixel = "*";

		cursor = " "; //reset no cursor. Acaba também uma barreira de sepração de torres

		// O seguinte loop percorre todas as colunas de uma única torre.
		for (int col = 0; col < L + 1; col++ ) {

			// Se a coluna estiver dentro do intervalo de desenho do disco, então é 
			// adiconado ao cursor um caracter "*".
			if (col >= farLeft_DiskBorder && col <= farRight_DiskBorder && diskNumber != 0) {

				cursor = cursor + diskPixel;	//o cursor vai adicionando os pixeis atuais aos antigos

			} else {
				//Caso se esteja fora do intervalo de desenho, é adicionado um " " ao cursor.
				cursor = cursor + emptyPixel;

			}

		}

		//A linha é de uma torre é terminada e enviada para ser adicionadas às linhas de outras torres
		return cursor;

	}

	public static String displayStat(int nmbOfRods,int totalScore,int numOfTimes){



		double average = 1.0*totalScore/numOfTimes;
		return"For "+nmbOfRods+" rods you have in average "+average+" moves and you played "+numOfTimes+" times";

	}

	public static void displayMenu() { 
		System.out.println("---------Select and Option----------\n");
		System.out.println("1-Play");
		System.out.println("2-See stats");
		System.out.println("Q-Stop");
		
	}
	
	public static String showResults(HashMap<Integer, Integer[]> dataScores){

		String endstring="";	
		for (int i=3; i<11;i++){
			if (dataScores.get(i)[1]>=0){
				endstring += displayStat(dataScores.get(i)[0],dataScores.get(i)[1],i)+"\n"; 
				//{number of times played,total score,number of disks}  
			}	

		}
		return endstring;
	}

}



