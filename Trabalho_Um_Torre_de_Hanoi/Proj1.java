package resolproj;
import java.util.Scanner;
//Java code to illustrate pop() 
import java.util.*; 


public class Proj1 {



	public static void main(String args[])
	{
		// criação do objeto da classe Scanner, que lê o teclado
		Scanner sc = new Scanner(System.in);
		
		//first game layer
		System.out.println("*".repeat(25));
		System.out.println("* Welcome to Hanoi Tower *");
		System.out.println("*".repeat(25));

		// Creating an empty Stack 
		//Stacks vazios são arrays que não estáticos
		
		
		//decidimos realizar este exercicio por Stacks pois para algoritmos recursivos é uma otima ferramenta
		//Em probleams recursivos Stacks demosntram uma elevada performance
		
		
		Stack<Integer> aux1 = new Stack<Integer>();       //torre direita		// cria uma stack vazia para cada torre
		Stack<Integer> aux2 = new Stack<Integer>();       //stack intermedia
		Stack<Integer> aux3 = new Stack<Integer>();       //stack esquerda

		
		int disk = 0;//número de discos usados durante o jogo
		int diskMin = 3;//número minimo de discos permitidos
		int diskMax = 10;//número máximo de discos permitidos
		int counter = 0;
		int solve = 0;

		int size1 = 0;
		int size2 = 0;
		int size3 = 0;
	
		
		
		while (true) {
			
			System.out.print("* Insert number of disks between 3 and 10 to continue: ");
			
			
			
			if(sc.hasNextInt()) {
				
			    disk = sc.nextInt();
			    
			} else {
				
				System.out.println("Insert a number:");
				
			}
			//pede-se ao usuário o numero de discos
			
			
	
			
			
			
			if (disk >= diskMin && disk <= diskMax) {
				break;
			}
			
		}
		
		solve = 2*(disk)-1;//número de tentativas minimas
		
		//base de cada torre. 
		aux1.push(1000);
		aux2.push(1000);
		aux3.push(1000);

		/*Loop de introdução do número de discos definidos pelo o usuario 
		 * Os discos são introduzidos por ordem descrescente no stack
		 */
		for (int j = disk; j >= 1; j--){
			
			aux1.push(j); 
		
			
		}

		String tower1; 	//
		String tower2;   //initializer

		
		while (true) {	
			//Pedido de jogada ao utilizador
			System.out.print("* Please enter moves A->B A->C B->C, for pause y/Y:\n");
			System.out.print("* From tower A, B, C: \n"); //Primeira torre selecionada. Origem do disco
			draw(disk, aux1, aux2, aux3);
			
			//-----------------------------------------------------------------------------------------------

			System.out.println("1:A-->B");                        
			System.out.println("2:A-->C");
			System.out.println("3:B-->A");
			System.out.println("4:B-->C");
			System.out.println("5:C-->A");
			System.out.println("6:C-->B");
			System.out.println("Press Y to pause.");
	 
			String option = sc.nextLine().toUpperCase();              /*usamos .toUpperCase(); para que o jogo possa
															            parar se o jogador seleccioanr y/Y */
			
			
			if (option.equals("Y")) {                       //excerto de codigo que indica que o jogo acaba quando y/Y
				System.out.println("Problem not solved, you pressed exit"); 
				break;
			}
			
			
			switch (option) {       //implementamos este switch para avaliar cada um dos inputs do ultilizador
				case "1":
					diskxange(aux1,aux2);    //1:A-->B
					break;
	
				case "2":
					diskxange(aux1,aux3);    //2:A-->C
					break;
	
				case "3":
					diskxange(aux2,aux1);    //3:B-->A
					break;
	
				case "4":
					diskxange(aux2,aux3);    //4:B-->C
					break;
	
				case "5":
					diskxange(aux3,aux1);    //5:C-->A
					break;
	
				case "6":
					diskxange(aux3,aux2);	 //6:C-->B
					break;
				case "":
					break;
				default:                      //qualquer outro input do 
					errordraw();   			  //mensagem de erro
					break;

			}

			//-----------------------------------------------------------------------------------------------

			//System.out.println(aux1);              //print Stack da primeira torre
			Object[] auxx1 = aux1.toArray(); 
			size1 = auxx1.length;
			
			//System.out.println(aux2);				//print Stack da segunda torre
			Object[] auxx2 = aux2.toArray(); 
			size2 = auxx2.length;
			
			//System.out.println(aux3);				//print Stack da terceira torre
			Object[] auxx3 = aux3.toArray(); 
			size3 = auxx3.length;
			
			
			
			
			if (counter>0) {
				if (size1==1 & size2 ==1)
				{ System.out.println("* Game solved with sucess *\n");break;}  
				System.out.println("Problem solved in "+counter+ " attemps");
			}
			
			counter ++;

		}

		System.out.println("Could be solved in "  + solve +" attemps");

		System.out.println("* Thank you for Playing \n");

		System.out.println("*".repeat(25));
		System.out.println("*         Credits       *");
		System.out.println("*".repeat(25)+"\n");
		System.out.println(" Francisco Relvão | 2018285965 |MIEF\n Gonçalo Gouveia  | 2018277419 |MIEF");


		sc.close();
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
		
		System.out.println("########".repeat(disk)); //tabuleiro sustenta as três torres
	}
	
	
	
	
	
	public static void errordraw() {         // implementamos esta função para avisas qnd um input do utilizador é invalido
		
		System.out.println("*".repeat(37));
		System.out.println("*******Try again, not possible*******");
		System.out.println("*".repeat(37));
		}
	
	
	public static void diskxange (Stack<Integer> a ,Stack<Integer> b) {
		int var;
		// se o ultimo disco da torre para que vai o disco seclecionado for maior então não é permitido
		if (a.lastElement() >= b.lastElement() )   
		{
			errordraw();   //chama a função a aviar que este paço náo é possivel  
		}

		else {  //se possivel executar passar os discos da torre, o ultimo disco é extraido da stack1 e colocado na stack 2
			var = a.pop(); b.push(var);}
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
	
}
