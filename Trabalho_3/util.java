package serverproject;
import java.io.IOException;
import java.security.MessageDigest;
public class util {

	public static void main(String[] args) 
	{
	
	String news = "1234";
	
	//Message digests are secure one-way hash functions that take arbitrary-sized data
	//and output a fixed-length hash value. 
	System.out.println(news);
	System.out.println(getSHA256(news));
	
	

	}
		public static String getSHA256(String data){
	    StringBuffer sb = new StringBuffer();
	    try{
	        MessageDigest md = MessageDigest.getInstance("SHA-256");
	        md.update(data.getBytes());
	        byte byteData[] = md.digest();

	        for (int i = 0; i < byteData.length; i++) {
	         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }
	    } catch(Exception e){
	        e.printStackTrace();
	    }
	    return sb.toString();
	
}
}
