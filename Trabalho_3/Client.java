package trabalho3.Trabalho_3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.HashMap;


public class Client { 																// CLIENTE

	public static void main(String args[]) throws IOException {
		Socket socket = new Socket("localhost", 1234); 					// Abrir conexão com o servidor, na ponte 7000

		InputStream in = socket.getInputStream();
		DataInputStream dataIn = new DataInputStream(in);
		OutputStream out = socket.getOutputStream();
		DataOutputStream dataOut = new DataOutputStream(out);
		boolean acabarJogo=true;
		
		
		while (acabarJogo) {
		
			Scanner sc = new Scanner(System.in);
			System.out.println("Connect to server 'Torre de Hanoy' [Y/N]: ");
			
			dataOut.writeUTF(sc.nextLine());

			dataOut.flush();
			// Ler a resposta do servidor
			String serverResponse = dataIn.readUTF();
			
			if (serverResponse.equals("PROTOCOL_ERROR")) 
			{
				System.out.println("Connection timed out");
				acabarJogo=false;
			} 
			else if (serverResponse.equals("PLAY")) 
			{
				System.out.println("The server replied: " + serverResponse);
				
				System.out.println("insert username: ");
				dataOut.writeUTF(sc.nextLine());
				
				System.out.println("insert password: ");
				dataOut.writeUTF(sc.nextLine());
				
				String respostaCredencial = dataIn.readUTF();
				
				if (respostaCredencial.equals("INVALID_CREDENTIAL")) 
				{
																						// Caso as credenciais estiverem erradas
					System.out.println("Invalid Login - Try again [Y/N]: ");
					dataOut.writeUTF(sc.nextLine().toUpperCase());
					boolean invalid = true;
					while (invalid) 
					{
						String respostaTentativa = dataIn.readUTF();
						if (respostaTentativa.equals("TRY_AGAIN")) 
						{
							System.out.println("Insert username again ");
							dataOut.writeUTF(sc.nextLine());
							System.out.println("Insert password again ");
							dataOut.writeUTF(sc.nextLine());
							String respostaNovaCredencial = dataIn.readUTF();
							
							if (respostaNovaCredencial.equals("VALID_CREDENTIAL")) 
							{
								System.out.println("Valid Login!");
								invalid = false;
							}
							else if (respostaNovaCredencial.equals("INVALID_CREDENTIAL"))
							{
								System.out.println("Invalid Login - Try again [Y/N]: ");
								dataOut.writeUTF(sc.nextLine().toUpperCase());
							}
						}
						else if (respostaTentativa.equals("NO_TRY")) 
						{ // TEM DE TERMINAR A CONEXÃO - PEDE A PASS
							System.out.println("Connection timed out");
							invalid = false;
						}
						
						
						
						
						
						
					}
				}
			}
		}
		
		
		
		boolean playing=true;
		while(playing) {
			
			Scanner sc = new Scanner(System.in);
			
			//Recepção ao jogador
			System.out.println("*".repeat(25));
			System.out.println("* Welcome to Hanoi Tower *");
			System.out.println("*".repeat(25)+"\n\n\n");
			
			
			System.out.print("* Insert number of disks between 3 and 10 to continue: ");
			
			
			
			
			
			
			sc.close();
		}
		
		
		

	

	}
}



			