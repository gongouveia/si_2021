package trabalho4;

import java.io.File;  // Import the File class  
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.IOException;
import java.util.Scanner; // Import the Scanner class to read text files
import java.io.FileWriter; 
import java.util.HashMap;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;





public class ReadWrite {

	String file_publication = "publicationDATABASE.txt";
	String file_client = "clientDATABASE.txt";

	public  void main(String[] args) {

		//String file = "C:\\Users\\MyName\\filename.txt");

		verify(file_publication);
		
		
		verify(file_client);


		write_new_client("dadada","dadada","dada","dadad");
		

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

	public void write_new_pub( String title, int year, String[] authors, String journal,int volume, int page,int nmb_citations,int DOI) {
		try {
			BufferedWriter bw_pubs = null;
			bw_pubs = new BufferedWriter(new FileWriter(this.file_publication, true));

			String author_string="";
			for(String i:authors) {
				author_string+=i+"*";
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
			System.out.println("Successfully wrote to the file.");
			System.out.println("publicationDB updated");

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


	public  HashMap<String, Pub> pubDB_updatefromfile(HashMap<String, Pub> pubDB) {

		try {

			File myObj = new File(this.file_publication);


			Scanner myReader = new Scanner(myObj);


			while (myReader.hasNextLine()) {

				String data = myReader.nextLine();
				String[] datastrip = data.split("/");

				String[] authors = datastrip[3].split("");


				Pub pub1 =   new Pub("title1",2,authors,"journal1",1,2,2,2);
				Pub new_Pub =   new Pub(datastrip[0],Integer.parseInt(datastrip[1]),authors,datastrip[3],Integer.parseInt(datastrip[4]),Integer.parseInt(datastrip[5]),Integer.parseInt(datastrip[6]),Integer.parseInt(datastrip[7]));


				pubDB.put(new_Pub.getTitle(), new_Pub);	


			}
			myReader.close();
			System.out.println("clientDB-updated");
		}catch (FileNotFoundException e) {
			System.out.println("An error occurred.\n");
			e.printStackTrace();
		}
		return pubDB;
	}






}




