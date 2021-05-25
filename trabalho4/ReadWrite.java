package si_2021.trabalho4;

import java.io.File;  // Import the File class  
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.IOException;
import java.util.Scanner; // Import the Scanner class to read text files
import java.io.FileWriter; 
import java.util.HashMap;

import java.io.BufferedWriter;

import java.util.stream.Collectors;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;


public class ReadWrite {

	String file_publication = "publicationDATABASE.txt";
	String file_client = "clientDATABASE.txt";

	public ReadWrite() {

		//String file = "C:\\Users\\MyName\\filename.txt");

		verify(file_publication);
		
		
		verify(file_client);


		//write_new_client("dadada","dadada","dada","dadad");
		

	}
	//da update na base de dados
	public   HashMap<String, Client> clientDB_updatefromfile( HashMap<String, Client> clientDB) {
		try {

			File myObj = new File(this.file_client);
			clientDB.clear();
			
			Scanner myReader = new Scanner(myObj);
			//clientDB.put(null, null);
			//lê todas as linhas do ficheiro de clientes
			while (myReader.hasNextLine()) {


				String data = myReader.nextLine();
				//converte uma das linahs para array
				String[] datastrip = stripLine(data); 
				System.out.println(data  + " user");
				//adiciona um cliente a hasmap
				
				Client newClient = new Client(datastrip[0],datastrip[1],datastrip[2],datastrip[3]);
				
				clientDB.put(datastrip[0], newClient);
				
			}
			
			//myReader.close();
		}catch (FileNotFoundException e) {
			System.out.println("An error occurred. File not found.");
			e.printStackTrace();
		}
		//retorna a hashmap actualizada
		
		
		return clientDB;


	}


	public  void verify(String file) {

		File myObj = new File(file);

		if (myObj.exists()) {

			System.out.println("File name: " + myObj.getName());
			System.out.println("Absolute path: " + myObj.getAbsolutePath());
			System.out.println("Writeable: " + myObj.canWrite());
			System.out.println("Readable: " + myObj.canRead());
			System.out.println("File size in bytes " + myObj.length());
			System.out.println("\n");
		} else {
			create(file);
			System.out.println("The file does not exist.\n");
		}


	}


