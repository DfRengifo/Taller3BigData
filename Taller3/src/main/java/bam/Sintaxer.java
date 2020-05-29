package bam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Sintaxer extends Thread
{
	private static final String URL = "https://api.meaningcloud.com/parser-2.0?";
	
	private String text; 
	
	public Sintaxer (String text)
	{
		this.text = text;
	}
	
	public void run ()
	{
		String base = URL;
		base += "key=123db7ab0904091f42360c056abd773b&";
		base += "lang=es&";
		base += "verbose=y&";
		base += "uw=y&";
		base += "rt=y&";
		base += "dm=s&";
		base += "sdg=g&";
		base += "tt=a&";
		base += "st=y&";
		base += "sm=general&";
		base += "egp=y&";
		base += "txt="+text.replace(" ","+");
		
		try 
		{
			System.out.println(base);
			URL url = new URL(base);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");		 
			con.setDoOutput(true);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
		    StringBuffer content = new StringBuffer();
		    
			while ((inputLine = in.readLine()) != null) 
			{
				content.append(inputLine);
			}
			
			in.close();
			con.disconnect();
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


