package bam;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Quora
{
	private static final String URL = "https://espanol.answers.yahoo.com/question/index?qid=";
	
	public static void main (String[] args)
	{			 
		try 
		{
			OutputStream os = new FileOutputStream(new File("data/resp.txt"));
			File myObj = new File("data/data.txt");
			Scanner myReader = new Scanner(myObj);		
			ArrayList<String> input = new ArrayList<String>();
	    	
			while (myReader.hasNextLine()) 
		    {		    	
				input.add(myReader.nextLine());
		    }
			
			myReader.close(); 
			
			for(int i = 0; i<input.size(); i++)
			{
				URL url = new URL(URL+input.get(i));
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("GET");		 
				con.setDoOutput(true);
				
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
			    StringBuffer content = new StringBuffer();
			    
				while ((inputLine = in.readLine()) != null) 
				{
					content.append(inputLine);
				}
				
				String resp = content.toString();
				
				int inic = resp.indexOf("{\"@context\":\"http://schema.org\",\"@type\":\"QAPage\",\"mainEntity\":")+"{\"@context\":\"http://schema.org\",\"@type\":\"QAPage\",\"mainEntity\":".length();
				resp = resp.substring(inic);			
				int fini = resp.indexOf("</script>");			
				resp = resp.substring(0, fini);
				
				inic = resp.indexOf("{\"@type\":\"Question\",\"name\":\"")+"{\"@type\":\"Question\",\"name\":\"".length();
				resp = resp.substring(inic);
				
				fini = resp.indexOf("\",\"text\":\"");
				String preg = resp.substring(0,fini);
				
				inic = resp.indexOf("\",\"text\":\"")+"\",\"text\":\"".length();
				resp = resp.substring(inic); 
				fini = resp.indexOf("\",\"answerCount\":");
				String pregDet = resp.substring(0,fini);
				
				inic = resp.indexOf("{\"@type\":\"Answer\",\"text\":\"");
				String data = preg+","+pregDet+"\n";
				
				os.write(data.getBytes(), 0, data.length());
				System.out.println(data);
				
				ArrayList<String> resps = new ArrayList<String>(); 
				
				data = "";
				
				while (inic != -1)
				{
					inic += "{\"@type\":\"Answer\",\"text\":\"".length();
					resp = resp.substring(inic); 
					fini = resp.indexOf("\",\"author\":");
					resps.add(resp.substring(0,fini));
					data = resp.substring(0,fini)+"\n";
					System.out.println(data);
					
					os.write(data.getBytes(), 0, data.length()); 
					inic = resp.indexOf("{\"@type\":\"Answer\",\"text\":\"");
				}
				
				in.close();
				con.disconnect();
			}
			
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		} 
		catch (ProtocolException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
			
	}
	
}


