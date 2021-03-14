/*  Primeiro trabalho de SI - 15/03/2021
 * 	Torre de Hanoi
 * 
 *  Francisco Relvão | 2018285965 |MIEF
 *  Gonçalo Gouveia  | 2018277419 |MIEF
 * 
 * */

package resolproj;

import java.util.*; 


public class Playground {



	public static void main(String args[])
	{
		// criação do objeto da classe Scanner, que lê o teclado
		Scanner sc = new Scanner(System.in);
		
		//Recepção ao jogador
		System.out.println("*".repeat(25));
		System.out.println("* Welcome to Hanoi Tower *");
		System.out.println("*".repeat(25));
		
		/*
		* Stacks vazios são arrays que permitem alterações aos seus elementos.
		* Decidimos realizar este exercicio com Stacks, porque para algoritmos 
		* recursivos, stacks são uma otima ferramenta.
		*/
		
		// criação de uma stack vazia para cada torre
		Stack<Integer> aux1 = new Stack<Integer>();       //torre direita		
		Stack<Integer> aux2 = new Stack<Integer>();       //stack intermedia
		Stack<Integer> aux3 = new Stack<Integer>();       //stack esquerda

		
		int disk = 0;    //número de discos usados durante o jogo 
		int diskMin = 3; //número minimo de discos permitidos
		int diskMax = 10;//número máximo de discos permitidos
		int counter = 0; // regista o numero de passos até resolver o puzzle
		int solve;       //regista o numero de tentaivas minimo para resolver o jogo

		int size1 = 0;   //inicializa o tamanho de cada um dos stacks
		int size2 = 0;
		int size3 = 0;
		

		String option; // Input de jogadas ao longo do programa

			
		    	//É pedido um input válido para o numero de discos entre 3 e 10
				//qualquer outro input fora do limite ou letra será lançada uma mensagem de erro 
				// e será pedido ao utilizador um novo numero de discos até ser valido
		
			do {
				
			System.out.print("* Insert number of disks between 3 and 10 to continue: ");
			
			
			if(sc.hasNextInt()) {      //seleciona apenas inputs inteiros
				
			    disk = sc.nextInt(); 
			    
			} else {
				
				disk = 0;   //valor para dar erro e voltar a pedir um input
			}
			//pede-se ao usuário o numero de discos
			
			
			sc.nextLine();   // tratar do caso especial do /n quando se insere o inteiro
			//O .nextInt separa o inteiro e o /n. Se o barra /n não for limpo da linha,
			//então este loop nunca irá parar.

			// caso o numero de discos selecionado não estiver dentro dos valores aceitaveis
			// é lançada uma mensagem de erro e é pedido um novo input válido.
			
			if (disk < diskMin || disk > diskMax) {
				errorDraw(); //rotina de erro
			}

		}  while (disk < diskMin || disk > diskMax) ;
			
			
			
		solve = (int)Math.pow(2,disk)-1;   //número de tentativas minimas para resolver o jogo
			
		//Base de cada torre. Com este, desenhamos um base da stack pois nunca pode haver troca 
		//de valores da base entre as bases. Com este metodo conseguimos com que as Stacks nunca fiquem vazias

		aux1.push(1000);
		aux2.push(1000);
		aux3.push(1000);

		/*
		 * Loop de introdução do número de discos definidos pelo o usuario 
		 * Os discos são introduzidos por ordem descrescente no stack
		 */
		
		for (int j = disk; j >= 1; j--){
			aux1.push(j); 

		}
		
		//Loop que mantem o jogo ativo, enquanto o puzzle não for resolvido
		
		while (true) {	
			
			
			
			System.out.print("* Please enter moves A->B A->C B->C, exit press y/Y:\n");
			System.out.print("* From tower A, B, C: \n"); //Primeira torre selecionada. Origem do disco
			
			//routina de desenho das 3 torres do jogo
			draw(disk, aux1, aux2, aux3);
			
			
			//-----------------------------------------------------------------------------------------------
			
			// Bloco de decisão para fechar o jogo, caso o jogador pressione a tecla Y
			
			System.out.println("Close game? ([Y] for yes, any key to continue.)");
			
			/*usamos .toUpperCase(); para que o jogo possa
            parar se o jogador seleccioanr y/Y */
			
			option = sc.nextLine().toUpperCase(); //a input é convertida para letra maiúscula
			
			if (option.equals("Y")) {  //excerto de codigo que indica que o jogo acaba quando y/Y
				
				System.out.println("Problem not solved, you pressed exit."); 
				break; // o jogo é terminado, o while loop é quebrado
			}
			
			//-----------------------------------------------------------------------------------------------
			
			//Output das opções de jogadas
			System.out.println("1:A-->B");                        
			System.out.println("2:A-->C");
			System.out.println("3:B-->A");
			System.out.println("4:B-->C");
			System.out.println("5:C-->A");
			System.out.println("6:C-->B");
			
			
			option = sc.nextLine().toUpperCase();           
			
			
		
			
			switch (option) {       //implementamos este switch para avaliar cada um dos inputs do ultilizador
			//além disso, um movimento so é contado, se existir movimento de discos entre pirâmides
				case "1":
					counter = diskXange(aux1,aux2, counter);    //1:A-->B
					break;
	
				case "2":
					counter = diskXange(aux1,aux3, counter);    //2:A-->C
					break;
	
				case "3":
					counter = diskXange(aux2,aux1, counter);    //3:B-->A
					break;
	
				case "4":
					counter = diskXange(aux2,aux3, counter);    //4:B-->C
					break;
	
				case "5":
					counter = diskXange(aux3,aux1, counter);    //5:C-->A
					break;
	
				case "6":
					counter = diskXange(aux3,aux2, counter);	 //6:C-->B
					break;
					
				case "":
					// movimento vazio é ignorado
					break;
					
				default:                      //qualquer outro input dá erro
					errorDraw();   			  //mensagem de erro
					break;

			}
			
			//-----------------------------------------------------------------------------------------------

            
			Object[] auxx1 = aux1.toArray(); 
			size1 = auxx1.length;
				
			Object[] auxx2 = aux2.toArray(); 
			size2 = auxx2.length;
			
			
			Object[] auxx3 = aux3.toArray(); 
			size3 = auxx3.length;
			
			
			System.out.println("Current play: " + counter + "\n");
			
			if (counter>0) {
				//qando apenas a primeira e a segunda torre tiverem os valores da base o jogo fica completo
				// ou seja, as duas primeiras torres tiverem apenas um elemento, isto implica
				//que a terceira torre tem todos os elementos e de forma ordenada.
				
				if (size1==1 && size2 ==1)   
				{ System.out.println("* Game solved with sucess *\n");
				System.out.println("Problem solved in "+ counter + " attemps");
				System.out.println("Could be solved in " + solve +" attemps");
				
				break;}  
				
			}
			
			

		}
		// créditos
		System.out.println("* Thank you for Playing \n");

		System.out.println("\n");
		System.out.println(" " +"*".repeat(23));
		System.out.println("*         Credits       *");
		System.out.println(" " +"*".repeat(23)+"\n");
		System.out.println(" Francisco Relvão | 2018285965 |MIEF\n Gonçalo Gouveia  | 2018277419 |MIEF");


		sc.close();
	}
	
	
	
	
	public static int diskXange (Stack<Integer> a ,Stack<Integer> b, int movement) {
		//rotina para mudar os discos
		
		int var;
		// se o ultimo disco da torre para que vai o disco seclecionado for maior então não é permitido
		if (a.lastElement() >= b.lastElement() )   
		{
			errorDraw();   //chama a função a aviar que este paço náo é possivel
			
		}

		else {  //se possivel executar passar os discos da torre, o ultimo disco é extraido da stack1 e colocado na stack 2
			var = a.pop(); b.push(var);
			movement ++; //o counter de jogada só é incrementado, se existir movimento de discos
			}
		return movement; // o counter de jogada é igualado ao movement 
	}
	
	
	public static void errorDraw() {         // implementamos esta função para avisar quando um input do utilizador é invalido
		System.out.println("\n");
		System.out.println(" "+"*".repeat(35));
		System.out.println("****** Try again, not possible. ******");
		System.out.println("****** Please, insert a number. ******");
		System.out.println(" "+"*".repeat(35));
		System.out.println("\n");
		}
	  
	
	public static void draw(int disk, Stack<Integer> towerOne, Stack<Integer> towerTwo, Stack<Integer> towerThree) {
		
		
		/*   
		 * ------------------------- FORMULAÇÂO MATEMÁTICA --------------------------------
		 * 
		 *   Considere-se a seguinte pirâmide e a sua comparação com uma stack:
		 *   
		 *   Na vertical, tem -se o index da Stack. 
		 *   Na horizontal, o index da coluna a escrever. Um pixel é considerado 
		 *   um ponto desta matriz.
		 *   
		 *   index Stack
		 *     . . . | . . .  
		 *	 3 . . * * * . .     Stack:  [ 1 - diskNumber at index 3
		 *	 2 . * * * * * .               2 
		 *	 1 * * * * * * *               3
		 *	 0 0 1 2 3 4 5 6              100]
		 *	 		(L = 6, neste caso.), (halfL = 3);
		 *
		 * 	 O número máximo de pixeis por matriz de torre é definido pelo o numero de 
		 *	 peças escolhidas no inicio do jogo. Isto, porque a maior peça  
		 *	 define este numero máximo de pixeis.
		 * 	 
		 *	 Logo, se L for o numero máximo de pixeis numa linha e em uma torre, então
		 *	 L é igual a 2*disk, sendo o disk o número peças em jogo.
		 *	 
		 *	 É necessário definir o centro geométrico das torres. 
		 *	 O centro geométrico é sempre halfL = disk e é metade do tamanho total, L.
		 *	 
		 *	 Por fim, cada disco é desenhado com simetria em relação ao eixo
		 *	 da base de sustento. Logo, o pixeis com (halfL, y) fazem sempre
		 * 	 parte do desenho de um disco. 
		 *   A partir deste píxel central, pode-se adicionar uma quantidade de 
		 *   asteriscos para a esquerda e para a direita do pixel referido. 
		 *   Seja W a quantidade de asteriscos a adicionar de cada lado. 
		 *   W é uma série em função do número que indentifica o disco na stack.
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
		 * Primeiro, o loop escolhe uma linha da matriz apresentada, (row). 
		 * De seguida. é chamada uma rotina de desenho, sendo que esta deseha prinheiro
		 * os espaços vazios e os espaços que correspondem a um disco em coluna.
		 * Após acabar uma torre, o cursor de desenho mantem-se na mesma linha,
		 * mas muda de coluna e torre, fazendo isto sucessivamente até todas as 
		 * torres estarem desenhadas, numa dada linha. 
		 * No final, a linha de desenho é fechada e o cursor de desenho passa 
		 * a uma nova linha, (row).
		 * A quantidade de linhas é diretamente proporcional ao número de peças
		 * em jogo.
		 *
		 */
		
		for (int row = disk + 1; row > 0; row--) {
			
			//É chamada a rotina de desenho para uma linha de cada torre
			drawRoutine(cursor, row, L, disk, towerOne, halfL, towerSize);
			//torre intermédia
			drawRoutine(cursor, row, L, disk, towerTwo, halfL, towerSize);
			//torre mais à direita
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
		int diskNumber; //o número do disco na stack está relacionado com o seu tamanho visual 
		
		
		/*
		 * Se linha (row) a onde estamos a desenhar é menor que o número de peças 
		 * numa torre (tower.size()), então certamente que tem de ser desenhado um disco.
		 * O tamanho do disco e a sua posição é definido pelo método towerDraw.
		 * Se o tamanho da torre é menor que a linha atual de desenho, então estamos 
		 * perante uma torre vazia ou parcialmente vazia. Se o cursor estiver 
		 * no centro geométrico da torre, então o cursor desenha um caracter "|". Caso
		 * contrário, desenha um " "(empty).
		 */
		
		if (row < tower.size()) {
			
			diskNumber = tower.get(row); //número de disco no index de valor row da stack selecionada
			towerSize = tower.size(); //tamanho atual da torre, que muda a cada jogada
			
			/* Método towerDraw desenha um caracter na coluna escolhida, tomando em consideração
			 * o tamanho do disco e a posição deste. Desenha também os pixeis "vazios"
			 * em torno dos disco.
			 */
			cursor = towerDraw(diskNumber, halfL, cursor, disk, towerSize, L);	
			
			//O método towerDraw devolve a linha, já com todas as colunas desenhadas de uma torre
			System.out.print(cursor);
			
		} else {
			
			cursor = " "; //reset no cursor. Acaba também por ser uma barreira de separação de torres
			
			/* Estamos perante o caso em que a linha é menor que o tamanho atual da
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
		 * Cada disco tem 2*n+1 "*" (asteriscos)
		 */
		
		//intervalos de desenho
		farRight_DiskBorder = halfL + diskNumber; 
		farLeft_DiskBorder = halfL - diskNumber;
		
		//Variáveis que definem quais os valores que podem ser desenhados nos pixeis, neste caso
		String emptyPixel = " ";
		String diskPixel = "*";
		
		cursor = " "; //reset no cursor. Acaba também por ser uma barreira de separação de torres
			
		// O seguinte loop percorre todas as colunas de uma única torre.
		for (int col = 0; col < L + 1; col++ ) {
			
			// Se a coluna estiver dentro do intervalo de desenho do disco, então é 
			// adiconado ao cursor um caracter "*".
			if (col >= farLeft_DiskBorder && col <= farRight_DiskBorder && diskNumber != 0) {
				
				cursor = cursor + diskPixel;	//o cursor vai adicionando os pixeis atuais aos antigos
				
			} else {
				//Caso o cursor esteja fora do intervalo de desenho, é adicionado um " " ao cursor.
				cursor = cursor + emptyPixel;
				
			}
				 
		}
		 
		//A linha de uma torre é terminada e é enviada para ser adicionada à linha de outras torres
		return cursor;
	}
	
}