	public  void create(String file) {
		try {
			File myObj = new File(file);
			if (myObj.createNewFile()) {
				System.out.println("File created: " + myObj.getName());
				System.out.println("\n");
			} else {
				System.out.println("File already exists.\n");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.\n");
			e.printStackTrace();
		}
	}


	public void write_new_client(String name, String email,String password,String affi) {
		try {


			BufferedWriter myWriter = null;
			myWriter = new BufferedWriter(new FileWriter(this.file_client, true));
			//FileWriter mypub = new FileWriter(file_publication);
			//FileWriter myauthor = new FileWriter(file_authors);
			//myWriter.write("user1 user1@mail 123 affiliation1 \n");
			//myWriter.write("user2 user2@mail 123 affiliation2 \n");
			//myWriter.write("user3 user3@mail 123 affiliation3 \n");
			myWriter.write(name+" "+email+" "+password+" "+affi);
			myWriter.newLine();
			myWriter.flush();
			myWriter.close();
			//System.out.println("Successfully wrote to the file.");
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	
	public  void fileClear(String file) {
		File myObj = new File(file ); 
		if (myObj.delete()) { 
			System.out.println("Deleted the file: " + myObj.getName());
			System.out.println("\n");
		} else {
			System.out.println("Failed to delete the file.\n");
		} 
	}

	public  String[] stripLine(String str) {
		String[] split = str.split(" ");
		return split;
	}


	public  HashMap<Integer, Pub> pubDB_updatefromfile(HashMap<Integer, Pub> pubDB) {
		
		try {

			File myObj = new File(this.file_publication);


			Scanner myReader = new Scanner(myObj);

			while (myReader.hasNextLine()) {
				
				String data = myReader.nextLine();
				String[] datastrip = data.split("/");

				String[] authors = datastrip[2].split("!");
				

				
				Pub new_Pub = new Pub(datastrip[0],Integer.parseInt(datastrip[1]),authors,datastrip[3],Integer.parseInt(datastrip[4]),Integer.parseInt(datastrip[5]),Integer.parseInt(datastrip[6]),Integer.parseInt(datastrip[7]));

				
				pubDB.put(new_Pub.getDOI(), new_Pub);	
	
			}
			
			
			myReader.close();
			System.out.println("clientDB hashmap updated from file.");
		}catch (FileNotFoundException e) {
			System.out.println("An error occurred updating the clientDB hashmap.\n");
			e.printStackTrace();
		}
		System.out.println("Pubs added to the hashmap.");
		
		return pubDB;
	}


	public boolean isThisPubValid(int DOI) {
		try {
			File myObj = new File(this.file_publication);
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				String[] dataStrip = data.split("/");
				int DOI_toCompare = Integer.parseInt(dataStrip[dataStrip.length-1]);
				if (DOI_toCompare==DOI) {
					myReader.close();
					//o DOI que se quer inserir está na base de dados
					return false;
				}
			}
			myReader.close();	
			System.out.println("clientDB-updated");
		}catch (FileNotFoundException e) {
			System.out.println("An error occurred.\n");
			e.printStackTrace();
		}
		//o DOI que ser quer inserir não esta na base de dados
		return true;
	}



	public void write_new_pub( String title, int year, String[] authors, String journal,int volume, int page,int nmb_citations,int DOI) {

		try {

			
				//se a publicação puder ser adicionada é adicionada ao ficheiro		
				BufferedWriter bw_pubs = null;
				bw_pubs = new BufferedWriter(new FileWriter(this.file_publication, true));

				
				String author_string="";
				
				for(String i:authors) {
					author_string+=i.trim()+"!";
				}
				//da append de uma nova pubicação no ficheiro
				
				bw_pubs.write(title+"/"+year+"/"+author_string+"/"+journal+"/"+volume+"/"+page+"/"+nmb_citations+"/"+DOI);
				bw_pubs.newLine();
				bw_pubs.flush();
				//fecha streams
				if ( bw_pubs != null) try {

					bw_pubs.close();
				} catch (IOException ioe2) {
					// just ignore it
				}


		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

	}





	public boolean add_pub( String title, int year, String[] authors, String journal,int volume, int page,int nmb_citations,int DOI) {
		
		//se a PUB ainda náo estiver na base de dados
		if( isThisPubValid(DOI) ) {
			write_new_pub( title,  year, authors,  journal, volume,  page, nmb_citations, DOI);
			return true;
		}else {
		// Se o DOI já estiver no ficheiro não escreve a publicação no ficheiro
			return false;
		}
		
	}
	
	public void removePub(int DOI) throws IOException
	{
		//este emtodo remove uma das linhas  ejunda todo o texto
		//ex:
		//inicio:      pub1   					pub1
		//			   pub2 removeline------>   pub3
		//             pub3
		//filer obtem todas as linhas que não tenham o DOI que queremos eliminar
		// collect coloca todas as linhas do novo ficheiro em uma lista
		//finalmente coloca todas as publicações guardadas na lista no novo ficheiro



	    File file = new File(this.file_publication);
	    List<String> out = Files.lines(file.toPath())
	    					//a linha que acabamos é a que esta guardada no ficheiro com o DOI
	    					//ex: nome/year/author1!author2!/journal/volume/page/nm_citations/doi
	                        .filter(line -> !line.endsWith("/"+DOI))

	                        .collect(Collectors.toList());

	    //usando TRUNCATE os conteudos anteriores dos ficheiros são subsituidos 
	    Files.write(file.toPath(), out, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
	    System.out.println("FILE_UPDATED");

	}



}




